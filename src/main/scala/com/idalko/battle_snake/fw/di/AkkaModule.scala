package com.idalko.battle_snake.fw.di

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.ActorContext
import com.google.inject.{AbstractModule, Provides}

import scala.concurrent.ExecutionContext

class AkkaModule(context: ActorContext[Nothing]) extends AbstractModule {
  override def configure(): Unit = ()

  @Provides
  def actorContext: ActorContext[_] = context

  @Provides
  def actorSystem: ActorSystem[_] = context.system

  @Provides
  def executionContext: ExecutionContext = context
    .system
    .executionContext
}
