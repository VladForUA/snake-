package com.idalko.battle_snake.fw.di

import akka.actor.typed.ActorRef
import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Injector, TypeLiteral}
import com.idalko.battle_snake.fw.ai.AiActorProvider
import com.idalko.battle_snake.fw.api.ai.{IAi, IAiActor}

class AiModule(akkaModuleInjector: Injector) extends AbstractModule {
  override def configure(): Unit = {
    requireBinding(classOf[IAi])
    bind(new TypeLiteral[ActorRef[IAiActor.ICommand[_]]](){})
      .annotatedWith(Names.named("AiActor"))
      .toProvider(classOf[AiActorProvider])
  }
}
