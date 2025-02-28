package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Logic.Abstract.Utils;
import com.puzzlegame.sokofun.Object.Position;

import java.util.HashSet;
import java.util.Set;

public class Converter {

    private BoardState boardState;
    private int[][][] board;

    public Converter(int[][][] board) {
        this.board = board;
        this.boardState = new BoardState();
        boardState.setTotalRows(board[0].length);
        boardState.setTotalCols(board[0][0].length);
        setPlayerPositions();
        setBoxPositions();
        setGoalPositions();
        setWallPositions();
    }

    public BoardState getSolverState() {
        return boardState;
    }

    private void setPlayerPositions() {
        int[] playerPosition = Utils.getPlayerPosition(board);
        Position position = new Position(playerPosition[0], playerPosition[1]);
        boardState.setPlayerPosition(position);
    }

    private void setBoxPositions() {

        Set<Position> boxPositions = new HashSet<>();

        for (int row = 0; row < boardState.getTotalRows(); row++) {
            for (int col = 0; col < boardState.getTotalCols(); col++) {
                if (board[GameConstants.BLOCK_INDEX][row][col] == GameConstants.BOX) {
                    Position position = new Position(row, col);
                    boxPositions.add(position);
                }
            }
        }

        boardState.setBoxPositions(boxPositions);
    }

    private void setGoalPositions() {

        Set<Position> goalPositions = new HashSet<>();

        for (int row = 0; row < boardState.getTotalRows(); row++) {
            for (int col = 0; col < boardState.getTotalCols(); col++) {
                if (Utils.isGoal(board[GameConstants.OBJECT_INDEX][row][col])) {
                    Position position = new Position(row, col);
                    goalPositions.add(position);
                }
            }
        }

        boardState.setGoalPositions(goalPositions);
    }

    private void setWallPositions() {

        Set<Position> wallPositions = new HashSet<>();

        for (int row = 0; row < boardState.getTotalRows(); row++) {
            for (int col = 0; col < boardState.getTotalCols(); col++) {
                if (Utils.isWall(board[GameConstants.BLOCK_INDEX][row][col])) {
                    Position position = new Position(row, col);
                    wallPositions.add(position);
                }
            }
        }

        boardState.setWallPositions(wallPositions);
    }


    public static void main(String[] args) {

        int[][][] board = new int[3][3][3];


//        board[GameConstants.BLOCK_INDEX][0][1] = GameConstants.BOX;
//        board[GameConstants.OBJECT_INDEX][1][2] = GameConstants.RED_GOAL;
//        board[GameConstants.OBJECT_INDEX][2][2] = GameConstants.BLUE_GOAL;
//        board[GameConstants.BLOCK_INDEX][2][1] = GameConstants.RED_BLOCK;
//        board[GameConstants.BLOCK_INDEX][0][0] = GameConstants.PLAYER;
//
//        // Initialize the Converter with the board
//        Converter converter = new Converter(board);
//
//        // Debug output for player, box, goal, and wall positions
//        System.out.println("Player Position: " + converter.getSolverState().getPlayerPosition());
//        System.out.println("Box Positions: " + converter.getSolverState().getBoxPositions());
//        System.out.println("Goal Positions: " + converter.getSolverState().getGoalPositions());
//        System.out.println("Wall Positions: " + converter.getSolverState().getWallPositions());
//

    }

}