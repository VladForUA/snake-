package com.idalko.battle_snake.fw.json

import com.idalko.battle_snake.fw.api.domain.move.{IMoveResponse, Move}
import com.idalko.battle_snake.fw.api.domain.start.{HeadType, IStartResponse, TailType}
import com.idalko.battle_snake.fw.api.domain.move._
import com.idalko.battle_snake.fw.api.domain.register.RegisterRequest
import com.idalko.battle_snake.fw.api.domain.start._
import com.idalko.battle_snake.fw.api.domain.test.TestRequest
import spray.json.{DefaultJsonProtocol, JsonFormat, RootJsonFormat}

object JsonFormats {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val headFormat: RootJsonFormat[HeadType] = HeadTypeFormat
  implicit val tailFormat: RootJsonFormat[TailType] = TailTypeFormat
  implicit val moveFormat: RootJsonFormat[Move] = MoveFormat
  implicit val pointFormat: RootJsonFormat[Point] = jsonFormat3(Point)
  implicit val snakeFormat: JsonFormat[Snake] = lazyFormat(jsonFormat7(Snake))
  implicit val listOfPointFormat: RootJsonFormat[BsAiFwList[Point]] = jsonFormat2(BsAiFwList[Point])
  implicit val listOfSnakeFormat: RootJsonFormat[BsAiFwList[Snake]] = jsonFormat2(BsAiFwList[Snake])

  implicit val worldFormat: RootJsonFormat[World] = jsonFormat8(World)
  implicit val moveResponseJsonFormat: RootJsonFormat[MoveResponse] = jsonFormat1(MoveResponse)

  implicit val startReqFormat: RootJsonFormat[StartRequest] = jsonFormat3(StartRequest)
  implicit val startResFormat: RootJsonFormat[StartResponse] = jsonFormat7(StartResponse)

  implicit val startResponseFormat: RootJsonFormat[IStartResponse] = JavaStartResponseFormat
  implicit val moveResponseFormat: RootJsonFormat[IMoveResponse] = JavaMoveResponseFormat

  implicit val registerFormat: RootJsonFormat[RegisterRequest] = jsonFormat2(RegisterRequest)
  implicit val testFormat: RootJsonFormat[TestRequest] = jsonFormat1(TestRequest)
}
