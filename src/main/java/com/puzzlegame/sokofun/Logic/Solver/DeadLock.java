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

    // Changed access modifiers from private to protected for testing purposes
    protected boolean checkStaticDeadLock(BoardState boardState) {
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

    protected void addStaticDeadLockPosition(BoardState boardState) {
        System.out.println("Starting addStaticDeadLockPosition...");
        // check corridors above
        for (int row = 0; row < boardState.getTotalRows() - 1; row++) {

            HashSet<Integer> corridorPositions = new HashSet<>();
            boolean corridorStarted = false;
            for (int col = 0; col < boardState.getTotalCols(); col++) {
                int index = row * boardState.getTotalCols() + col;
                System.out.println("Processing index: " + index);
                corridorStarted = this.processTileForCorridor(corridorPositions, boardState, corridorStarted, index,
                        GameConstants.UP, GameConstants.LEFT, GameConstants.RIGHT);
            }
        }

        // check corridors below
        for (int row = 1; row < boardState.getTotalRows(); row++) {

            HashSet<Integer> corridorPositions = new HashSet<>();
            boolean corridorStarted = false;
            for (int col = 0; col < boardState.getTotalCols(); col++) {
                int index = row * boardState.getTotalCols() + col;
                System.out.println("Processing index: " + index);
                corridorStarted = this.processTileForCorridor(corridorPositions, boardState, corridorStarted, index,
                        GameConstants.DOWN, GameConstants.LEFT, GameConstants.RIGHT);
            }
        }

        // check corridors left
        for (int col = 0; col < boardState.getTotalCols() - 1; col++) {

            HashSet<Integer> corridorPositions = new HashSet<>();
            boolean corridorStarted = false;
            for (int row = 0; row < boardState.getTotalRows(); row++) {
                int index = row * boardState.getTotalCols() + col;
                System.out.println("Processing index: " + index);
                corridorStarted = this.processTileForCorridor(corridorPositions, boardState, corridorStarted, index,
                        GameConstants.LEFT, GameConstants.UP, GameConstants.DOWN);
            }
        }

        // check corridors right
        for (int col = 1; col < boardState.getTotalCols(); col++) {

            HashSet<Integer> corridorPositions = new HashSet<>();
            boolean corridorStarted = false;
            for (int row = 0; row < boardState.getTotalRows(); row++) {
                int index = row * boardState.getTotalCols() + col;
                System.out.println("Processing index: " + index);
                corridorStarted = this.processTileForCorridor(corridorPositions, boardState, corridorStarted, index,
                        GameConstants.RIGHT, GameConstants.UP, GameConstants.DOWN);
            }
        }

        // Ensure static deadlocks are computed
        this.isStaticDeadLockComputed = true;
        System.out.println("Finished addStaticDeadLockPosition.");
    }

    private boolean processTileForCorridor(HashSet<Integer> corridorPositions, BoardState boardState,
                                           boolean isStarted, int index, int direction,
                                           int startDir, int endDir) {

        int[] directions = SolverUtils.getDirections(index, boardState);
        System.out.println("Directions for index " + index + ": " + java.util.Arrays.toString(directions));
        if (boardState.isFreeSpace(index)) {
            System.out.println("Index " + index + " is free space.");
            if (!SolverUtils.isValidPosition(directions[direction], boardState)) {
                System.out.println("Direction " + direction + " is not valid for index " + index);
                if (!SolverUtils.isValidPosition(directions[startDir], boardState)) {
                    System.out.println("Start direction " + startDir + " is not valid for index " + index);
                    isStarted = true;
                }

                if (isStarted) {
                    System.out.println("Adding index " + index + " to corridor positions.");
                    corridorPositions.add(index);
                }

                if (!SolverUtils.isValidPosition(directions[endDir], boardState)) {
                    System.out.println("End direction " + endDir + " is not valid for index " + index);
                    isStarted = false;
                    if (!corridorPositions.isEmpty()) {
                        System.out.println("Corridor found: " + corridorPositions);
                        if (this.isDeadLockCorridor(corridorPositions, boardState)) {
                            System.out.println("Static deadlock detected in corridor: " + corridorPositions);
                            this.staticDeadLockPositions.addAll(corridorPositions);
                        }
                        corridorPositions.clear();
                    }
                }
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

    protected boolean isInCorner(BoardState boardState, int index) {
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

    protected boolean isBlockedDirection(BoardState boardState, int position) {
        // Check if the position is out of bounds, a wall, or has another box
        return SolverUtils.isOutOfBounds(position, boardState) ||
               boardState.getWallPositions().contains(position);
    }


}
