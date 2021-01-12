package com.idalko.battle_snake.fw.api.ai;

import com.idalko.battle_snake.fw.api.domain.move.IMoveRequest;
import com.idalko.battle_snake.fw.api.domain.start.IStartRequest;
import com.idalko.battle_snake.fw.api.domain.start.IStartResponse;
import com.idalko.battle_snake.fw.api.domain.move.IMoveResponse;

import javax.annotation.Nonnull;

public interface IAi {
    @Nonnull IStartResponse getStartResponse(@Nonnull IStartRequest startRequest);
    @Nonnull IMoveResponse makeMove(@Nonnull IMoveRequest moveRequest);
}
