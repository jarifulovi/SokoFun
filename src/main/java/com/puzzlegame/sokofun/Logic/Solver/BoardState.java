package com.puzzlegame.sokofun.Logic.Solver;


import com.puzzlegame.sokofun.Logic.Abstract.Utils;
import com.puzzlegame.sokofun.Object.Move;
import com.puzzlegame.sokofun.Object.Position;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BoardState {

    private Position playerPosition;
    private Set<Position> boxPositions;
    private Set<Position> goalPositions;
    private Set<Position> wallPositions;
    private int totalCols;
    private int totalRows;

    public BoardState() {
        this.boxPositions = new HashSet<>();
        this.goalPositions = new HashSet<>();
        this.wallPositions = new HashSet<>();
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


    public boolean isSucceed() {

        for (Position boxPos : boxPositions) {
            if (!goalPositions.contains(boxPos)) {
                return false;
            }
        }
        return true;
    }


    public void applyMove(Move move) {

        this.playerPosition = new Position(move.getNewRow(), move.getNewCol());

        if (move.getIsPushed()) {
            Position boxPosition = new Position(move.getNewRow(), move.getNewCol());
            this.boxPositions.remove(boxPosition);

            Position newBoxPosition = new Position(move.getPushedRow(), move.getPushedCol());
            this.boxPositions.add(newBoxPosition);
        }
    }



    public BoardState copy() {
        BoardState copy = new BoardState();
        copy.totalRows = this.totalRows;
        copy.totalCols = this.totalCols;

        copy.playerPosition = (this.playerPosition != null)
                ? new Position(this.playerPosition.getRow(), this.playerPosition.getCol())
                : null;


        copy.boxPositions = this.boxPositions.stream()
                .map(pos -> new Position(pos.getRow(), pos.getCol())).collect(Collectors.toUnmodifiableSet());

        copy.goalPositions = Collections.unmodifiableSet(this.goalPositions);
        copy.wallPositions = Collections.unmodifiableSet(this.wallPositions);

        return copy;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (playerPosition != null ? playerPosition.hashCode() : 0);
        result = 31 * result + (boxPositions != null ? boxPositions.hashCode() : 0);
        return result;
    }

    public boolean isFreeSpace(Position position) {

        if (!Utils.isWithinBound(position.getRow(), position.getCol(), totalRows, totalCols)) {
            return false;
        }

        return !wallPositions.contains(position) && !boxPositions.contains(position);
    }

}
