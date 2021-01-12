package com.idalko.battle_snake.fw.api.domain.start

case class StartRequest(game_id: Int, width: Int, height: Int) extends IStartRequest {
  override def getGameId: Int = game_id

  override def getWidth: Int = width

  override def getHeight: Int = height
}
