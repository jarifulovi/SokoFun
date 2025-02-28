package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Object.Position;

import java.util.Arrays;
import java.util.Set;

public abstract class SolverUtils {

    public static void plotBoard(Set<Position> boxPos,int rows,int cols,char symbol) {
        char[][] grid = new char[rows][cols];

        for (char[] row : grid) {
            Arrays.fill(row, 'I');
        }

        for (Position pos : boxPos) {
            grid[pos.getRow()][pos.getCol()] = symbol;
        }

        for (char[] row : grid) {
            System.out.println(String.join(" ", new String(row).split("")));
        }
        System.out.println();
    }

    public static void displayBoxPos(Set<Position> boxPos,int rows,int cols) {
        plotBoard(boxPos,rows,cols,'#');
    }

    public static void displayPushes(Set<Position> boxPos,int rows,int cols) {
        plotBoard(boxPos,rows,cols,'X');
    }
}
