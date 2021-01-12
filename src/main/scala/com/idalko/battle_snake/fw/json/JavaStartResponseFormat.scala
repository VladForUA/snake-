package com.idalko.battle_snake.fw.json

import com.idalko.battle_snake.fw.api.domain.start.IStartResponse
import com.idalko.battle_snake.fw.api.domain.start.StartResponse
import javax.inject.Inject
import spray.json.{JsValue, RootJsonFormat}

object JavaStartResponseFormat extends RootJsonFormat[IStartResponse] {
  override def write(obj: IStartResponse): JsValue = obj match {
    case sr: StartResponse =>
      JsonFormats
        .startResFormat
        .write(sr)
    case _ => JsonFormats
      .startResFormat
      .write(
        StartResponse(
        obj.getColor,
        obj.getName,
        obj.getHeadUrl,
        obj.getTaunt,
        obj.getHeadType,
        obj.getTailType,
        obj.getSecondaryColor
      )
    )
  }

  override def read(json: JsValue): IStartResponse = JsonFormats
    .startResFormat
    .read(json)
}
