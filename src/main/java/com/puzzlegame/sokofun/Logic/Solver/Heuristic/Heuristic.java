package com.puzzlegame.sokofun.Logic.Solver.Heuristic;

import com.puzzlegame.sokofun.Logic.Solver.BoardState;

public interface Heuristic {

    int estimate(BoardState state);

}
