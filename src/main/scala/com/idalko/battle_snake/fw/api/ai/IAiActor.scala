package com.idalko.battle_snake.fw.api.ai

import akka.actor.typed.{ActorRef, ExtensibleBehavior}
import com.google.inject.ImplementedBy
import com.idalko.battle_snake.fw.api.domain.move.IMoveResponse
import com.idalko.battle_snake.fw.api.domain.move.World
import com.idalko.battle_snake.fw.api.domain.start.IStartResponse
import com.idalko.battle_snake.fw.api.domain.start.StartRequest

object IAiActor {
  sealed trait ICommand[R] {
    def replyTo: ActorRef[R]
  }
  case class GetStartResponse(startRequest: StartRequest, override val replyTo: ActorRef[IStartResponse]) extends ICommand[IStartResponse]
  case class GetMoveResponse(moveRequest: World, override val replyTo: ActorRef[IMoveResponse]) extends ICommand[IMoveResponse]
}
@ImplementedBy(classOf[com.idalko.battle_snake.fw.ai.AiActor])
trait IAiActor extends ExtensibleBehavior[IAiActor.ICommand[_]]
