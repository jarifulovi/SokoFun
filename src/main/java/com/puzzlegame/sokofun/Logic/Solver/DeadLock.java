package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;

public class DeadLock {

    public boolean isDeadLock(BoardState boardState) {
        if (boardState == null) {
            throw new IllegalArgumentException("BoardState cannot be null");
        }

        return isCornerDeadLock(boardState);
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
               boardState.getWallPositions().contains(position) ||
               boardState.getBoxPositions().contains(position);
    }


}
