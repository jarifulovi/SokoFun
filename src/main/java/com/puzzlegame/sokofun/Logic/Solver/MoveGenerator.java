package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Object.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Responsible only for generating possible push moves from a given board state.
 */
public class MoveGenerator {

    private final PushFinder pushFinder;
    private final PathFinder pathFinder;

    public MoveGenerator(PushFinder pushFinder, PathFinder pathFinder) {
        this.pushFinder = pushFinder;
        this.pathFinder = pathFinder;
    }

    /**
     * Returns all valid push moves the player can perform from the current board state.
     */
    public List<Move> getAllPossibleMoves(BoardState boardState) {
        if (boardState == null) {
            throw new IllegalStateException("Board state is not initialized");
        }
        // 1) Find all positions the player can stand to push a box
        Set<Integer> pushablePositions = pushFinder.getAllPushablePositions(boardState);
        // 2) Filter only those that are actually reachable by the player
        Set<Integer> validPushablePositions = pathFinder.getReachableTargets(boardState,
                boardState.getPlayerPosition(), pushablePositions);
        // 3) Create concrete push moves
        return generateValidMoves(boardState, validPushablePositions);
    }

    private List<Move> generateValidMoves(BoardState boardState, Set<Integer> validPushablePositions) {
        List<Move> validMoves = new ArrayList<>();
        for (Integer pushPos : validPushablePositions) {
            int[] directions = SolverUtils.getDirections(pushPos, boardState);
            for (int dir : directions) {
                if (dir != -1 && boardState.getBoxPositions().contains(dir)) {
                    // Calculate where the box would go after being pushed
                    int boxDestination = SolverUtils.calculateBoxDestination(pushPos, dir, boardState);

                    // Validate that the box can actually be pushed to the destination
                    if (!SolverUtils.isValidBoxDestination(boxDestination, boardState)) {
                        continue; // Skip this invalid push move
                    }

                    // Create a move
                    int preRow = SolverUtils.getRow(pushPos, boardState.getTotalCols());
                    int preCol = SolverUtils.getCol(pushPos, boardState.getTotalCols());
                    int newRow = SolverUtils.getRow(dir, boardState.getTotalCols());
                    int newCol = SolverUtils.getCol(dir, boardState.getTotalCols());
                    int direction = (preRow > newRow ) ? GameConstants.UP :
                            (preRow < newRow) ? GameConstants.DOWN :
                                    (preCol > newCol) ? GameConstants.LEFT :
                                            (preCol < newCol) ? GameConstants.RIGHT : -1;
                    if (direction == -1) {
                        throw new IllegalStateException("Invalid move direction");
                    }
                    Move move = new Move(preRow, preCol, newRow, newCol, direction);
                    move.setIsPush(true);
                    validMoves.add(move);
                }
            }
        }
        return validMoves;
    }
}
