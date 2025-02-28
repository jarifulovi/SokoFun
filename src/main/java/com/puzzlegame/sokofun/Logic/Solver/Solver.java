package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.GameLogic.LevelLoader;
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

    public boolean getIsSolvable() {
        return isSolvable;
    }
    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;
    }

    private void solve(int[][][] board) {
        converter = new Converter(board);
        boardState = converter.getSolverState();
        // Implement BFS, A*, or other search algorithms to find a solution
        // If a solution is found, populate `moves` and set `isSolvable = true`
    }

    private void generateAllPossibleMove() {
        // check state to gen moves
        Set<Position> pushablePositions = getAllPushablePositions();
        // check pathfinding to reach
        // return the move
    }

    private Set<Position> getAllPushablePositions() {
        Set<Position> pushablePositions = new HashSet<>();

        for(Position position : boardState.getBoxPositions()) {
            getPushPosition(position,pushablePositions);
        }
        return pushablePositions;
    }

    private void getPushPosition(Position boxPosition,Set<Position> pushablePositions) {

        int[][] offsets = {
                {-1, 0},  // Up
                {0, -1},  // Left
        };

        for (int[] offset : offsets) {
            int directionRow = offset[0];
            int directionCol = offset[1];

            int pushRow = boxPosition.getRow() + directionRow;
            int pushCol = boxPosition.getCol() + directionCol;
            Position pos = new Position(pushRow,pushCol);

            int oppositeRow = boxPosition.getRow() - directionRow;
            int oppositeCol = boxPosition.getCol() - directionCol;
            Position oppositePos = new Position(oppositeRow, oppositeCol);


            if (boardState.isFreeSpace(pos) && boardState.isFreeSpace(oppositePos)) {
                pushablePositions.add(pos);
                pushablePositions.add(oppositePos);
            }
        }
    }

    private void pathFinding() {
        // check success
        // use bfs
        // if pos is pushable perform push
        // create move ( direction always opposite )
        // applyMove(move)
        // calc the pushable again ( opt by updating adj one's )
        // use bfs on new state
        // if deadlock then go back
    }


    public static void main(String[] args) {
        int level = 2;
        LevelLoader loader = new LevelLoader(level);
        int[][][] board = loader.getLevelBoard();
        //Utils.displayBoard(board);
        Converter converter = new Converter(board);
        BoardState boardState = converter.getSolverState();
        SolverUtils.displayBoxPos(boardState.getBoxPositions(), boardState.getTotalRows(), boardState.getTotalCols());
        Solver solver = new Solver();
        solver.setBoardState(boardState);
        SolverUtils.displayPushes(solver.getAllPushablePositions(), boardState.getTotalRows(), boardState.getTotalCols());
        System.out.println(solver.getAllPushablePositions().size());
    }

    // Notes
    // Pushable positions are bi-direction ( if left then also right )
    // Player doesn't need to account direction of that pushable pos
    // Pushable also check player theoretical reachability
    // Pushable position can contain push for multi boxes ( 1 -> 2 <- 3 where 1,3 box pos )
}