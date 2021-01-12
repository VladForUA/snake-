package com.idalko.battle_snake.fw.api.domain.move


/*
{
  "you": {...Snake},
  "width": 20,
  "turn": 1,
  "snakes": {
    "object": "list",
    "data": [
      {...Snake}
    ]
  },
  "object": "world",
  "id": 12,
  "height": 20,
  "food": {
    "object": "list",
    "data": [
      {
        "y": 2,
        "x": 1,
        "object": "point"
      }
    ]
  }
}
 */
case class World(id: Long, you: Snake, snakes: BsAiFwList[Snake], height: Int, width: Int, turn: Int, food: BsAiFwList[Point], `object`: String = "world") extends IMoveRequest {
  override def getObject: String = `object`

  override def getId: Long = id

  override def getYou: ISnake = you

  override def getSnakes: IList[ISnake] = snakes
    .map(_.asInstanceOf[ISnake])

  override def getHeight: Int = height

  override def getWidth: Int = width

  override def getTurn: Int = turn

  override def getFood: IList[IPoint] = food
    .map(_.asInstanceOf[IPoint])
}
