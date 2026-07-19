package com.puzzlegame.sokofun.Logic.Solver.Strategy;

import com.puzzlegame.sokofun.Logic.Solver.*;
import com.puzzlegame.sokofun.Logic.Solver.Heuristic.Heuristic;
import com.puzzlegame.sokofun.Object.Move;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class AStarStrategy implements SearchStrategy {

    private static class Node implements Comparable<Node> {
        BoardState state;
        int cost;
        int priority;

        Node(BoardState state, int cost, int priority) {
            this.state = state;
            this.cost = cost;
            this.priority = priority;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    private final SolverContext context;
    private final Heuristic heuristic;

    public AStarStrategy(SolverContext context, Heuristic heuristic) {
        this.context = context;
        this.heuristic = heuristic;
    }

    @Override
    public SearchResult solve(BoardState initialState) {

        MoveGenerator moveGenerator = context.getMoveGenerator();
        PathConstructor pathConstructor = context.getPathConstructor();
        DeadLock deadLock = context.getDeadLock();

        deadLock.clearStaticDeadLocks();

        PriorityQueue<Node> open = new PriorityQueue<>();

        Map<BoardState, BoardState> parentMap = new HashMap<>();
        Map<BoardState, Move> moveMap = new HashMap<>();
        Map<BoardState, Integer> costMap = new HashMap<>();

        BoardState start = initialState.copy();

        open.add(new Node(start, 0, heuristic.estimate(start)));

        parentMap.put(start, null);
        moveMap.put(start, null);
        costMap.put(start, 0);

        long iterations = 0;
        while (!open.isEmpty()) {

            Node currentNode = open.poll();
            BoardState current = currentNode.state;

            iterations++;
            if (iterations % 10000 == 0) {
                System.out.println("Expanded states: " + iterations);
                System.out.println("Queue size: " + open.size());
                System.out.println("Visited: " + costMap.size());
            }


            if (current.isSucceed()) {
                List<Move> moves =
                        pathConstructor.constructFullPath(
                                current,
                                parentMap,
                                moveMap
                        );

                return SearchResult.of(true, moves);
            }


            for (Move move : moveGenerator.getAllPossibleMoves(current)) {

                BoardState next = current.copy();

                int playerIdx = SolverUtils.toIndex(
                        move.getPrevRow(),
                        move.getPrevCol(),
                        next.getTotalCols()
                );

                next.setPlayerPosition(playerIdx);
                next.applyMove(move);


                if (deadLock.isDeadLock(next)) {
                    continue;
                }


                int newCost = currentNode.cost + 1;


                if (!costMap.containsKey(next)
                        || newCost < costMap.get(next)) {

                    costMap.put(next, newCost);

                    parentMap.put(next, current);
                    moveMap.put(next, move);


                    int priority = newCost + heuristic.estimate(next);

                    open.add(new Node(next, newCost, priority));
                }
            }
        }


        return SearchResult.unsolvable();
    }
}
