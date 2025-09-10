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

                // if (deadLock.isDeadLock(next)) continue; // prune


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
        List<Move> path = new ArrayList<>();
        BoardState current = solution;

        // Backtrack from solution to initial state
        while (parentMap.get(current) != null) {
            Move move = moveMap.get(current);
            if (move != null) {
                path.add(move);
            }
            current = parentMap.get(current);
        }

        // Reverse to get the correct order (from start to solution)
        Collections.reverse(path);
        this.moves = path;
        // We ignore the in between player moves for now
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
            int level = 3;
            LevelLoader loader = new LevelLoader(level);
            int[][][] board = loader.getLevelBoard();
            Converter converter = new Converter(board);
            BoardState boardState = converter.getSolverState();

            System.out.println("Initial board state:");
            System.out.println("Player position: " + boardState.getPlayerPosition());
            System.out.println("Box positions: " + boardState.getBoxPositions());
            System.out.println("Goal positions: " + boardState.getGoalPositions());
            System.out.println("Board size: " + boardState.getTotalRows() + "x" + boardState.getTotalCols());

            Solver solver = new Solver();
            // Start the solving process and measure time
            long startTime = System.currentTimeMillis();
            solver.solve(boardState);
            long endTime = System.currentTimeMillis();

            if (solver.getIsSolvable()) {
                List<Move> moves = solver.getMoves();
                System.out.println("Solution found in " + moves.size() + " moves.");
                for (Move move : moves) {
                    System.out.println(move.toString());
                }
            } else {
                System.out.println("No solution exists.");
            }
            System.out.println("Time taken: " + (endTime - startTime) + " ms");
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        // Notes
        // Pushable positions are bi-direction ( if left then also right ignoring player reachability )
        // After pushable player reachability is filtered
        // Then moves are generated in all 4 directions for each pushable position (assuming box is there)
        // If no box present then there is bug in pushable position generation
        // In-between player moves are ignored ( only consider moves to pushable positions )
        // So before update player position should be set to pushable position
    }
}