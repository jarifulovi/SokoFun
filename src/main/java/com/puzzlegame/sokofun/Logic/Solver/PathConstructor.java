package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Object.Move;

import java.util.*;

/**
 * Responsible for constructing the full move path (walks + pushes) from BFS parent maps.
 */
public class PathConstructor {

    private final PathFinder pathFinder;

    public PathConstructor(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    /**
     * Build the full sequence of moves (including in-between walking moves) from the
     * solution node using the provided parent and move maps produced by BFS.
     */
    public List<Move> constructFullPath(BoardState solution,
                                        Map<BoardState, BoardState> parentMap,
                                        Map<BoardState, Move> moveMap) {
        // 1) Backtrack to get push-only moves from start to solution
        List<Move> pushOnly = new ArrayList<>();
        BoardState current = solution;
        while (parentMap.get(current) != null) {
            Move move = moveMap.get(current);
            if (move != null) {
                pushOnly.add(move);
            }
            current = parentMap.get(current);
        }
        Collections.reverse(pushOnly);

        // 2) Reconstruct initial state by walking back to the root node
        BoardState root = solution;
        while (parentMap.get(root) != null) {
            root = parentMap.get(root);
        }
        BoardState simulator = root.copy();
        int currentPlayerIdx = simulator.getPlayerPosition();

        // 3) Expand to full sequence including in-between player moves
        List<Move> fullMoves = new ArrayList<>();
        for (Move pushMove : pushOnly) {
            // Where the player must stand to perform the push
            int pushPosIdx = SolverUtils.toIndex(pushMove.getPrevRow(), pushMove.getPrevCol(), simulator.getTotalCols());

            // Find shortest free-space path from current player to required push position
            List<Integer> path = pathFinder.getShortestPath(simulator, currentPlayerIdx, pushPosIdx);
            if (path.isEmpty()) {
                throw new IllegalStateException("No path found to push position during reconstruction");
            }
            // Convert index path to walking moves (skip the first index which is current position)
            for (int i = 1; i < path.size(); i++) {
                int from = path.get(i - 1);
                int to = path.get(i);
                int fromRow = SolverUtils.getRow(from, simulator.getTotalCols());
                int fromCol = SolverUtils.getCol(from, simulator.getTotalCols());
                int toRow = SolverUtils.getRow(to, simulator.getTotalCols());
                int toCol = SolverUtils.getCol(to, simulator.getTotalCols());
                int dir = (toRow < fromRow) ? GameConstants.UP :
                        (toRow > fromRow) ? GameConstants.DOWN :
                                (toCol < fromCol) ? GameConstants.LEFT : GameConstants.RIGHT;
                Move walk = new Move(fromRow, fromCol, toRow, toCol, dir);
                simulator.applyMove(walk);
                fullMoves.add(walk);
            }
            // Now perform the actual push
            simulator.applyMove(pushMove);
            fullMoves.add(pushMove);
            currentPlayerIdx = simulator.getPlayerPosition();
        }

        return fullMoves;
    }
}
