package com.puzzlegame.sokofun.Logic.Solver;


import com.puzzlegame.sokofun.Logic.Abstract.Utils;
import com.puzzlegame.sokofun.Object.Position;

import java.util.Set;

public class BoardState {

    private Position playerPosition;
    private Set<Position> boxPositions;
    private Set<Position> goalPositions;
    private Set<Position> wallPositions;
    private int totalCols;
    private int totalRows;

    public BoardState() {

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

    public void setPlayerPosition(Position playerPosition) {
        this.playerPosition = playerPosition;
    }

    public void setBoxPositions(Set<Position> boxPositions) {
        this.boxPositions = boxPositions;
    }

    public void setGoalPositions(Set<Position> goalPositions) {
        this.goalPositions = goalPositions;
    }

    public void setWallPositions(Set<Position> wallPositions) {
        this.wallPositions = wallPositions;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public Set<Position> getBoxPositions() {
        return boxPositions;
    }

    public Set<Position> getGoalPositions() {
        return goalPositions;
    }

    public Set<Position> getWallPositions() {
        return wallPositions;
    }

    public boolean isPushable(int row,int col) {
        return Utils.isWithinBound(row,col,totalRows,totalCols) &&
                !wallPositions.contains(new Position(row,col)) &&
                !boxPositions.contains(new Position(row,col));
    }


    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + (playerPosition != null ? playerPosition.hashCode() : 0);
        result = 31 * result + (boxPositions != null ? boxPositions.hashCode() : 0);
        result = 31 * result + (goalPositions != null ? goalPositions.hashCode() : 0);

        return result;
    }
}
