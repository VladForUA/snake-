package com.idalko.battle_snake.fw.api.domain.start;

public interface IStartResponse {

    /**
     * What color your snake should be on the board.
     * Accepts any valid CSS color.
     * https://developer.mozilla.org/en-US/docs/Web/CSS/color
     * */
    String getColor();

    /**
     * Accepts any valid CSS color.
     * https://developer.mozilla.org/en-US/docs/Web/CSS/color
     * */
    String getSecondaryColor();

    String getName();

    String getHeadUrl();

    String getTaunt();

    HeadType getHeadType();

    TailType getTailType();
}
