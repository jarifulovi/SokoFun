package com.puzzlegame.sokofun.Logic.Solver.Heuristic;


import com.puzzlegame.sokofun.Logic.Abstract.Utils;
import com.puzzlegame.sokofun.Logic.Solver.BoardState;
import com.puzzlegame.sokofun.Logic.Solver.SolverUtils;

import java.util.*;

public class WallDistanceHeuristic implements Heuristic {

    /*
     * goal -> (cell -> distance)
     */
    private final Map<Integer, Map<Integer, Integer>> goalDistances;


    public WallDistanceHeuristic(BoardState initialState) {

        this.goalDistances = new HashMap<>();

        for (int goal : initialState.getGoalPositions()) {
            goalDistances.put(
                    goal,
                    calculateDistances(goal, initialState)
            );
        }
    }


    @Override
    public int estimate(BoardState state) {

        int total = 0;

        for (int box : state.getBoxPositions()) {

            int best = Integer.MAX_VALUE;

            for (Map<Integer, Integer> distances : goalDistances.values()) {

                Integer distance = distances.get(box);

                if (distance != null) {
                    best = Math.min(best, distance);
                }
            }


            /*
             * A box cannot reach any goal.
             * This state is effectively impossible.
             */
            if (best == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }

            total += best;
        }

        return total;
    }


    private Map<Integer, Integer> calculateDistances(
            int start,
            BoardState state
    ) {

        Map<Integer, Integer> distances = new HashMap<>();

        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);
        distances.put(start, 0);


        while (!queue.isEmpty()) {

            int current = queue.poll();

            int currentDistance = distances.get(current);


            for (int next : getNeighbors(current, state)) {

                if (!distances.containsKey(next)) {

                    distances.put(
                            next,
                            currentDistance + 1
                    );

                    queue.add(next);
                }
            }
        }

        return distances;
    }


    private List<Integer> getNeighbors(
            int index,
            BoardState state
    ) {

        List<Integer> result = new ArrayList<>();

        int row = SolverUtils.getRow(
                index,
                state.getTotalCols()
        );

        int col = SolverUtils.getCol(
                index,
                state.getTotalCols()
        );


        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };


        for (int[] dir : directions) {

            int newRow = row + dir[0];
            int newCol = col + dir[1];


            if (!Utils.isWithinBound(
                    newRow,
                    newCol,
                    state.getTotalRows(),
                    state.getTotalCols()
            )) {
                continue;
            }


            int next = SolverUtils.toIndex(
                    newRow,
                    newCol,
                    state.getTotalCols()
            );


            if (!state.getWallPositions().contains(next)) {
                result.add(next);
            }
        }

        return result;
    }
}