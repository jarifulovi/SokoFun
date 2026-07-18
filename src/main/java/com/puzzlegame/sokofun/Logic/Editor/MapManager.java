package com.puzzlegame.sokofun.Logic.Editor;

import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;


public class MapManager {

    private int[][][] map;
    private int boxGoal;


    public MapManager(int[][][] map, int boxGoal) {
        this.map = map;
        this.boxGoal = boxGoal;
    }

    public int[][][] getMap() {
        return this.map;
    }

    public int getBoxGoal() {
        return this.boxGoal;
    }

    public void updateMap(String buttonId, int row, int col) {
        switch (buttonId) {
            case "floorTileButton" -> this.map[GameConstants.GROUND_INDEX][row][col] = GameConstants.FLOOR;
            case "sandTileButton" -> this.map[GameConstants.GROUND_INDEX][row][col] = GameConstants.SAND;
            case "greenTileButton" -> this.map[GameConstants.GROUND_INDEX][row][col] = GameConstants.GREEN;
        }

        switch (buttonId) {
            case "stoneTileButton" -> this.map[GameConstants.BLOCK_INDEX][row][col] = GameConstants.STONE;
            case "stoneBlockTileButton" -> this.map[GameConstants.BLOCK_INDEX][row][col] = GameConstants.STONE_BLOCK;
            case "woodTileButton" -> this.map[GameConstants.BLOCK_INDEX][row][col] = GameConstants.WOOD;
            case "woodBlockTileButton" -> this.map[GameConstants.BLOCK_INDEX][row][col] = GameConstants.WOOD_BLOCK;
            case "redTileButton" -> this.map[GameConstants.BLOCK_INDEX][row][col] = GameConstants.RED;
            case "redBlockTileButton" -> this.map[GameConstants.BLOCK_INDEX][row][col] = GameConstants.RED_BLOCK;
        }

        if (this.map[GameConstants.BLOCK_INDEX][row][col] == GameConstants.NOTHING) {
            switch (buttonId) {
                case "woodGoalButton" -> this.map[GameConstants.OBJECT_INDEX][row][col] = GameConstants.WOOD_GOAL;
                case "greenGoalButton" -> this.map[GameConstants.OBJECT_INDEX][row][col] = GameConstants.GREEN_GOAL;
                case "stoneGoalButton" -> this.map[GameConstants.OBJECT_INDEX][row][col] = GameConstants.STONE_GOAL;
                case "blueGoalButton" -> this.map[GameConstants.OBJECT_INDEX][row][col] = GameConstants.BLUE_GOAL;
                case "redGoalButton" -> this.map[GameConstants.OBJECT_INDEX][row][col] = GameConstants.RED_GOAL;
                case "coinButton" -> this.map[GameConstants.OBJECT_INDEX][row][col] = GameConstants.COIN;
            }
        }

    }

    public void updatePlayer(int row, int col) {
        if (this.map[GameConstants.GROUND_INDEX][row][col] != GameConstants.FLOOR) return;
        if (this.map[GameConstants.BLOCK_INDEX][row][col] != GameConstants.NOTHING) return;
        this.map[GameConstants.BLOCK_INDEX][row][col] = GameConstants.PLAYER;
    }

    public void updateBox(int row, int col) {
        if (this.map[GameConstants.GROUND_INDEX][row][col] != GameConstants.FLOOR) return;
        if (this.map[GameConstants.BLOCK_INDEX][row][col] != GameConstants.NOTHING) return;
        this.map[GameConstants.BLOCK_INDEX][row][col] = GameConstants.BOX;
    }
}
