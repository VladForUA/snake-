package com.idalko.battle_snake.fw.api.domain.start;

public enum HeadType {
    BENDR,
    DEAD,
    FANG,
    PIXEL,
    REGULAR,
    SAFE,
    SAND_WORM,
    SHADES,
    SMILE,
    TONGUE;

    public String getApiRepresentation() {
        return this
                .name()
                .toLowerCase()
                .replaceAll("_", "-");
    }
}
