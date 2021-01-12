package com.idalko.battle_snake.fw.api.test

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest}
import akka.http.scaladsl.model.HttpMethods._
import com.idalko.battle_snake.fw.api.domain.test.TestRequest
import com.idalko.battle_snake.fw.json.JsonFormats
import javax.inject.Inject
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}

object TestClient {
  private[test] val LOG = LoggerFactory.getLogger(classOf[TestClient])
}
class TestClient @Inject()(implicit val actorSystem: ActorSystem[_], implicit val executionContext: ExecutionContext) {
  import TestClient._
  def test(gameServerUrl: String, testRequest: TestRequest): Future[None.type] = {
    LOG.info(s"#test sending: POST $gameServerUrl/test")
    Http()
      .singleRequest(HttpRequest(
        POST, gameServerUrl+"/test", entity = HttpEntity(
          ContentTypes.`application/json`,
          JsonFormats
            .testFormat
            .write(testRequest)
            .toString()
        )
      ))
      .map { response =>
        LOG.info(s"#test sent: POST $gameServerUrl/test\n${response.toString()}")
        None
      }
  }
}
