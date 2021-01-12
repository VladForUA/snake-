package com.idalko.battle_snake.fw.ai

import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.ActorContext
import com.google.inject.Provider
import com.idalko.battle_snake.fw.api.ai.IAiActor
import javax.inject.Inject

class AiActorProvider @Inject()(actorContext: ActorContext[_], aiActor: IAiActor) extends Provider[ActorRef[IAiActor.ICommand[_]]] {
  override def get(): ActorRef[IAiActor.ICommand[_]] = {
    val aiActorRef = actorContext.spawn(aiActor, "AiActor")
    actorContext.watch(aiActorRef)
    aiActorRef
  }
}
