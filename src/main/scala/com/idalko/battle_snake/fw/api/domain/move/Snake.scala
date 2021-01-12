package com.idalko.battle_snake.fw.api.domain.move

import com.idalko.battle_snake.fw.api.domain.move.{IList, IPoint, ISnake}

/*
{
    "taunt": null,
    "object": "snake",
    "name": "snek",
    "length": 3,
    "id": "33e36b8f-441e-4eaf-96e4-6f1d4abf12f3",
    "health": 99,
    "body": {
      "object": "list",
      "data": [
        {
          "y": 9,
          "x": 1,
          "object": "point"
        },
        {
          "y": 10,
          "x": 1,
          "object": "point"
        },
        {
          "y": 10,
          "x": 1,
          "object": "point"
        }
      ]
    }
  }
 */
case class Snake(body: BsAiFwList[Point], health: Int, id: String, length: Int, name: String, taunt: Option[String], `object`: String = "snake") extends ISnake {
  override def getBody: IList[IPoint] = body
    .map(_.asInstanceOf[IPoint])

  override def getHealth: Int = health

  override def getId: String = id

  override def getLength: Int = length

  override def getName: String = name

  override def getObject: String = `object`

  override def getTaunt: String = taunt.orNull
}
