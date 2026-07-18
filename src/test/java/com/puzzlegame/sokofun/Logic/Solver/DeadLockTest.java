package com.puzzlegame.sokofun.Logic.Solver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.HashSet;

class DeadLockTest {

    private DeadLock deadLock;
    private BoardState mockBoardState;

    @BeforeEach
    void setUp() {
        deadLock = new DeadLock();
        mockBoardState = new BoardState();

        // Mock board state setup
        mockBoardState.setTotalRows(5);
        mockBoardState.setTotalCols(5);

        Set<Integer> wallPositions = new HashSet<>();
        wallPositions.add(0);
        wallPositions.add(1);
        wallPositions.add(2);
        mockBoardState.setWallPositions(wallPositions);

        Set<Integer> boxPositions = new HashSet<>();
        boxPositions.add(6);
        mockBoardState.setBoxPositions(boxPositions);

        Set<Integer> goalPositions = new HashSet<>();
        goalPositions.add(24);
        mockBoardState.setGoalPositions(goalPositions);
    }

    @Test
    void testIsDeadLock_NoDeadlock() {
        assertFalse(deadLock.isDeadLock(mockBoardState), "No deadlock should be detected.");
    }

    @Test
    void testIsDeadLock_CornerDeadlock() {
        mockBoardState.getBoxPositions().add(20); // Place a box in a corner
        assertTrue(deadLock.isDeadLock(mockBoardState), "Corner deadlock should be detected.");
    }

    @Test
    void testCheckStaticDeadLock() {
        deadLock.clearStaticDeadLocks();
        assertFalse(deadLock.checkStaticDeadLock(mockBoardState), "No static deadlock should be detected.");
    }

    @Test
    void testAddStaticDeadLockPosition() {
        deadLock.clearStaticDeadLocks();

        // Update mockBoardState to simulate a static deadlock scenario
        Set<Integer> wallPositions = new HashSet<>();
        wallPositions.add(0);
        wallPositions.add(1);
        wallPositions.add(2);
        wallPositions.add(5);
        wallPositions.add(10);
        wallPositions.add(15);
        mockBoardState.setWallPositions(wallPositions);

        Set<Integer> boxPositions = new HashSet<>();
        boxPositions.add(6);
        mockBoardState.setBoxPositions(boxPositions);

        Set<Integer> goalPositions = new HashSet<>();
        goalPositions.add(24);
        mockBoardState.setGoalPositions(goalPositions);

        // Add static deadlock positions and verify
        deadLock.addStaticDeadLockPosition(mockBoardState);
        assertTrue(deadLock.checkStaticDeadLock(mockBoardState), "Static deadlock should be detected after adding.");
    }

    @Test
    void testIsInCorner() {
        int cornerPosition = 20; // Mock corner position
        mockBoardState.getBoxPositions().add(cornerPosition);
        assertTrue(deadLock.isInCorner(mockBoardState, cornerPosition), "Position should be detected as a corner.");
    }

    @Test
    void testIsBlockedDirection() {
        int blockedPosition = 0; // Mock wall position
        assertTrue(deadLock.isBlockedDirection(mockBoardState, blockedPosition), "Position should be detected as blocked.");
    }
}
