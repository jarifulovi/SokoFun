package com.puzzlegame.sokofun.Logic.Solver;


import java.util.Arrays;
import java.util.Set;

public abstract class SolverUtils {

    public static int getRow(int index, int totalCols) {
        return index / totalCols;
    }

    public static int getCol(int index, int totalCols) {
        return index % totalCols;
    }

    public static int toIndex(int row, int col, int totalCols) {
        return row * totalCols + col;
    }

    public static boolean isOutOfBounds(int position, BoardState boardState) {
        if (position < 0) {
            return true;
        }
        int totalPositions = boardState.getTotalRows() * boardState.getTotalCols();
        return position >= totalPositions;
    }

    public static int[] getDirections(int index,BoardState boardState) {
        int upIndex = SolverUtils.getUpIndex(index,boardState.getTotalCols());
        int downIndex = SolverUtils.getDownIndex(index,boardState.getTotalCols(),boardState.getTotalRows());
        int leftIndex = SolverUtils.getLeftIndex(index,boardState.getTotalCols());
        int rightIndex = SolverUtils.getRightIndex(index,boardState.getTotalCols());
        return new int[]{upIndex,rightIndex,downIndex,leftIndex};
    }

    public static int getUpIndex(int index, int totalCols) {

        int row = getRow(index,totalCols);
        int col = getCol(index,totalCols);

        if (row > 0) {
            return (row - 1) * totalCols + col;
        }
        return -1;
    }
    public static int getDownIndex(int index, int totalCols, int totalRows) {
        int row = getRow(index, totalCols);
        int col = getCol(index, totalCols);

        if (row < totalRows - 1) {
            return (row + 1) * totalCols + col;
        }
        return -1;
    }

    public static int getLeftIndex(int index, int totalCols) {
        int row = getRow(index, totalCols);
        int col = getCol(index, totalCols);

        if(col > 0) {
            return row * totalCols + (col - 1);
        }
        return -1;

    }
    public static int getRightIndex(int index, int totalCols) {
        int row = getRow(index, totalCols);
        int col = getCol(index, totalCols);

        if(col < totalCols - 1) {
            return row * totalCols + (col + 1);
        }
        return -1;
    }



    public static void plotBoard(Set<Integer> boxPos, int totalRows, int totalCols, char symbol) {
        char[][] grid = new char[totalRows][totalCols];

        for (char[] row : grid) {
            Arrays.fill(row, 'I');
        }

        for (int index : boxPos) {
            int row = SolverUtils.getRow(index, totalCols);
            int col = SolverUtils.getCol(index, totalCols);
            grid[row][col] = symbol;
        }

        for (char[] row : grid) {
            System.out.println(String.join(" ", new String(row).split("")));
        }
        System.out.println();
    }


    public static void displayBoxPos(Set<Integer> boxPos,int rows,int cols) {
        plotBoard(boxPos,rows,cols,'#');
    }

    public static void displayPushes(Set<Integer> boxPos,int rows,int cols) {
        plotBoard(boxPos,rows,cols,'X');
    }

    /**
     * Check if a position is valid (within bounds and not a wall)
     */
    public static boolean isValidPosition(int position, BoardState boardState) {
        if (isOutOfBounds(position, boardState)) {
            return false;
        }
        return !boardState.getWallPositions().contains(position);
    }

    /**
     * Calculate where a box would end up after being pushed from a push position
     */
    public static int calculateBoxDestination(int pushPos, int boxPos, BoardState boardState) {
        int pushRow = getRow(pushPos, boardState.getTotalCols());
        int pushCol = getCol(pushPos, boardState.getTotalCols());
        int boxRow = getRow(boxPos, boardState.getTotalCols());
        int boxCol = getCol(boxPos, boardState.getTotalCols());

        // Calculate direction vector from push position to box
        int deltaRow = boxRow - pushRow;
        int deltaCol = boxCol - pushCol;

        // Box destination = current box position + same delta
        int destRow = boxRow + deltaRow;
        int destCol = boxCol + deltaCol;

        // Check bounds
        if (destRow < 0 || destRow >= boardState.getTotalRows() ||
            destCol < 0 || destCol >= boardState.getTotalCols()) {
            return -1; // Out of bounds
        }

        return toIndex(destRow, destCol, boardState.getTotalCols());
    }

    /**
     * Check if a box can be pushed to a destination position
     */
    public static boolean isValidBoxDestination(int destination, BoardState boardState) {
        if (destination == -1) {
            return false; // Out of bounds
        }

        // Check if destination is free (not wall, not another box)
        return isValidPosition(destination, boardState) &&
               !boardState.getBoxPositions().contains(destination);
    }
}
