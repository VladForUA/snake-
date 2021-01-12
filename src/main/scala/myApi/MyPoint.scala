package myApi

import com.idalko.battle_snake.fw.api.domain.move.Move

class MyPoint(var x: java.lang.Integer, var y: java.lang.Integer,var move: Move) {
   var h: java.lang.Integer = null
   var zone: String = null
   def equals(obj: MyPoint): Boolean = obj.x == x && obj.y == y

  override def toString: String = s"Point(X=${x} Y=${y})"


  def calculateH (point: MyPoint , food : MyPoint): Unit = {
    h = Math.abs(food.x-point.x) + Math.abs(food.y-point.y)
  }
}
