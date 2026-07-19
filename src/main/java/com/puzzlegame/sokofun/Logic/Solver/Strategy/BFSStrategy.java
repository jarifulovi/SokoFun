package com.puzzlegame.sokofun.Logic.Solver.Strategy;

import com.puzzlegame.sokofun.Logic.Solver.*;
import com.puzzlegame.sokofun.Object.Move;

import java.util.*;

/**
 * Breadth-First Search strategy for Sokoban.
 * Uses SolverContext services to generate moves, prune deadlocks, and
 * reconstruct the full sequence (walk + push) when a solution is found.
 */
public class BFSStrategy implements SearchStrategy {

    private final SolverContext context;

    public BFSStrategy() {
        this(new SolverContext());
    }

    public BFSStrategy(SolverContext context) {
        this.context = context;
    }

    @Override
    public SearchResult solve(BoardState initialState) {
        if (initialState == null) {
            throw new IllegalArgumentException("initialState cannot be null");
        }

        // Get required services from context
        MoveGenerator moveGenerator = context.getMoveGenerator();
        PathConstructor pathConstructor = context.getPathConstructor();
        DeadLock deadLock = context.getDeadLock();

        // Reset static deadlocks to match fresh run semantics
        deadLock.clearStaticDeadLocks();

        Queue<BoardState> queue = new LinkedList<>();
        Set<BoardState> visited = new HashSet<>();
        Map<BoardState, BoardState> parentMap = new HashMap<>();
        Map<BoardState, Move> moveMap = new HashMap<>();

        BoardState start = initialState.copy();
        queue.add(start);
        visited.add(start);
        parentMap.put(start, null);
        moveMap.put(start, null);

        while (!queue.isEmpty()) {
            BoardState current = queue.poll();

            if (current.isSucceed()) {
                List<Move> fullMoves = pathConstructor.constructFullPath(current, parentMap, moveMap);
                return SearchResult.of(true, fullMoves);
            }

            List<Move> possibleMoves = moveGenerator.getAllPossibleMoves(current);
            for (Move move : possibleMoves) {
                BoardState next = current.copy();
                // Before applying, align player to the push position (where the push is executed from)
                int playerIdx = com.puzzlegame.sokofun.Logic.Solver.SolverUtils
                        .toIndex(move.getPrevRow(), move.getPrevCol(), next.getTotalCols());
                next.setPlayerPosition(playerIdx);

                next.applyMove(move);

                if (deadLock.isDeadLock(next)) {
                    continue; // prune deadlocked positions
                }

                if (!visited.contains(next)) {
                    visited.add(next);
                    parentMap.put(next, current);
                    moveMap.put(next, move);
                    queue.add(next);
                }
            }
        }

        return SearchResult.unsolvable();
    }
}
