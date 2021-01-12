package com.idalko.battle_snake.fw.json

import com.idalko.battle_snake.fw.api.domain.start.HeadType
import spray.json.{JsString, JsValue, RootJsonFormat}

object HeadTypeFormat extends RootJsonFormat[HeadType] {
  override def write(obj: HeadType): JsValue = JsString(obj.getApiRepresentation)

  override def read(json: JsValue): HeadType = json match {
    case JsString(str) => HeadType
      .values()
      .find(_.getApiRepresentation == str)
      .getOrElse(HeadType.REGULAR)
  }
}
