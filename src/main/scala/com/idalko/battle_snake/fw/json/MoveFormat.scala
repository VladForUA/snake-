package com.idalko.battle_snake.fw.json

import com.idalko.battle_snake.fw.api.domain.move.Move
import spray.json.{JsString, JsValue, RootJsonFormat}

object MoveFormat extends RootJsonFormat[Move] {
  override def write(obj: Move): JsValue = JsString(obj.getApiRepresentation)

  override def read(json: JsValue): Move = json match {
    case JsString(moveStr) => Move
      .values()
      .find(_.getApiRepresentation == moveStr)
      .getOrElse(Move.UP)
  }
}
