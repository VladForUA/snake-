package com.idalko.battle_snake.fw.api.routes

import akka.http.scaladsl.server.Route
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[com.idalko.battle_snake.fw.routes.HttpRoutes])
trait IHttpRoutes {
  def routes: Route
}
