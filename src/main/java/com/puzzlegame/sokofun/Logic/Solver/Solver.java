package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Logic.GameLogic.LevelLoader;
import com.puzzlegame.sokofun.Object.Move;

import java.util.*;

// Only bfs for now (will implement the strategy pattern later if needed)
public class Solver {

    private PushFinder pushFinder;
    private PathFinder pathFinder;
    private DeadLock deadLock;
    private List<Move> moves;
    private boolean isSolvable;

    public Solver() {
        this.pushFinder = new PushFinder();
        this.pathFinder = new PathFinder();
        this.deadLock = new DeadLock();
        this.moves = new ArrayList<>();
        this.isSolvable = false;
    }

    public boolean getIsSolvable() {
        return isSolvable;
    }

    public List<Move> getMoves() {
        if (moves == null || moves.isEmpty()) {
            throw new IllegalStateException("No moves computed. Please run solve() first or the board is unsolvable.");
        }
        return moves;
    }


    public void solve(BoardState boardState) {

        this.deadLock.clearStaticDeadLocks();
        // BFS queue
        Queue<BoardState> queue = new LinkedList<>();
        queue.add(boardState.copy());

        // Track visited states
        Set<BoardState> visited = new HashSet<>();
        visited.add(boardState.copy());

        // Simple path tracking: Map state to its parent and the move that created it
        Map<BoardState, BoardState> parentMap = new HashMap<>();
        Map<BoardState, Move> moveMap = new HashMap<>();

        BoardState initial = boardState.copy();
        parentMap.put(initial, null);
        moveMap.put(initial, null);

        while (!queue.isEmpty()) {
            BoardState current = queue.poll();

            if (current.isSucceed()) {
                reconstructPath(current, parentMap, moveMap);
                this.isSolvable = true;
                return;
            }

            List<Move> possibleMoves = getAllPossibleMove(current);
            // System.out.println("Possible moves from current state: " + possibleMoves.size());

            for (Move move : possibleMoves) {
                BoardState next = current.copy();
                // update player position to pushable pos (like a teleport)
                int playerPos = SolverUtils.toIndex(move.getPrevRow(), move.getPrevCol(), next.getTotalCols());
                next.setPlayerPosition(playerPos);


                next.applyMove(move);

                if (deadLock.isDeadLock(next)) continue; // prune


                if (!visited.contains(next)) {
                    visited.add(next);
                    parentMap.put(next, current);
                    moveMap.put(next, move);
                    queue.add(next);
                }
            }
        }

        System.err.println("No solution found in solver().");
        this.isSolvable = false;
    }

    private void reconstructPath(BoardState solution, Map<BoardState, BoardState> parentMap, Map<BoardState, Move> moveMap) {
        // 1) Backtrack to get push-only moves from start to solution
        List<Move> pushOnly = new ArrayList<>();
        BoardState current = solution;
        while (parentMap.get(current) != null) {
            Move move = moveMap.get(current);
            if (move != null) {
                pushOnly.add(move);
            }
            current = parentMap.get(current);
        }
        Collections.reverse(pushOnly);

        // 2) Reconstruct initial state by walking back to the root node
        BoardState root = solution;
        while (parentMap.get(root) != null) {
            root = parentMap.get(root);
        }
        BoardState simulator = root.copy();
        int currentPlayerIdx = simulator.getPlayerPosition();

        // 3) Expand to full sequence including in-between player moves
        List<Move> fullMoves = new ArrayList<>();
        for (Move pushMove : pushOnly) {
            // Where the player must stand to perform the push
            int pushPosIdx = SolverUtils.toIndex(pushMove.getPrevRow(), pushMove.getPrevCol(), simulator.getTotalCols());

            // Find shortest free-space path from current player to required push position
            List<Integer> path = pathFinder.getShortestPath(simulator, currentPlayerIdx, pushPosIdx);
            if (path.isEmpty()) {
                // Should not happen since BFS ensured reachability, but guard anyway
                throw new IllegalStateException("No path found to push position during reconstruction");
            }
            // Convert index path to walking moves (skip the first index which is current position)
            for (int i = 1; i < path.size(); i++) {
                int from = path.get(i - 1);
                int to = path.get(i);
                int fromRow = SolverUtils.getRow(from, simulator.getTotalCols());
                int fromCol = SolverUtils.getCol(from, simulator.getTotalCols());
                int toRow = SolverUtils.getRow(to, simulator.getTotalCols());
                int toCol = SolverUtils.getCol(to, simulator.getTotalCols());
                int dir = (toRow < fromRow) ? GameConstants.UP :
                          (toRow > fromRow) ? GameConstants.DOWN :
                          (toCol < fromCol) ? GameConstants.LEFT : GameConstants.RIGHT;
                Move walk = new Move(fromRow, fromCol, toRow, toCol, dir);
                // walk move is not a push
                simulator.applyMove(walk);
                fullMoves.add(walk);
            }
            // Now perform the actual push
            simulator.applyMove(pushMove);
            fullMoves.add(pushMove);
            currentPlayerIdx = simulator.getPlayerPosition();
        }

        this.moves = fullMoves;
    }



    private List<Move> getAllPossibleMove(BoardState boardState) {
        if (boardState == null) {
            throw new IllegalStateException("Board state is not initialized");
        }
        // check state to gen moves
        Set<Integer> pushablePositions = pushFinder.getAllPushablePositions(boardState);

        Set<Integer> validPushablePositions = pathFinder.getReachableTargets(boardState,
                            boardState.getPlayerPosition(), pushablePositions);


        return generateValidMoves(boardState, validPushablePositions);
    }


    private List<Move> generateValidMoves(BoardState boardState, Set<Integer> validPushablePositions) {
        List<Move> validMoves = new ArrayList<>();
        for (Integer pushPos : validPushablePositions) {
            int[] directions = SolverUtils.getDirections(pushPos, boardState);
            for (int dir : directions) {
                if (dir != -1 && boardState.getBoxPositions().contains(dir)) {
                    // Calculate where the box would go after being pushed
                    int boxDestination = SolverUtils.calculateBoxDestination(pushPos, dir, boardState);

                    // Validate that the box can actually be pushed to the destination
                    if (!SolverUtils.isValidBoxDestination(boxDestination, boardState)) {
                        continue; // Skip this invalid push move
                    }

                    // Create a move
                    int preRow = SolverUtils.getRow(pushPos, boardState.getTotalCols());
                    int preCol = SolverUtils.getCol(pushPos, boardState.getTotalCols());
                    int newRow = SolverUtils.getRow(dir, boardState.getTotalCols());
                    int newCol = SolverUtils.getCol(dir, boardState.getTotalCols());
                    int direction = (preRow > newRow ) ? GameConstants.UP :
                                    (preRow < newRow) ? GameConstants.DOWN :
                                    (preCol > newCol) ? GameConstants.LEFT :
                                    (preCol < newCol) ? GameConstants.RIGHT : -1;
                    if (direction == -1) {
                        throw new IllegalStateException("Invalid move direction");
                    }
                    Move move = new Move(preRow, preCol, newRow, newCol, direction);
                    move.setIsPush(true);
                    validMoves.add(move);
                }
            }
        }
        return validMoves;
    }


    public static void main(String[] args) {
        try {
            int level = 5; // default test level
            long slower = 1500;
            LevelLoader loader = new LevelLoader(level);
            int[][][] board = loader.getLevelBoard();
            Converter converter = new Converter(board);
            BoardState boardState = converter.getSolverState();

            // Print initial board
            boardState.printBoard();
            Thread.sleep(slower);

            Solver solver = new Solver();
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