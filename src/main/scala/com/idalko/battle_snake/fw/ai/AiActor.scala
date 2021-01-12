package com.idalko.battle_snake.fw.ai

import akka.actor.typed.{Behavior, Signal, TypedActorContext}
import akka.actor.typed.scaladsl.Behaviors
import com.idalko.battle_snake.fw.api.ai.IAi
import com.idalko.battle_snake.fw.api.ai.IAiActor
import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class AiActor @Inject()(ai: IAi, implicit val executionContext: ExecutionContext) extends IAiActor {
  import IAiActor._
  override def receive(ctx: TypedActorContext[IAiActor.ICommand[_]], msg: IAiActor.ICommand[_]): Behavior[IAiActor.ICommand[_]] = msg match {
    case GetStartResponse(startRequest, replyTo) =>
      Future {
        val response = ai.getStartResponse(startRequest)
        replyTo ! response
      }
      Behaviors.same[IAiActor.ICommand[_]]
    case GetMoveResponse(moveRequest, replyTo) =>
      Future {
        val response = ai.makeMove(moveRequest)
        replyTo ! response
      }
      Behaviors.same[IAiActor.ICommand[_]]
  }

  override def receiveSignal(ctx: TypedActorContext[IAiActor.ICommand[_]], msg: Signal): Behavior[IAiActor.ICommand[_]] =
    Behaviors.same[IAiActor.ICommand[_]]
}
