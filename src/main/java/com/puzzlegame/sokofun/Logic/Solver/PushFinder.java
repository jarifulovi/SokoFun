package com.puzzlegame.sokofun.Logic.Solver;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;

import java.util.HashSet;
import java.util.Set;


public class PushFinder {



    public Set<Integer> getAllPushablePositions(BoardState boardState) {
        Set<Integer> pushablePositions = new HashSet<>();

        for(Integer position : boardState.getBoxPositions()) {
            getPushPosition(boardState, position, pushablePositions);
        }
        return pushablePositions;
    }


    private void getPushPosition(BoardState boardState, int boxPosition, Set<Integer> pushablePositions) {
        int[] directions = SolverUtils.getDirections(boxPosition, boardState);

        if (boardState.isFreeSpace(directions[GameConstants.UP])
                && boardState.isFreeSpace(directions[GameConstants.DOWN])) {
            pushablePositions.add(directions[GameConstants.UP]);
            pushablePositions.add(directions[GameConstants.DOWN]);
        }

        if (boardState.isFreeSpace(directions[GameConstants.LEFT])
                && boardState.isFreeSpace(directions[GameConstants.RIGHT])) {
            pushablePositions.add(directions[GameConstants.LEFT]);
            pushablePositions.add(directions[GameConstants.RIGHT]);
        }
    }
}
