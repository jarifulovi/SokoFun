package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;

import java.util.HashSet;

public class DeadLock {

    private HashSet<Integer> staticDeadLockPositions;
    private boolean isStaticDeadLockComputed = false;

    public DeadLock() {
        this.staticDeadLockPositions = new HashSet<>();
    }

    public void clearStaticDeadLocks() {
        this.staticDeadLockPositions.clear();
        this.isStaticDeadLockComputed = false;
    }

    public boolean isDeadLock(BoardState boardState) {
        if (boardState == null) {
            throw new IllegalArgumentException("BoardState cannot be null");
        }

        // More deadlock detection strategies can be added here
        return this.isCornerDeadLock(boardState) || this.checkStaticDeadLock(boardState);
    }

    private boolean checkStaticDeadLock(BoardState boardState) {
        if (this.staticDeadLockPositions.isEmpty() && !this.isStaticDeadLockComputed) {
            this.addStaticDeadLockPosition(boardState);
        }

        for (int boxIndex : boardState.getBoxPositions()) {
            if (this.staticDeadLockPositions.contains(boxIndex) &&
                    !boardState.getGoalPositions().contains(boxIndex)) {
                return true;
            }
        }
        return false;
    }


    private void addStaticDeadLockPosition(BoardState boardState) {
        // check corridors above
        for (int row = 0; row < boardState.getTotalRows()-1; row++) {

            HashSet<Integer> corridorPositions = new HashSet<>();
            boolean corridorStarted = false;
            for(int col = 0; col < boardState.getTotalCols(); col++) {
                int index = row * boardState.getTotalCols() + col;
                corridorStarted = this.processTileForCorridor(corridorPositions, boardState, corridorStarted, index,
                        GameConstants.UP, GameConstants.LEFT, GameConstants.RIGHT);

            }
        }


        // check corridors below
        for (int row = 1; row < boardState.getTotalRows(); row++) {

            HashSet<Integer> corridorPositions = new HashSet<>();
            boolean corridorStarted = false;
            for(int col = 0; col < boardState.getTotalCols(); col++) {
                int index = row * boardState.getTotalCols() + col;
                corridorStarted = this.processTileForCorridor(corridorPositions, boardState, corridorStarted, index,
                        GameConstants.DOWN, GameConstants.LEFT, GameConstants.RIGHT);

            }
        }

        // check corridors left
        for (int col = 0; col < boardState.getTotalCols()-1; col++) {

            HashSet<Integer> corridorPositions = new HashSet<>();
            boolean corridorStarted = false;
            for(int row = 0; row < boardState.getTotalRows(); row++) {
                int index = row * boardState.getTotalCols() + col;
                corridorStarted = this.processTileForCorridor(corridorPositions, boardState, corridorStarted, index,
                        GameConstants.LEFT, GameConstants.UP, GameConstants.DOWN);

            }
        }


        // check corridors right
        for (int col = 1; col < boardState.getTotalCols(); col++) {

            HashSet<Integer> corridorPositions = new HashSet<>();
            boolean corridorStarted = false;
            for(int row = 0; row < boardState.getTotalRows(); row++) {
                int index = row * boardState.getTotalCols() + col;
                corridorStarted = this.processTileForCorridor(corridorPositions, boardState, corridorStarted, index,
                        GameConstants.RIGHT, GameConstants.UP, GameConstants.DOWN);

            }
        }

        this.isStaticDeadLockComputed = true;
    }



    private boolean processTileForCorridor(HashSet<Integer> corridorPositions, BoardState boardState,
                                           boolean isStarted, int index, int direction,
                                           int startDir, int endDir) {

        int[] directions = SolverUtils.getDirections(index, boardState);
        if (boardState.isFreeSpace(index)) {
            if (!SolverUtils.isValidPosition(directions[direction], boardState)) {
                if (!SolverUtils.isValidPosition(directions[startDir], boardState)) {
                    isStarted = true;
                }

                if (isStarted) {
                    corridorPositions.add(index);
                }

                if (!SolverUtils.isValidPosition(directions[endDir], boardState)) {
                    isStarted = false;
                    if (!corridorPositions.isEmpty()) {
                        // corridor found
                        if (this.isDeadLockCorridor(corridorPositions, boardState)) {
                            this.staticDeadLockPositions.addAll(corridorPositions);
                        }
                    }
                    corridorPositions.clear();
                }
            } else {
                isStarted = false;
                corridorPositions.clear();
            }
        }
        return isStarted;
    }

    private boolean isDeadLockCorridor(HashSet<Integer> corridor, BoardState boardState) {
        for (int pos : corridor) {
            if (boardState.getGoalPositions().contains(pos)) {
                return false;
            }
        }
        return true;
    }






    private boolean isCornerDeadLock(BoardState boardState) {
        for (int boxIndex : boardState.getBoxPositions()) {
            if (isInCorner(boardState, boxIndex) &&
                    !boardState.getGoalPositions().contains(boxIndex)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCorner(BoardState boardState, int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Box Index cannot be negative");
        }

        int[] directions = SolverUtils.getDirections(index, boardState);

        boolean upBlocked = isBlockedDirection(boardState, directions[GameConstants.UP]);
        boolean downBlocked = isBlockedDirection(boardState, directions[GameConstants.DOWN]);
        boolean leftBlocked = isBlockedDirection(boardState, directions[GameConstants.LEFT]);
        boolean rightBlocked = isBlockedDirection(boardState, directions[GameConstants.RIGHT]);

        return (upBlocked && leftBlocked) || (upBlocked && rightBlocked) ||
               (downBlocked && leftBlocked) || (downBlocked && rightBlocked);
    }

    private boolean isBlockedDirection(BoardState boardState, int position) {
        // Check if the position is out of bounds, a wall, or has another box
        return SolverUtils.isOutOfBounds(position, boardState) ||
               boardState.getWallPositions().contains(position);
    }


}
