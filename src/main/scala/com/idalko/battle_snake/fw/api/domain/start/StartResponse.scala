package com.idalko.battle_snake.fw.api.domain.start

case class StartResponse (color: String,
                          name: String,
                          /**
                            * URL of the image to display as your avatar.
                            */
                          head_url: String,
                          taunt: String,
                          head_type: HeadType,
                          tail_type: TailType,
                          secondary_color: String) extends IStartResponse {
  override def getColor: String = color

  override def getSecondaryColor: String = secondary_color

  override def getName: String = name

  override def getHeadUrl: String = head_url

  override def getTaunt: String = taunt

  override def getHeadType: HeadType = head_type

  override def getTailType: TailType = tail_type
}
