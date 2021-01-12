package com.idalko.battle_snake.fw.api.domain.move;

public enum Move {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public String getApiRepresentation() {
        return this
                .name()
                .toLowerCase();
    }
}
