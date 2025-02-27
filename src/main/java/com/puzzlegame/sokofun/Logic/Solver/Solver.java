package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Object.Move;
import com.puzzlegame.sokofun.Object.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Solver {

    private Converter converter;
    private BoardState boardState;
    private List<Move> moves;
    private boolean isSolvable;

    public Solver() {
        moves = new ArrayList<>();
        this.isSolvable = false;
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    private void solve(int[][][] board) {
        converter = new Converter(board);
        boardState = converter.getSolverState();
        // Implement BFS, A*, or other search algorithms to find a solution
        // If a solution is found, populate `moves` and set `isSolvable = true`
    }

    private void generateAllPossibleMove() {
        // check state to gen moves
        Set<Position> pushablePositions = getAllPushedPositions();
        // check pathfinding to reach
        // return the move
    }

    private Set<Position> getAllPushedPositions() {
        Set<Position> pushablePositions = new HashSet<>();

        for(Position position : boardState.getBoxPositions()) {
            getPushPosition(position,pushablePositions);
        }
        return pushablePositions;
    }

    private void getPushPosition(Position boxPosition,Set<Position> pushablePositions) {

        int[][] offsets = {
                {-1, 0},  // Up
                {1, 0},   // Down
                {0, -1},  // Left
                {0, 1}    // Right
        };

        // Iterate over all four directions
        for (int[] offset : offsets) {
            int directionRow = offset[0];
            int directionCol = offset[1];

            int pushRow = boxPosition.getRow() + directionRow;
            int pushCol = boxPosition.getCol() + directionCol;


            if (boardState.isPushable(pushRow, pushCol)) {
                pushablePositions.add(new Position(pushRow,pushCol));
            }
        }

    }


    private void applyMove(Move move) {
        // change player position
        // if collides with box then pushes it
    }
}