package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.Abstract.Utils;
import com.puzzlegame.sokofun.Object.Move;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class BoardState {

    private int playerPosition;
    private Set<Integer> boxPositions;
    private Set<Integer> goalPositions;
    private Set<Integer> wallPositions;
    private int totalCols;
    private int totalRows;

    public BoardState() {
        this.boxPositions = new HashSet<>();
        this.goalPositions = new HashSet<>();
        this.wallPositions = new HashSet<>();
        playerPosition = -1;
    }

    public void setTotalCols(int totalCols) {
        if (totalCols <= 0) {
            throw new IllegalArgumentException("Total columns must be positive");
        }
        this.totalCols = totalCols;
    }

    public void setTotalRows(int totalRows) {
        if (totalRows <= 0) {
            throw new IllegalArgumentException("Total rows must be positive");
        }
        this.totalRows = totalRows;
    }

    public int getTotalCols() {
        return totalCols;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setPlayerPosition(int playerPosition) {
        if (playerPosition < -1) {
            throw new IllegalArgumentException("Player position cannot be less than -1");
        }
        this.playerPosition = playerPosition;
    }

    public void setBoxPositions(Set<Integer> boxPositions) {
        if (boxPositions == null) {
            throw new IllegalArgumentException("Box positions cannot be null");
        }
        this.boxPositions = boxPositions;
    }

    public void setGoalPositions(Set<Integer> goalPositions) {
        if (goalPositions == null) {
            throw new IllegalArgumentException("Goal positions cannot be null");
        }
        this.goalPositions = goalPositions;
    }

    public void setWallPositions(Set<Integer> wallPositions) {
        if (wallPositions == null) {
            throw new IllegalArgumentException("Wall positions cannot be null");
        }
        this.wallPositions = wallPositions;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public Set<Integer> getBoxPositions() {
        return boxPositions;
    }

    public Set<Integer> getGoalPositions() {
        return goalPositions;
    }

    public Set<Integer> getWallPositions() {
        return wallPositions;
    }



    public boolean isSucceed() {
        for (int boxIndex : boxPositions) {
            if (!goalPositions.contains(boxIndex)) {
                return false;
            }
        }
        return true;
    }



    public void applyMove(Move move) {
        this.playerPosition = SolverUtils.toIndex(move.getNewRow(), move.getNewCol(), totalCols);

        if (move.getIsPushed()) {
            int boxIndex = SolverUtils.toIndex(move.getNewRow(), move.getNewCol(), totalCols);
            if (this.boxPositions.contains(boxIndex)) {
                this.boxPositions.remove(boxIndex);
            } else {
                throw new IllegalStateException("Error: No box is pushed");
            }

            int newBoxIndex = SolverUtils.toIndex(move.getPushedRow(), move.getPushedCol(), totalCols);
            this.boxPositions.add(newBoxIndex);
        }
    }

    public void undoMove(Move move) {
        this.playerPosition = SolverUtils.toIndex(move.getPrevRow(), move.getPrevCol(), totalCols);

        if (move.getIsPushed()) {
            int boxIndex = SolverUtils.toIndex(move.getPushedRow(), move.getPushedCol(), totalCols);
            if (this.boxPositions.contains(boxIndex)) {
                this.boxPositions.remove(boxIndex);
            } else {
                throw new IllegalStateException("Error: No box is pushed at that direction");
            }

            int prevBoxIndex = SolverUtils.toIndex(move.getNewRow(), move.getNewCol(), totalCols);
            this.boxPositions.add(prevBoxIndex);
        }
    }




    public BoardState copy() {
        BoardState copy = new BoardState();
        copy.totalRows = this.totalRows;
        copy.totalCols = this.totalCols;
        copy.playerPosition = this.playerPosition;

        copy.boxPositions = new HashSet<>(this.boxPositions);

        copy.goalPositions = Collections.unmodifiableSet(this.goalPositions);
        copy.wallPositions = Collections.unmodifiableSet(this.wallPositions);

        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        BoardState that = (BoardState) obj;
        return playerPosition == that.playerPosition &&
               totalCols == that.totalCols &&
               totalRows == that.totalRows &&
               Objects.equals(boxPositions, that.boxPositions) &&
               Objects.equals(goalPositions, that.goalPositions) &&
               Objects.equals(wallPositions, that.wallPositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerPosition, boxPositions, goalPositions, wallPositions, totalCols, totalRows);
    }



    public boolean isFreeSpace(int index) {
        int row = SolverUtils.getRow(index, totalCols);
        int col = SolverUtils.getCol(index, totalCols);

        if (index == -1 || !Utils.isWithinBound(row, col, totalRows, totalCols)) {
            throw new IllegalArgumentException("Index is out of bounds or invalid");
        }
        return !wallPositions.contains(index) && !boxPositions.contains(index);
    }

    // #: Wall, B: Box, *: Box on Goal, .: Goal, P: Player, +: Player on Goal, ' ' : Empty
    public void printBoard() {
        System.out.println("// #: Wall, B: Box, *: Box on Goal, .: Goal, P: Player, +: Player on Goal, ' ' : Empty");
        for (int r = 0; r < totalRows; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < totalCols; c++) {
                int idx = SolverUtils.toIndex(r, c, totalCols);
                char ch;
                boolean isGoal = goalPositions.contains(idx);
                boolean isBox = boxPositions.contains(idx);
                boolean isWall = wallPositions.contains(idx);
                boolean isPlayer = (playerPosition == idx);

                if (isWall) ch = '#';
                else if (isBox && isGoal) ch = '*';
                else if (isPlayer && isGoal) ch = '+';
                else if (isBox) ch = 'B';
                else if (isPlayer) ch = 'P';
                else if (isGoal) ch = '.';
                else ch = ' ';

                sb.append(ch);
            }
            System.out.println(sb.toString());
        }
        System.out.println();
    }

}
