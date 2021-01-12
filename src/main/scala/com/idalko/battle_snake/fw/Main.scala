package com.idalko.battle_snake.fw

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.idalko.battle_snake.fw.api.ai.IAi
import com.idalko.battle_snake.fw.api.domain.move.{IMoveRequest, IMoveResponse, IPoint, Move, MoveResponse}
import com.idalko.battle_snake.fw.api.domain.start._
import com.idalko.battle_snake.fw.api.server.SnakeAiServer
import myApi.MyPoint
import org.slf4j.LoggerFactory

import scala.collection.convert.ImplicitConversions.`list asScalaBuffer`


object Main {
  val LOG = LoggerFactory.getLogger("com.idalko.battle_snake.fw.Main")
  val neiList  = new scala.collection.mutable.ListBuffer[MyPoint]{
      def contains(elem: MyPoint): Boolean = exists (_.equals(elem))
  }
  val bodylist = new scala.collection.mutable.ListBuffer[MyPoint]{
    def contains(elem: MyPoint): Boolean = exists (_.equals(elem))
  }


  ///////////////////////////////////////////////////////////////////
  var food: MyPoint = null
  var neack: MyPoint = null
  var headOfSnake: MyPoint = null
  var neiFromTop: MyPoint = null
  var neiFromBottom: MyPoint = null
  var neiFromLeft: MyPoint = null
  var neiFromRight: MyPoint = null
  var world: IMoveRequest = null





    // new ALGORITHM varibles

   var chekPointA: MyPoint =  new MyPoint(0,19,null)
   var chekPointB: MyPoint =  new MyPoint(19,19,null)
   var chekPointC: MyPoint =  new MyPoint(19,0,null)
   var chekPointD: MyPoint =  new MyPoint(0,0,null)
   var nextChekPoint: String = "A"
    var startPoint: MyPoint = new MyPoint(0, 9,null)
    var movesAfterSnakeStayOnTheCenterPoin = new scala.collection.mutable.ListBuffer[MyPoint]{
        def contains(elem: MyPoint): Boolean = exists (_.equals(elem))
    }
    var checkPoints = new scala.collection.mutable.ListBuffer[MyPoint]{
        def contains(elem: MyPoint): Boolean = exists (_.equals(elem))
    }
    var workListWithCheckPoints = new scala.collection.mutable.ListBuffer[MyPoint]{
        def contains(elem: MyPoint): Boolean = exists (_.equals(elem))
    }
    var checkList = new scala.collection.mutable.ListBuffer[MyPoint]{
        def contains(elem: MyPoint): Boolean = exists (_.equals(elem))
    }
    checkPoints.addAll(Iterable(chekPointA,chekPointB,chekPointC,chekPointD).toBuffer)
    var isSnakeOnTheStartPosition: Boolean = false
    var isSnakeAteAnApple: Boolean = false
    var canGoCycleAgain: Boolean = false






    ////////



  def main(args: Array[String]): Unit = {

      LOG.info(s"#main started at `${LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)}` with args: ${args.mkString(" ")}")
    SnakeAiServer
      .start(new IAi {
          override def getStartResponse(startRequest: IStartRequest): IStartResponse = {
              StartResponse(
            "#0052CC",
            "Vlad",
            "https://raw.githubusercontent.com/VladForUA/vladforua.github.io/master/mdn/video-player/IMG_1260.JPG",
            "Hiss",
            HeadType.SMILE,
            TailType.ROUND_BUM,
            "#172B4D")
          }


          override def makeMove(moveRequest: IMoveRequest): IMoveResponse = {
              if(moveRequest.getYou.getBody.getData.length < 20){
                  clearWorld()
                  getData(moveRequest)
              }else{
                  cycleAlgo(moveRequest)

              }
              println(s"LENGHT ${bodylist.length}")
              MoveResponse(neiList.head.move)

          }
        })
    }








