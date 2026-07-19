package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.GameLogic.LevelLoader;
import com.puzzlegame.sokofun.Logic.Solver.Heuristic.Heuristic;
import com.puzzlegame.sokofun.Logic.Solver.Heuristic.WallDistanceHeuristic;
import com.puzzlegame.sokofun.Logic.Solver.Strategy.AStarStrategy;
import com.puzzlegame.sokofun.Logic.Solver.Strategy.BFSStrategy;
import com.puzzlegame.sokofun.Logic.Solver.Strategy.SearchStrategy;
import com.puzzlegame.sokofun.Object.Move;

import java.util.*;

// Solver wired to use a pluggable SearchStrategy (default: BFSStrategy)
public class Solver {

    private final SolverContext context;
    private final SearchStrategy strategy;
    private List<Move> moves;
    private boolean isSolved; // Flag to indicate of solve() has been called
    private boolean isSolvable; // Flag to indicate if a solution was found

    public Solver() {
        this(new SolverContext(), null);
    }

    public Solver(SolverContext context) {
        this(context, null);
    }

    public Solver(SearchStrategy strategy) {
        this(new SolverContext(), strategy);
    }

    public Solver(SolverContext context, SearchStrategy strategy) {
        this.context = (context != null) ? context : new SolverContext();
        this.strategy = (strategy != null) ? strategy : new BFSStrategy(this.context);
        this.moves = null;
        this.isSolved = false;
        this.isSolvable = false;
    }

    public boolean getIsSolvable() {
        if (!isSolved) {
            throw new IllegalStateException("Please run solve() first.");
        }
        return isSolvable;
    }

    public List<Move> getMoves() {
        if (!isSolved) {
            throw new IllegalStateException("Please run solve() first.");
        }
        return moves;
    }

    public void solve(BoardState boardState) {
        if (boardState == null) {
            throw new IllegalArgumentException("boardState cannot be null");
        }
        SearchResult result = strategy.solve(boardState.copy());
        this.isSolved = true;
        this.isSolvable = result.isSolvable();
        this.moves = result.getMoves();
        if (!this.isSolvable) {
            System.err.println("No solution found in solver().");
        }
    }

    public static void main(String[] args) {
        try {
            int level = 5; // default test level
            long slower = 500;
            LevelLoader loader = new LevelLoader(level);
            int[][][] board = loader.getLevelBoard();
            Converter converter = new Converter(board);
            BoardState boardState = converter.getSolverState();

            // Print initial board
            boardState.printBoard();
            Thread.sleep(slower);

            Heuristic heuristic = new WallDistanceHeuristic(boardState);
            Solver solver = new Solver(new AStarStrategy(new SolverContext(), heuristic));
            solver.solve(boardState);

            if (solver.getIsSolvable()) {
                // Simulate and print each step
                BoardState simulator = boardState.copy();
                for (Move move : solver.getMoves()) {
                    simulator.applyMove(move);
                    simulator.printBoard();
                    Thread.sleep(slower);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}