package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.Abstract.Utils;
import com.puzzlegame.sokofun.Object.Move;

import java.util.Collections;
import java.util.HashSet;
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
        this.totalCols = totalCols;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalCols() {
        return totalCols;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

    public void setBoxPositions(Set<Integer> boxPositions) {
        this.boxPositions = boxPositions;
    }

    public void setGoalPositions(Set<Integer> goalPositions) {
        this.goalPositions = goalPositions;
    }

    public void setWallPositions(Set<Integer> wallPositions) {
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
            this.boxPositions.remove(boxIndex);

            int newBoxIndex = SolverUtils.toIndex(move.getPushedRow(), move.getPushedCol(), totalCols);
            this.boxPositions.add(newBoxIndex);
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
    public int hashCode() {
        int result = 17;
        result = 31 * result + Integer.hashCode(playerPosition);
        result = 31 * result + (boxPositions != null ? boxPositions.hashCode() : 0);
        return result;
    }


    public boolean isFreeSpace(int index) {
        int row = SolverUtils.getRow(index, totalCols);
        int col = SolverUtils.getCol(index, totalCols);

        if (!Utils.isWithinBound(row, col, totalRows, totalCols)) {
            return false;
        }

        return !wallPositions.contains(index) && !boxPositions.contains(index);
    }

}
