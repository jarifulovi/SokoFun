package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Object.Position;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class PathFinder {

    public boolean canPlayerReach(BoardState boardState,Position start, Set<Position> targets) {
        // Initialize a queue for BFS and a set to track visited positions
        Queue<Position> queue = new LinkedList<>();
        Set<Position> visited = new HashSet<>();

        // Add the player's starting position to the queue
        queue.add(start);
        visited.add(start);


        int[][] directions = {
                {-1, 0},  // Up
                {1, 0},   // Down
                {0, -1},  // Left
                {0, 1}    // Right
        };

        // Perform BFS
        while (!queue.isEmpty()) {
            Position current = queue.poll();

            // Check if the current position is one of the target positions
            if (targets.contains(current)) {
                return true;
            }

            // Check all 4 possible directions
            for (int[] direction : directions) {
                int newRow = current.getRow() + direction[0];
                int newCol = current.getCol() + direction[1];
                Position nextPos = new Position(newRow, newCol);

                // Check if the position is free space and not visited yet
                if (boardState.isFreeSpace(nextPos) && !visited.contains(nextPos)) {
                    visited.add(nextPos);
                    queue.add(nextPos);
                }
            }
        }

        return false;
    }


}
