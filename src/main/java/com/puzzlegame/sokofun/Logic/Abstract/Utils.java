package com.puzzlegame.sokofun.Logic.Abstract;

import static com.puzzlegame.sokofun.Logic.Abstract.GameConstants.*;
import static com.puzzlegame.sokofun.Logic.Abstract.GameConstants.STONE_GOAL;

public abstract class Utils {

    public static boolean isWall(int type) {
        return type >= STONE && type <= RED_BLOCK;
    }

    public static boolean isGoal(int type) {
        return type >= WOOD_GOAL && type <= STONE_GOAL;
    }

    public static boolean isWithinBound(int row, int col,int totalRow,int totalCol) {
        return row >= 0 && row < totalRow && col >= 0 && col < totalCol;
    }



    public static int[][][] copyBoard(int[][][] board) {
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

    public static int[] getPlayerPosition(int[][][] board) {
        for(int row = 0;row < board[0].length; row++) {
            for(int col = 0;col < board[0][0].length; col++) {
                if(board[BLOCK_INDEX][row][col] == PLAYER) return new int[]{row,col};
            }
        }
        return new int[2];
    }

    public static void displayBoard(int[][][] board) {
        if (board == null || board.length == 0) {
            System.out.println("Empty board.");
            return;
        }

        for (int z = 0; z < board.length; z++) {
            if (board.length > 1) {
                System.out.println("Layer " + z + ":");
            }

            for (int y = 0; y < board[z].length; y++) {
                for (int x = 0; x < board[z][y].length; x++) {
                    System.out.print(board[z][y][x] + " ");
                }
                System.out.println();
            }
            if (board.length > 1){
                System.out.println("-----");
            }

        }
    }
}
