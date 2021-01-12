package com.idalko.battle_snake.fw.api.domain.move

/*
  {
    "y": 2,
    "x": 1,
    "object": "point"
  }
 */
case class Point(x: Int, y: Int, `object`: String = "point") extends IPoint {
  override def getObject: String = `object`

  override def getX: Int = x

  override def getY: Int = y
}
