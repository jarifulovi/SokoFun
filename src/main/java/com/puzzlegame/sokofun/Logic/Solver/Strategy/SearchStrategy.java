package com.puzzlegame.sokofun.Logic.Solver.Strategy;

import com.puzzlegame.sokofun.Logic.Solver.BoardState;
import com.puzzlegame.sokofun.Logic.Solver.SearchResult;

/**
 * Strategy interface for search algorithms (BFS, A*, IDA*, etc.).
 * Implementations should be stateless or internally manage their own state for a single run.
 */
public interface SearchStrategy {
    /**
     * Solve the given Sokoban board state and return the result.
     */
    SearchResult solve(BoardState initialState);
}
