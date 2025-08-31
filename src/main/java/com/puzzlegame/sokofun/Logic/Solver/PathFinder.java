package com.puzzlegame.sokofun.Logic.Solver;


import java.util.*;

// This class finds if the player can reach any of the target positions using BFS
public class PathFinder {


    /**
     * Find all reachable target positions from the start position
     * @param start Starting position index
     * @param targets Set of target position indices
     * @return Set of reachable target positions
     */
    public Set<Integer> getReachableTargets(BoardState currentState, int start, Set<Integer> targets) {
        if (targets == null || targets.isEmpty()) {
            return new HashSet<>();
        }
        if (start < 0) {
            throw new IllegalArgumentException("Start position cannot be negative");
        }

        Set<Integer> reachableTargets = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        // Perform BFS
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // Check if the current position is a target
            if (targets.contains(current)) {
                reachableTargets.add(current);
                if (reachableTargets.size() == targets.size()) {
                    return reachableTargets;
                }
            }

            // Get valid adjacent positions
            int[] directions = SolverUtils.getDirections(current, currentState);
            for (int next : directions) {
                // Skip invalid positions (-1) and already visited positions
                if (next != -1 && !visited.contains(next)) {
                    try {
                        // Check if the position is free (not wall or box)
                        if (currentState.isFreeSpace(next)) {
                            visited.add(next);
                            queue.add(next);
                        }
                    } catch (IllegalArgumentException e) {
                        // Skip invalid positions silently
                    }
                }
            }
        }

        return reachableTargets;
    }




    /**
     * Check if the player can reach any of the target positions
     * @param start Starting position index
     * @param targets Set of target position indices
     * @return true if any target is reachable, false otherwise
     */
    public boolean canPlayerReach(BoardState currentState, int start, Set<Integer> targets) {
        if (targets == null || targets.isEmpty()) {
            return false;
        }
        if (start < 0) {
            throw new IllegalArgumentException("Start position cannot be negative");
        }

        // Use local variables to avoid state pollution
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        // Perform BFS
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // Check if the current position is a target
            if (targets.contains(current)) {
                return true;
            }

            // Get valid adjacent positions
            int[] directions = SolverUtils.getDirections(current, currentState);
            for (int next : directions) {
                // Skip invalid positions (-1) and already visited positions
                if (next != -1 && !visited.contains(next)) {
                    try {
                        // Check if the position is free (not wall or box)
                        if (currentState.isFreeSpace(next)) {
                            visited.add(next);
                            queue.add(next);
                        }
                    } catch (IllegalArgumentException e) {
                        // Skip invalid positions silently
                    }
                }
            }
        }

        return false;
    }



    /**
     * Get all positions reachable from the start position
     * @param start Starting position index
     * @return Set of all reachable positions
     */
    public Set<Integer> getAllReachablePositions(BoardState currentState, int start) {
        if (start < 0) {
            throw new IllegalArgumentException("Start position cannot be negative");
        }

        Set<Integer> reachablePositions = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);
        reachablePositions.add(start);

        // Perform BFS
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // Get valid adjacent positions
            int[] directions = SolverUtils.getDirections(current, currentState);
            for (int next : directions) {
                // Skip invalid positions (-1) and already visited positions
                if (next != -1 && !reachablePositions.contains(next)) {
                    try {
                        // Check if the position is free (not wall or box)
                        if (currentState.isFreeSpace(next)) {
                            reachablePositions.add(next);
                            queue.add(next);
                        }
                    } catch (IllegalArgumentException e) {
                        // Skip invalid positions silently
                    }
                }
            }
        }

        return reachablePositions;
    }
}
