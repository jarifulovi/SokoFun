package com.puzzlegame.sokofun.Logic.Abstract;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public final class ImagePathMap {

    private static final Map<Integer, String> IMAGE_PATH_MAP = new HashMap<>();

    static {
        IMAGE_PATH_MAP.put(GameConstants.PLAYER, GameConstants.PLAYER_DOWN);
        IMAGE_PATH_MAP.put(GameConstants.SAND, GameConstants.TILE_SAND_PATH);
        IMAGE_PATH_MAP.put(GameConstants.GREEN, GameConstants.TILE_GREEN_PATH);
        IMAGE_PATH_MAP.put(GameConstants.FLOOR, GameConstants.TILE_FLOOR_PATH);
        IMAGE_PATH_MAP.put(GameConstants.WOOD, GameConstants.WOOD_PATH);
        IMAGE_PATH_MAP.put(GameConstants.WOOD_BLOCK, GameConstants.WOOD_BLOCK_PATH);
        IMAGE_PATH_MAP.put(GameConstants.BOX, GameConstants.WOOD_BOX_PATH);
        IMAGE_PATH_MAP.put(GameConstants.STONE, GameConstants.STONE_PATH);
        IMAGE_PATH_MAP.put(GameConstants.STONE_BLOCK, GameConstants.STONE_BLOCK_PATH);
        IMAGE_PATH_MAP.put(GameConstants.RED, GameConstants.RED_PATH);
        IMAGE_PATH_MAP.put(GameConstants.RED_BLOCK, GameConstants.RED_BLOCK_PATH);
    }

    public static String getImagePath(int block) {
        return IMAGE_PATH_MAP.get(block);
    }

    public static Image getImage(int block) {
        String path = getImagePath(block);
        if (path != null) {
            return AssetLoader.getImage(path);
        }
        // Return a default green ground
        return AssetLoader.getImage(GameConstants.TILE_GREEN_PATH);
    }

    private ImagePathMap() {
        // Private constructor to prevent instantiation
    }
}