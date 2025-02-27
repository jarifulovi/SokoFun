package com.puzzlegame.sokofun.Logic.GameLogic;


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
        board = copyBoard(initialBoard);
        this.totalRow = board[0].length;
        this.totalCol = board[0][0].length;
    }

    public void resetBoard() {
        board = copyBoard(initialBoard);
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

        if (!isWithinBound(newRow, newCol)) {
            return false;
        }

        int blockType = board[BLOCK_INDEX][newRow][newCol];

        if (isBlock(blockType)) {
            return false;
        }


        if (blockType == BOX) {
            int nextRow = move.getPushedRow();
            int nextCol = move.getPushedCol();

            if (!isWithinBound(nextRow, nextCol)) {
                return false;
            }

            int nextBlockType = board[BLOCK_INDEX][nextRow][nextCol];
            return !isBlock(nextBlockType) && nextBlockType != BOX;
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
                        !isGoal(board[OBJECT_INDEX][row][col])) {
                    return false;
                }
            }
        }
        return true;
    }


    public int[] getPlayerPosition() {
        for(int row = 0;row < totalRow; row++) {
            for(int col = 0;col < totalCol; col++) {
                if(board[BLOCK_INDEX][row][col] == PLAYER) return new int[]{row,col};
            }
        }
        return new int[2];
    }

    private boolean isBlock(int type) {
        return type >= STONE && type <= RED_BLOCK;
    }

    private boolean isGoal(int type) {
        return type >= WOOD_GOAL && type <= STONE_GOAL;
    }

    private boolean isWithinBound(int row, int col) {
        return row >= 0 && row < totalRow && col >= 0 && col < totalCol;
    }

    private int[][][] copyBoard(int[][][] board) {
        int rows = board.length;
        int cols = board[0].length;
        int depth = board[0][0].length;
        int[][][] newBoard = new int[rows][cols][depth];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.arraycopy(board[i][j], 0, newBoard[i][j], 0, depth);
            }
        }
        return newBoard;
    }


}
