package com.idalko.battle_snake.fw.api.domain.move;

/*
interface World {
  object: 'world';
  id: number;
  you: Snake;
  snakes: List<Snake>;
  height: number;
  width: number;
  turn: number;
  food: List<Point>;
}
 */
public interface IWorld {
    String getObject();
    long getId();
    ISnake getYou();
    IList<ISnake> getSnakes();
    int getHeight();
    int getWidth();
    int getTurn();
    IList<IPoint> getFood();
}
