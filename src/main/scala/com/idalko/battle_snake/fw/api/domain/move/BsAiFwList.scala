package com.idalko.battle_snake.fw.api.domain.move

import scala.jdk.CollectionConverters._

case class BsAiFwList[T](data: Seq[T], `object`: String = "list") extends IList[T] {
  override def getObject: String = `object`

  override def getData: java.util.List[T] = data
    .asJava

  def map[U](fn: T => U): BsAiFwList[U] =
    copy(data = data.map(fn))

  def flatMap[U](fn: T => IList[U]): BsAiFwList[U] =
    copy(data = data.flatMap{e =>
      val res = fn(e)
      res
        .getData
        .asScala
    })
}
