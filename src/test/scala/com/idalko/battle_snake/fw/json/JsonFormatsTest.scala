package com.idalko.battle_snake.fw.json

import org.scalatest.BeforeAndAfter
import org.scalatest.funsuite.AnyFunSuite
import spray.json.{DefaultJsonProtocol, JsonParser, ParserInput}

class JsonFormatsTest extends AnyFunSuite with BeforeAndAfter {
  test("""read world""") {
    val jsonStr = """{"you":{"taunt":"Hiss","object":"snake","name":"snek","length":3,"id":"d50264da-e057-49a6-b84a-a75f1c371639","health":100,"body":{"object":"list","data":[{"y":14,"x":11,"object":"point"},{"y":14,"x":11,"object":"point"},{"y":14,"x":11,"object":"point"}]}},"width":20,"turn":0,"snakes":{"object":"list","data":[{"taunt":"Hiss","object":"snake","name":"snek","length":3,"id":"d50264da-e057-49a6-b84a-a75f1c371639","health":100,"body":{"object":"list","data":[{"y":14,"x":11,"object":"point"},{"y":14,"x":11,"object":"point"},{"y":14,"x":11,"object":"point"}]}}]},"object":"world","id":14,"height":20,"food":{"object":"list","data":[{"y":9,"x":18,"object":"point"}]}}"""
    val json = JsonParser(ParserInput(jsonStr)).asJsObject
    JsonFormats.worldFormat.read(json)
  }
  test("""read snake""") {
    val jsonStr = """{
                    |    "taunt": null,
                    |    "object": "snake",
                    |    "name": "snek",
                    |    "length": 3,
                    |    "id": "33e36b8f-441e-4eaf-96e4-6f1d4abf12f3",
                    |    "health": 99,
                    |    "body": {
                    |      "object": "list",
                    |      "data": [
                    |        {
                    |          "y": 9,
                    |          "x": 1,
                    |          "object": "point"
                    |        },
                    |        {
                    |          "y": 10,
                    |          "x": 1,
                    |          "object": "point"
                    |        },
                    |        {
                    |          "y": 10,
                    |          "x": 1,
                    |          "object": "point"
                    |        }
                    |      ]
                    |    }
                    |  }""".stripMargin
    val json = JsonParser(ParserInput(jsonStr)).asJsObject
    JsonFormats.snakeFormat.read(json)
  }
  test("""read list of snakes""") {
    val jsonStr = """{
                    |    "object": "list",
                    |    "data": [
                    |      {
                    |        "taunt": null,
                    |        "object": "snake",
                    |        "name": "snek",
                    |        "length": 3,
                    |        "id": "33e36b8f-441e-4eaf-96e4-6f1d4abf12f3",
                    |        "health": 99,
                    |        "body": {
                    |          "object": "list",
                    |          "data": [
                    |            {
                    |              "y": 9,
                    |              "x": 1,
                    |              "object": "point"
                    |            },
                    |            {
                    |              "y": 10,
                    |              "x": 1,
                    |              "object": "point"
                    |            },
                    |            {
                    |              "y": 10,
                    |              "x": 1,
                    |              "object": "point"
                    |            }
                    |          ]
                    |        }
                    |      }
                    |    ]
                    |  }""".stripMargin
    val json = JsonParser(ParserInput(jsonStr)).asJsObject
    JsonFormats.listOfSnakeFormat.read(json)
  }
  test("""read list of food""") {
    val jsonStr = """{
                    |    "object": "list",
                    |    "data": [
                    |      {
                    |        "y": 2,
                    |        "x": 1,
                    |        "object": "point"
                    |      }
                    |    ]
                    |  }""".stripMargin
    val json = JsonParser(ParserInput(jsonStr)).asJsObject
    JsonFormats.listOfPointFormat.read(json)
  }
}
