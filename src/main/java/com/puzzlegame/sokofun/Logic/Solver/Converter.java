package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Logic.Abstract.Utils;

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
        int playerIndex = SolverUtils.toIndex(playerPosition[0], playerPosition[1], boardState.getTotalCols());
        boardState.setPlayerPosition(playerIndex);
    }


    private void setBoxPositions() {

        Set<Integer> boxPositions = new HashSet<>();

        for (int row = 0; row < boardState.getTotalRows(); row++) {
            for (int col = 0; col < boardState.getTotalCols(); col++) {
                if (board[GameConstants.BLOCK_INDEX][row][col] == GameConstants.BOX) {
                    int index = SolverUtils.toIndex(row, col, boardState.getTotalCols());
                    boxPositions.add(index);
                }
            }
        }

        boardState.setBoxPositions(boxPositions);
    }

    private void setGoalPositions() {

        Set<Integer> goalPositions = new HashSet<>();

        for (int row = 0; row < boardState.getTotalRows(); row++) {
            for (int col = 0; col < boardState.getTotalCols(); col++) {
                if (Utils.isGoal(board[GameConstants.OBJECT_INDEX][row][col])) {
                    int index = SolverUtils.toIndex(row, col, boardState.getTotalCols());
                    goalPositions.add(index);
                }
            }
        }

        boardState.setGoalPositions(goalPositions);
    }

    private void setWallPositions() {

        Set<Integer> wallPositions = new HashSet<>();

        for (int row = 0; row < boardState.getTotalRows(); row++) {
            for (int col = 0; col < boardState.getTotalCols(); col++) {
                if (Utils.isWall(board[GameConstants.BLOCK_INDEX][row][col])) {
                    int index = SolverUtils.toIndex(row, col, boardState.getTotalCols());
                    wallPositions.add(index);
                }
            }
        }

        boardState.setWallPositions(wallPositions);
    }


    public static void main(String[] args) {

        int[][][] board = new int[3][3][3];


        board[GameConstants.BLOCK_INDEX][0][1] = GameConstants.BOX;
        board[GameConstants.OBJECT_INDEX][1][2] = GameConstants.RED_GOAL;
        board[GameConstants.OBJECT_INDEX][2][2] = GameConstants.BLUE_GOAL;
        board[GameConstants.BLOCK_INDEX][2][1] = GameConstants.RED_BLOCK;
        board[GameConstants.BLOCK_INDEX][0][0] = GameConstants.PLAYER;

        // Initialize the Converter with the board
        Converter converter = new Converter(board);

        // Debug output for player, box, goal, and wall positions
        System.out.println("Player Position: " + converter.getSolverState().getPlayerPosition());
        System.out.println("Box Positions: " + converter.getSolverState().getBoxPositions());
        System.out.println("Goal Positions: " + converter.getSolverState().getGoalPositions());
        System.out.println("Wall Positions: " + converter.getSolverState().getWallPositions());


    }

}