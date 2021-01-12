package com.idalko.battle_snake.fw.api.server

import java.time.Instant
import java.util.concurrent.TimeUnit

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.{Http, server}
import com.google.inject.{AbstractModule, Guice, Injector, Module}
import com.idalko.battle_snake.fw.api.ai.IAi
import com.idalko.battle_snake.fw.api.routes.IHttpRoutes
import com.idalko.battle_snake.fw.di.{AiModule, AkkaModule, MainModule}
import com.typesafe.config.Config
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.concurrent.duration.Duration

object SnakeAiServer {
  val LOG = LoggerFactory.getLogger(classOf[SnakeAiServer.type])

  def start(ai: IAi): Unit =
    start(new AbstractModule {
      override def configure(): Unit =
        bind(classOf[IAi]).toInstance(ai)
    })

  def start(module: Module): Unit = {
    val startedCalledAt = Instant.now()

    val mainModuleInjector = Guice.createInjector(
      new MainModule
    )
    val conf: Config = mainModuleInjector.getInstance(classOf[Config])

    val rootBehavior = Behaviors.setup[Nothing] { context =>

      val akkaModuleInjector = mainModuleInjector.createChildInjector(
        new AkkaModule(context)
      )

      val aiServiceInjector = akkaModuleInjector.createChildInjector(
        module
      )

      val aiModuleInjector = aiServiceInjector.createChildInjector(
        new AiModule(akkaModuleInjector)
      )

      val httpRoutes = aiModuleInjector.getInstance(classOf[IHttpRoutes])
      startHttpServer(conf, httpRoutes.routes)(context.system)

      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, conf.getString("battle_snake_ai_fw.server.name"))
    val startedAt = Instant.now()
    val startDuration = Duration(startedAt.toEpochMilli - startedCalledAt.toEpochMilli, TimeUnit.MILLISECONDS)
      .toCoarsest
    LOG.trace(s"#start actorSystem `$system` started in $startDuration")
  }

  def startHttpServer(conf: Config, route: server.Route)(implicit system: ActorSystem[Nothing]): Future[None.type] = {
    // Akka HTTP still needs a classic ActorSystem to start
    import system.executionContext

    val futureBinding = Http()
      .newServerAt(
        conf.getString("battle_snake_ai_fw.server.interface"), conf.getInt("battle_snake_ai_fw.server.port"))
      .bind(route)
    futureBinding
      .map { binding =>
        val address = binding.localAddress
        LOG.info("#startHttpServer Server online at http://{}:{}/", address.getHostString, address.getPort)
        None
      }
      .recover{
        case ex: Throwable =>
          LOG.error("#startHttpServer Failed to bind HTTP endpoint, terminating system", ex)
          system.terminate()
          None
      }
  }
}
