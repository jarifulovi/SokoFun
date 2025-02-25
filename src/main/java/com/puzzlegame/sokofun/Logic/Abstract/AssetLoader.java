package com.puzzlegame.sokofun.Logic.Abstract;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AssetLoader {

    private static final Map<String, Image> images = new HashMap<>();

    public static void loadTiles() {
        loadImage(GameConstants.TILE_SAND_PATH);
        loadImage(GameConstants.TILE_GREEN_PATH);
        loadImage(GameConstants.TILE_FLOOR_PATH);

        loadImage(GameConstants.STONE_PATH);
        loadImage(GameConstants.STONE_BLOCK_PATH);
        loadImage(GameConstants.WOOD_PATH);
        loadImage(GameConstants.WOOD_BLOCK_PATH);
        loadImage(GameConstants.RED_PATH);
        loadImage(GameConstants.RED_BLOCK_PATH);

        loadImage(GameConstants.WOOD_BOX_PATH);
        loadImage(GameConstants.STONE_BOX_PATH);
        loadImage(GameConstants.BLUE_BOX_PATH);
        loadImage(GameConstants.GREEN_BOX_PATH);
        loadImage(GameConstants.RED_BOX_PATH);

        loadImage(GameConstants.WOOD_GOAL_PATH);
        loadImage(GameConstants.BLUE_GOAL_PATH);
        loadImage(GameConstants.GREEN_GOAL_PATH);
        loadImage(GameConstants.RED_GOAL_PATH);
        loadImage(GameConstants.STONE_GOAL_PATH);
        loadImage(GameConstants.COIN_GOAL_PATH);
    }

    public static void loadPlayer() {
        loadImage(GameConstants.PLAYER_UP);
        loadImage(GameConstants.PLAYER_DOWN);
        loadImage(GameConstants.PLAYER_LEFT);
        loadImage(GameConstants.PLAYER_RIGHT);
    }


    private static void loadImage(String path) {
        images.put(path, new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/"+path))));
    }
    public static Image getImage(String path) {
        return images.get(path);
    }
}
