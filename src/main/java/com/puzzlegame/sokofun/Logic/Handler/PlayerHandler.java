package com.puzzlegame.sokofun.Logic.Handler;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlayerHandler {

    private int direction;

    public PlayerHandler() {
        this.direction = GameConstants.DOWN;
    }

    public void updateDirection(KeyEvent event) {

        KeyCode key = event.getCode();
        switch (key) {
            case W, UP -> direction = GameConstants.UP;
            case S, DOWN -> direction = GameConstants.DOWN;
            case A, LEFT -> direction = GameConstants.LEFT;
            case D, RIGHT -> direction = GameConstants.RIGHT;
        }

    }


    public int getDirection() {
        return direction;
    }
}

