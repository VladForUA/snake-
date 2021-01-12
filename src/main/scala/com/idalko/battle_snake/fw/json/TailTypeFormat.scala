package com.idalko.battle_snake.fw.json

import com.idalko.battle_snake.fw.api.domain.start.TailType
import spray.json.{JsString, JsValue, RootJsonFormat}

object TailTypeFormat extends RootJsonFormat[TailType] {
  override def write(obj: TailType): JsValue = JsString(obj.getApiRepresentation)

  override def read(json: JsValue): TailType = json match {
    case JsString(str) => TailType
      .values()
      .find(_.getApiRepresentation == str)
      .getOrElse(TailType.REGULAR)
  }
}
