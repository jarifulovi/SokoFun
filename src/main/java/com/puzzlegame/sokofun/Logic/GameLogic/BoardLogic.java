package com.puzzlegame.sokofun.Logic.GameLogic;

import com.puzzlegame.sokofun.Object.Move;
import static com.puzzlegame.sokofun.Logic.Abstract.GameConstants.*;
import static com.puzzlegame.sokofun.Logic.Abstract.GameConstants.NOTHING;

public class BoardLogic {

    private static final int[][] groundLayer = {
            {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR},
            {FLOOR, SAND, SAND, SAND, FLOOR},
            {FLOOR, SAND, FLOOR, SAND, FLOOR},
            {FLOOR, SAND, SAND, SAND, FLOOR},
            {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR}
    };

    private static final int[][] blockLayer = {
            {STONE, STONE, STONE, STONE, STONE},
            {STONE, NOTHING, NOTHING, STONE, STONE},
            {STONE, NOTHING, BOX, NOTHING, NOTHING},
            {STONE, NOTHING, BOX, STONE, STONE},
            {STONE, STONE, STONE, STONE, STONE}
    };


    private static final int[][] objectLayer = {
            {NOTHING, NOTHING, NOTHING, NOTHING, NOTHING},
            {NOTHING, PLAYER, NOTHING, NOTHING, NOTHING},
            {NOTHING, NOTHING, NOTHING, RED_GOAL, NOTHING},
            {NOTHING, NOTHING, NOTHING, NOTHING, NOTHING},
            {NOTHING, NOTHING, NOTHING, NOTHING, NOTHING}
    };

    private int[][][] board;

    public BoardLogic() {
        initializeBoard();
    }


    // note block layer has both blocks and boxes
    public boolean isValidMove(Move move) {
        int newRow = move.getNewRow();
        int newCol = move.getNewCol();

        if (!isWithinBound(newRow, newCol)) {
            return false;
        }

        int blockType = board[BLOCK_INDEX][newRow][newCol];

        if (isBlock(blockType)) {
            return false;
        }


        if (blockType == BOX) {
            int nextRow = newRow + move.getDirectionRow();
            int nextCol = newCol + move.getDirectionCol();

            if (!isWithinBound(nextRow, nextCol)) {
                return false;
            }

            int nextBlockType = board[BLOCK_INDEX][nextRow][nextCol];
            return !isBlock(nextBlockType) && nextBlockType != BOX;
        }

        return true;
    }



    public void updateBoard(Move move) {
        // update the board based on move
    }





    public int[][][] getBoard() {
        return board;
    }

    public boolean isBlock(int type) {
        return type >= STONE && type <= RED_BLOCK;
    }
    public boolean isWithinBound(int row, int col) {
        return row >= 0 && row < board[0].length && col >= 0 && col < board[0][0].length;
    }


    private void initializeBoard() {
        // load from file
        board = mergeLayers();
    }

    public static int[][][] mergeLayers() {
        int rows = groundLayer.length;
        int cols = groundLayer[0].length;
        int[][][] mergedBoard = new int[3][rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                mergedBoard[0][row][col] = groundLayer[row][col];
                mergedBoard[1][row][col] = blockLayer[row][col];
                mergedBoard[2][row][col] = objectLayer[row][col];
            }
        }

        return mergedBoard;
    }
}
