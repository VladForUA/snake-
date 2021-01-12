package com.idalko.battle_snake.fw.json

import com.idalko.battle_snake.fw.api.domain.move.IMoveResponse
import com.idalko.battle_snake.fw.api.domain.move.MoveResponse
import javax.inject.Inject
import spray.json.{JsValue, RootJsonFormat}

object JavaMoveResponseFormat extends RootJsonFormat[IMoveResponse]{
  override def write(obj: IMoveResponse): JsValue = obj match {
    case mr: MoveResponse =>
      JsonFormats
        .moveResponseJsonFormat
        .write(mr)
    case _ =>
      JsonFormats
      .moveResponseJsonFormat
      .write(MoveResponse(
        obj.getMove
      ))
  }

  override def read(json: JsValue): IMoveResponse = JsonFormats
    .moveResponseJsonFormat
    .read(json)
}
