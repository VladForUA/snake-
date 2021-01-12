package com.idalko.battle_snake.fw.di

import com.google.inject.AbstractModule
import com.typesafe.config.{Config, ConfigFactory}

class MainModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Config])
      .toInstance(ConfigFactory.defaultApplication())
  }
}
