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
}
