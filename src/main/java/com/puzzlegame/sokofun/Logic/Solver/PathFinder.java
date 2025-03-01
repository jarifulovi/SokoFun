package com.puzzlegame.sokofun.Logic.Solver;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;


public class PathFinder {

    public boolean canPlayerReach(BoardState boardState,int start, Set<Integer> targets) {

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        // Perform BFS
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // Check if the current position is one of the target positions
            if (targets.contains(current)) {
                return true;
            }

            int[] directions = SolverUtils.getDirections(current,boardState);

            for (int next : directions) {
                if (next != -1 && !visited.contains(next) && boardState.isFreeSpace(next)) {
                    visited.add(next);
                    queue.add(next);
                }
            }
        }

        return false;
    }


}
