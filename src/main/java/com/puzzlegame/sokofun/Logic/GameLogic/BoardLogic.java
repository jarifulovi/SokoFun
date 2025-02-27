package com.puzzlegame.sokofun.Logic.GameLogic;


import com.puzzlegame.sokofun.Logic.Abstract.Utils;
import com.puzzlegame.sokofun.Object.Move;
import static com.puzzlegame.sokofun.Logic.Abstract.GameConstants.*;
import static com.puzzlegame.sokofun.Logic.Abstract.GameConstants.NOTHING;

public class BoardLogic {


    private int[][][] board;
    private final int[][][] initialBoard;
    private int totalRow;
    private int totalCol;
    private LevelLoader levelLoader;

    public BoardLogic(int level) {
        levelLoader = new LevelLoader(level);
        initialBoard = levelLoader.getLevelBoard();
        board = Utils.copyBoard(initialBoard);
        this.totalRow = board[0].length;
        this.totalCol = board[0][0].length;
    }

    public void resetBoard() {
        board = Utils.copyBoard(initialBoard);
    }

    public int[][][] getBoard() {
        return board;
    }
    public int getTotalRows() {
        return this.totalRow;
    }
    public int getTotalCols() {
        return this.totalCol;
    }

    // note block layer has both blocks and boxes
    public boolean isValidMove(Move move) {
        int newRow = move.getNewRow();
        int newCol = move.getNewCol();

        if (!Utils.isWithinBound(newRow, newCol,totalRow,totalCol)) {
            return false;
        }

        int blockType = board[BLOCK_INDEX][newRow][newCol];

        if (Utils.isWall(blockType)) {
            return false;
        }


        if (blockType == BOX) {
            int nextRow = move.getPushedRow();
            int nextCol = move.getPushedCol();

            if (!Utils.isWithinBound(nextRow, nextCol,totalRow,totalCol)) {
                return false;
            }

            int nextBlockType = board[BLOCK_INDEX][nextRow][nextCol];
            return !Utils.isWall(nextBlockType) && nextBlockType != BOX;
        }

        return true;
    }



    public void updateBoard(Move move) {

        int newRow = move.getNewRow();
        int newCol = move.getNewCol();
        move.setIsPush(false);

        if (board[BLOCK_INDEX][newRow][newCol] == BOX) {
            move.setIsPush(true);
            board[BLOCK_INDEX][newRow][newCol] = NOTHING;
            int nextRow = move.getPushedRow();
            int nextCol = move.getPushedCol();
            board[BLOCK_INDEX][nextRow][nextCol] = BOX;
        }

    }


    public boolean isLevelComplete() {
        for (int row = 0; row < totalRow; row++) {
            for (int col = 0; col < totalCol; col++) {
                if (board[BLOCK_INDEX][row][col] == BOX &&
                        !Utils.isGoal(board[OBJECT_INDEX][row][col])) {
                    return false;
                }
            }
        }
        return true;
    }


    public int[] getPlayerPosition() {
        return Utils.getPlayerPosition(board);
    }

}
