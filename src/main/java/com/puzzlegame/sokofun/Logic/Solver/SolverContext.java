package com.puzzlegame.sokofun.Logic.Solver;

/**
 * Aggregates commonly used solver services so strategies and solvers can share the same
 * utilities (push finding, path finding, deadlock detection, etc.).
 *
 * Placed under the Strategy package to be used by future concrete strategies
 * (e.g., BFS, A*, IDA*).
 */
public class SolverContext {

    private final PushFinder pushFinder;
    private final PathFinder pathFinder;
    private final DeadLock deadLock;
    private final MoveGenerator moveGenerator;
    private final PathConstructor pathConstructor;

    public SolverContext() {
        this.pushFinder = new PushFinder();
        this.pathFinder = new PathFinder();
        this.deadLock = new DeadLock();
        this.moveGenerator = new MoveGenerator(this.pushFinder, this.pathFinder);
        this.pathConstructor = new PathConstructor(this.pathFinder);
    }

    public PushFinder getPushFinder() { return pushFinder; }
    public PathFinder getPathFinder() { return pathFinder; }
    public DeadLock getDeadLock() { return deadLock; }
    public MoveGenerator getMoveGenerator() { return moveGenerator; }
    public PathConstructor getPathConstructor() { return pathConstructor; }
}
