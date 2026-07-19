package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Object.Move;

import java.util.Collections;
import java.util.List;

/**
 * Immutable result of a search run.
 */
public class SearchResult {
    private final boolean solvable;
    private final List<Move> moves;

    public static SearchResult unsolvable() {
        return new SearchResult(false, Collections.emptyList());
    }

    public static SearchResult of(boolean solvable, List<Move> moves) {
        return new SearchResult(solvable, moves);
    }

    public SearchResult(boolean solvable, List<Move> moves) {
        this.solvable = solvable;
        this.moves = moves == null ? Collections.emptyList() : Collections.unmodifiableList(moves);
    }

    public boolean isSolvable() {
        return solvable;
    }

    public List<Move> getMoves() {
        return moves;
    }
}
