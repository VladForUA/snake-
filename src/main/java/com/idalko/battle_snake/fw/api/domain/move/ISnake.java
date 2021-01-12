package com.idalko.battle_snake.fw.api.domain.move;

import javax.annotation.Nullable;

public interface ISnake {
    /*
      body: List<Point>;
  health: number;
  id: string;
  length: number;
  name: string;
  object: 'snake';
  taunt: string;
     */
    IList<IPoint> getBody();
    int getHealth();
    String getId();
    int getLength();
    String getName();
    String getObject();
    @Nullable String getTaunt();
}
