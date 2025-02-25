package com.puzzlegame.sokofun.Object;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;

public class Move {
    private int prevRow;
    private int prevCol;
    private int newRow;
    private int newCol;
    private int direction;

    public Move(int prevRow, int prevCol, int newRow, int newCol, int direction) {
        this.prevRow = prevRow;
        this.prevCol = prevCol;
        this.newRow = newRow;
        this.newCol = newCol;
        this.direction = direction;
    }

    public int getPrevRow() {
        return prevRow;
    }

    public int getPrevCol() {
        return prevCol;
    }

    public int getNewRow() {
        return newRow;
    }

    public int getNewCol() {
        return newCol;
    }

    public int getDirection() {
        return direction;
    }


    public int getDirectionRow() {
        return switch (direction) {
            case GameConstants.UP -> -1;
            case GameConstants.DOWN -> 1;
            default -> 0;
        };
    }

    public int getDirectionCol() {
        return switch (direction) {
            case GameConstants.LEFT -> -1;
            case GameConstants.RIGHT -> 1;
            default -> 0;
        };
    }
}

