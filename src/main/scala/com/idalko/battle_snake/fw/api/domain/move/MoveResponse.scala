package com.idalko.battle_snake.fw.api.domain.move

import com.idalko.battle_snake.fw.api.domain.move.{IMoveResponse, Move}

case class MoveResponse(move: Move) extends IMoveResponse {
  override def getMove: Move = move
}
