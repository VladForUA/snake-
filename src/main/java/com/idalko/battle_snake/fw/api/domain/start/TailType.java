package com.idalko.battle_snake.fw.api.domain.start;

public enum TailType {
    BLOCK_BUM,
    CURLED,
    FAT_RATTLE,
    FRECKLED,
    PIXEL,
    REGULAR,
    ROUND_BUM,
    SKINNY,
    SMALL_RATTLE;

    public String getApiRepresentation() {
        return this
                .name()
                .toLowerCase()
                .replaceAll("_", "-");
    }
}
