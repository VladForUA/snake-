package com.idalko.battle_snake.fw.routes

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.javadsl.marshalling.Marshaller
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.PredefinedToEntityMarshallers
import akka.http.scaladsl.model.{HttpHeader, MediaTypes, StatusCodes}
import akka.http.scaladsl.model.MediaTypes._
import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.server.ContentNegotiator.Alternative.MediaType
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Sink
import akka.util.{ByteString, Timeout}
import com.google.inject.name.Named
import com.idalko.battle_snake.fw.api.domain.move.IMoveResponse
import com.idalko.battle_snake.fw.api.domain.start.IStartResponse
import com.idalko.battle_snake.fw.api.ai.IAiActor
import com.idalko.battle_snake.fw.api.domain.move.{IMoveResponse, World}
import com.idalko.battle_snake.fw.api.domain.register.RegisterRequest
import com.idalko.battle_snake.fw.api.domain.start.StartRequest
import com.idalko.battle_snake.fw.api.domain.test.TestRequest
import com.idalko.battle_snake.fw.api.routes.IHttpRoutes
import com.idalko.battle_snake.fw.api.test.TestClient
import com.typesafe.config.Config
import javax.inject.Inject
import org.slf4j.LoggerFactory

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object HttpRoutes {
  private[routes] val LOG = LoggerFactory.getLogger(classOf[HttpRoutes])
}
class HttpRoutes @Inject()(@Named("AiActor") aiActorRef: ActorRef[IAiActor.ICommand[_]],
                           conf: Config,
                           testClient: TestClient)(implicit system: ActorSystem[_])  extends IHttpRoutes with SprayJsonSupport {

  import akka.actor.typed.scaladsl.AskPattern.schedulerFromActorSystem
  import akka.actor.typed.scaladsl.AskPattern.Askable
  import com.idalko.battle_snake.fw.json.JsonFormats._
  import HttpRoutes._

  private val timeoutFromConfStr: String = conf.getString("battle_snake_ai_fw.routes.ask-timeout")
  private val durationFromConf = Duration(timeoutFromConfStr)
  // asking someone requires a timeout and a scheduler, if the timeout hits without response
  // the ask is failed with a TimeoutException
  implicit val timeout: Timeout = Timeout.durationToTimeout(
    durationFromConf match {
      case fD: FiniteDuration => fD
      case _ => 200.millis
    }
  )
  override def routes: Route = concat(
    get {
      LOG.info("#routes receiving: GET /\nOK 200\n")
      complete(
        StatusCodes.OK,
        """
          |<html>
          |<head></head>
          |<body>
          | <h1>Hello!</h1>
          |</body>
          |</html>
        """.stripMargin)(PredefinedToEntityMarshallers.stringMarshaller(`text/html`))
    },
    // POST /register
    (post & path("register")) {
      entity(as[RegisterRequest]) { regRequest =>
        LOG.info(s"#routes receiving: POST /register $regRequest")
        val resultFuture: Future[None.type] = testClient.test(regRequest.gameServerUrl, TestRequest(regRequest.aiUrl))
        onSuccess(resultFuture) { _ =>
          LOG.info("#routes responding to POST /register\n204")
          complete(StatusCodes.NoContent)
        }
      }
    },
    // POST /start
    (post & path("start")) {
      entity(as[StartRequest]) { startRequest =>
        LOG.info(s"#routes receiving: POST /start $startRequest")
        val resultFuture: Future[IStartResponse] = aiActorRef
          .ask(
            IAiActor.GetStartResponse(startRequest, _)
          )
        onSuccess(resultFuture) { result =>
          LOG.info(s"#routes responding to POST /start $result")
          complete(result)
        }
      }
    },
    // POST /move
    (post & path("move")) {
      extractRequest { r =>
        onComplete(r.entity.dataBytes.runWith(Sink.fold(Seq.empty[ByteString])((result, byteString) => result :+ byteString))) {
          case Failure(e) =>
            LOG.error(s"#routes receiving: POST /move failed to read the request into memory", e)
            complete(StatusCodes.ServerError(500)("internal server error", "failed to read in the Move request"))
          case Success(byteStrings) =>
            val requestStr = new String(byteStrings.flatten.toArray)
            LOG.info(s"#routes reading in POST /move $requestStr")
            entity(as[World]) { moveRequest =>
              LOG.info(s"#routes receiving: POST /move $moveRequest")
              val resultFuture: Future[IMoveResponse] = aiActorRef
                .ask(
                  IAiActor.GetMoveResponse(moveRequest, _)
                )
              onSuccess(resultFuture) { result =>
                LOG.info(s"#routes responding to POST /move $result")
                complete(result)
              }
            }
        }
      }
    },
    // POST /end
    (post & path("end")) {
      LOG.info(s"#routes receiving: POST /end")
      complete("OK")
    }
  )
}
