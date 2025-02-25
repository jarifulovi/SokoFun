package com.puzzlegame.sokofun.Logic.GameLogic;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Logic.Handler.PlayerHandler;
import com.puzzlegame.sokofun.Object.Move;
import javafx.scene.input.KeyEvent;

public class Player {

    private int row;
    private int col;
    private final PlayerHandler playerHandler;

    public Player(int startRow, int startCol) {
        this.row = startRow;
        this.col = startCol;
        this.playerHandler = new PlayerHandler();
    }

    public Move getMove(KeyEvent event) {
        playerHandler.updateDirection(event);
        int direction = playerHandler.getDirection();


        int prevRow = row;
        int prevCol = col;
        int newRow = row;
        int newCol = col;

        switch (direction) {
            case GameConstants.UP -> newRow--;
            case GameConstants.DOWN -> newRow++;
            case GameConstants.LEFT -> newCol--;
            case GameConstants.RIGHT -> newCol++;
        }
        return new Move(prevRow,prevCol,newRow,newCol,direction);
    }

    public void updatePosition(Move move) {
        this.row = move.getNewRow();
        this.col = move.getNewCol();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