        // NEW ALGO METHODS
        def makeMovesToTheStartPoint () = {
            if (headOfSnake.y - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y - 1, null))) {
                neiFromTop = new MyPoint(headOfSnake.x, headOfSnake.y - 1, Move.UP)
            }
            else {
                neiFromTop = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.UP)
            }
            if (headOfSnake.y + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y + 1, null))) {
                neiFromBottom = new MyPoint(headOfSnake.x, headOfSnake.y + 1, Move.DOWN)
            }
            else {
                neiFromBottom = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.DOWN)
            }
            if (headOfSnake.x - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x - 1, headOfSnake.y, null))) {
                neiFromLeft = new MyPoint(headOfSnake.x - 1, headOfSnake.y, Move.LEFT)
            }
            else {
                neiFromLeft = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.LEFT)
            }
            if (headOfSnake.x + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x + 1, headOfSnake.y, null))) {
                neiFromRight = new MyPoint(headOfSnake.x + 1, headOfSnake.y, Move.RIGHT)
            }
            else {
                neiFromRight = new MyPoint(headOfSnake.x + 100000, headOfSnake.y + 100000, Move.RIGHT)
            }
            Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).foreach(e => e.calculateH(e, startPoint))
            neiList.addAll(Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).toBuffer.sortWith(_.h < _.h))
        }
        def goToTheStartPoint() = {
            if(bodylist.head.equals(startPoint) && isSnakeOnTheStartPosition == false){
                println(s"Snake On THE Start Point ")
                neiList.clear()
                neiList.addOne(new MyPoint(0,0,Move.LEFT))
                isSnakeOnTheStartPosition = true
            }else if (isSnakeOnTheStartPosition == false){
                makeMovesToTheStartPoint()
            }

        }
        def goToTheCyclePoint (point: MyPoint) = {
        if (headOfSnake.y - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y - 1, null))) {
            neiFromTop = new MyPoint(headOfSnake.x, headOfSnake.y - 1, Move.UP)
        }
        else {
            neiFromTop = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.UP)
        }
        if (headOfSnake.y + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y + 1, null))) {
            neiFromBottom = new MyPoint(headOfSnake.x, headOfSnake.y + 1, Move.DOWN)
        }
        else {
            neiFromBottom = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.DOWN)
        }
        if (headOfSnake.x - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x - 1, headOfSnake.y, null))) {
            neiFromLeft = new MyPoint(headOfSnake.x - 1, headOfSnake.y, Move.LEFT)
        }
        else {
            neiFromLeft = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.LEFT)
        }
        if (headOfSnake.x + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x + 1, headOfSnake.y, null))) {
            neiFromRight = new MyPoint(headOfSnake.x + 1, headOfSnake.y, Move.RIGHT)
        }
        else {
            neiFromRight = new MyPoint(headOfSnake.x + 100000, headOfSnake.y + 100000, Move.RIGHT)
        }
        Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).foreach(e => e.calculateH(e, point))
        neiList.addAll(Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).toBuffer.sortWith(_.h < _.h))
    }
        def goToTheFoodCycleVersion (food: MyPoint) = {
        if (headOfSnake.y - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y - 1, null))) {
            neiFromTop = new MyPoint(headOfSnake.x, headOfSnake.y - 1, Move.UP)
        }
        else {
            neiFromTop = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.UP)
        }
        if (headOfSnake.y + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y + 1, null))) {
            neiFromBottom = new MyPoint(headOfSnake.x, headOfSnake.y + 1, Move.DOWN)
        }
        else {
            neiFromBottom = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.DOWN)
        }
        if (headOfSnake.x - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x - 1, headOfSnake.y, null))) {
            neiFromLeft = new MyPoint(headOfSnake.x - 1, headOfSnake.y, Move.LEFT)
        }
        else {
            neiFromLeft = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.LEFT)
        }
        if (headOfSnake.x + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x + 1, headOfSnake.y, null))) {
            neiFromRight = new MyPoint(headOfSnake.x + 1, headOfSnake.y, Move.RIGHT)
        }
        else {
            neiFromRight = new MyPoint(headOfSnake.x + 100000, headOfSnake.y + 100000, Move.RIGHT)
        }
        Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).foreach(e => e.calculateH(e, food))
        neiList.addAll(Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).toBuffer.sortWith(_.h < _.h))
    }
        def goToThePoint (point: MyPoint) = {
        if (headOfSnake.y - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y - 1, null))) {
            neiFromTop = new MyPoint(headOfSnake.x, headOfSnake.y - 1, Move.UP)
        }
        else {
            neiFromTop = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.UP)
        }
        if (headOfSnake.y + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y + 1, null))) {
            neiFromBottom = new MyPoint(headOfSnake.x, headOfSnake.y + 1, Move.DOWN)
        }
        else {
            neiFromBottom = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.DOWN)
        }
        if (headOfSnake.x - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x - 1, headOfSnake.y, null))) {
            neiFromLeft = new MyPoint(headOfSnake.x - 1, headOfSnake.y, Move.LEFT)
        }
        else {
            neiFromLeft = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.LEFT)
        }
        if (headOfSnake.x + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x + 1, headOfSnake.y, null))) {
            neiFromRight = new MyPoint(headOfSnake.x + 1, headOfSnake.y, Move.RIGHT)
        }
        else {
            neiFromRight = new MyPoint(headOfSnake.x + 100000, headOfSnake.y + 100000, Move.RIGHT)
        }
        Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).foreach(e => e.calculateH(e, point))
        neiList.addAll(Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).toBuffer.sortWith(_.h < _.h))
    }
        def cycleAlgo(moveRequest: IMoveRequest) = {
            clearWorld()
            food = new MyPoint(moveRequest.getFood.getData.get(0).getX, moveRequest.getFood.getData.get(0).getY, null)
            bodylist.addAll(moveRequest.getYou.getBody.getData.map(e => new MyPoint(e.getX, e.getY, null)))
            headOfSnake = new MyPoint(moveRequest.getYou.getBody.getData.get(0).getX, moveRequest.getYou.getBody.getData.get(0).getY, null)
            goToTheStartPoint()
            if(workListWithCheckPoints.length == 0 ){
                workListWithCheckPoints.addAll(checkPoints)
            }
            if(headOfSnake.equals(chekPointA)){
                nextChekPoint = "B"
            }
            else if (headOfSnake.equals(chekPointB)){
                nextChekPoint = "C"
            }
            else if (headOfSnake.equals(chekPointC)){
                nextChekPoint = "D"
            }
            else if (headOfSnake.equals(chekPointD)){
                nextChekPoint = "A"
            }
            if( isSnakeOnTheStartPosition == true && nextChekPoint == "A"){
                neiList.clear()
                neiList.addOne(new MyPoint(0,0,Move.DOWN))
            }
            else if (isSnakeOnTheStartPosition == true && nextChekPoint == "B"){
                checkList.clear()
                checkList.addAll(List.range(9,19).map(point => new MyPoint(headOfSnake.x,point,null)).toBuffer)
                if(checkList.contains(food)){
                    goToTheFoodCycleVersion(food)
                }
                else{
                    if(headOfSnake.x + 1 == 19 && bodylist.contains(new MyPoint(headOfSnake.x,headOfSnake.y + 1,null))){
                        neiList.clear()
                        neiList.addOne(new MyPoint(0,0,Move.RIGHT))
                        nextChekPoint = "C"

                    }else{
                        goToTheCyclePoint(new MyPoint(headOfSnake.x + 1,19,null))
                    }
                }
            }
            else if (isSnakeOnTheStartPosition == true && nextChekPoint == "C"){
                neiList.clear()
                neiList.addOne(new MyPoint(0,0,Move.UP))
            }
            else if (isSnakeOnTheStartPosition == true && nextChekPoint == "D"){
                checkList.clear()
                checkList.addAll(List.range(0,10).map(point => new MyPoint(headOfSnake.x,point,null)).toBuffer)
                if(checkList.contains(food) ){
                    goToTheFoodCycleVersion(food)
                }
                else{
                    if(headOfSnake.x - 1 == 0 && bodylist.contains(new MyPoint(headOfSnake.x,headOfSnake.y - 1,null))){
                        neiList.clear()
                        neiList.addOne(new MyPoint(0,0,Move.LEFT))
                        nextChekPoint = "A"

                    }else{
                        goToTheCyclePoint(new MyPoint(headOfSnake.x - 1,0,null))
                    }
                }

            }
        }
        /////

    //OLD ALGOS METHODS
    def goToTheFood () = {
        if (headOfSnake.y - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y - 1, null))) {
            neiFromTop = new MyPoint(headOfSnake.x, headOfSnake.y - 1, Move.UP)
        }
        else {
            neiFromTop = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.UP)
        }
        if (headOfSnake.y + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y + 1, null))) {
            neiFromBottom = new MyPoint(headOfSnake.x, headOfSnake.y + 1, Move.DOWN)
        }
        else {
            neiFromBottom = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.DOWN)
        }
        if (headOfSnake.x - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x - 1, headOfSnake.y, null))) {
            neiFromLeft = new MyPoint(headOfSnake.x - 1, headOfSnake.y, Move.LEFT)
        }
        else {
            neiFromLeft = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.LEFT)
        }
        if (headOfSnake.x + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x + 1, headOfSnake.y, null))) {
            neiFromRight = new MyPoint(headOfSnake.x + 1, headOfSnake.y, Move.RIGHT)
        }
        else {
            neiFromRight = new MyPoint(headOfSnake.x + 100000, headOfSnake.y + 100000, Move.RIGHT)
        }
        Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).foreach(e => e.calculateH(e, food))
        neiList.addAll(Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).toBuffer.sortWith(_.h < _.h))
    }
    def clearWorld (): Unit = {
      headOfSnake = null
      food = null
      neack = null
      neiFromTop = null
      neiFromBottom = null
      neiFromLeft = null
      neiFromRight = null
      bodylist.clear()
      neiList.clear()
    }
    def calculateH (point: MyPoint , food : MyPoint): Integer = {
        Math.abs(food.x-point.x) + Math.abs(food.y-point.y)
    }
    def getData(moveRequest: IMoveRequest): Unit = {

        food = new MyPoint(moveRequest.getFood.getData.get(0).getX, moveRequest.getFood.getData.get(0).getY, null)
        bodylist.addAll(moveRequest.getYou.getBody.getData.map(e => new MyPoint(e.getX, e.getY, null)))
        headOfSnake = new MyPoint(moveRequest.getYou.getBody.getData.get(0).getX, moveRequest.getYou.getBody.getData.get(0).getY, null)


        if (headOfSnake.y - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y - 1, null))) {
            neiFromTop = new MyPoint(headOfSnake.x, headOfSnake.y - 1, Move.UP)
        }
        else {
            neiFromTop = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.UP)
        }
        if (headOfSnake.y + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x, headOfSnake.y + 1, null))) {
            neiFromBottom = new MyPoint(headOfSnake.x, headOfSnake.y + 1, Move.DOWN)
        }
        else {
            neiFromBottom = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.DOWN)
        }
        if (headOfSnake.x - 1 >= 0 && !bodylist.contains(new MyPoint(headOfSnake.x - 1, headOfSnake.y, null))) {
            neiFromLeft = new MyPoint(headOfSnake.x - 1, headOfSnake.y, Move.LEFT)
        }
        else {
            neiFromLeft = new MyPoint(headOfSnake.x + 10000, headOfSnake.y + 10000, Move.LEFT)
        }
        if (headOfSnake.x + 1 < 20 && !bodylist.contains(new MyPoint(headOfSnake.x + 1, headOfSnake.y, null))) {
            neiFromRight = new MyPoint(headOfSnake.x + 1, headOfSnake.y, Move.RIGHT)
        }
        else {
            neiFromRight = new MyPoint(headOfSnake.x + 100000, headOfSnake.y + 100000, Move.RIGHT)
        }
        Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).foreach(e => e.calculateH(e, food))
        neiList.addAll(Iterable(neiFromTop, neiFromBottom, neiFromLeft, neiFromRight).toBuffer.sortWith(_.h < _.h))
    }
}

