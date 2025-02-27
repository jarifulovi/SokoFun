package com.puzzlegame.sokofun.Logic.Abstract;

public final class GameConstants {

    private GameConstants() {
    }

    // Board size
    public static final int BOARD_WIDTH = 700;
    public static final int BOARD_HEIGHT = 600;

    public static final int TOTAL_LEVELS = 2;

    public static final int GROUND_INDEX = 0;
    public static final int BLOCK_INDEX = 1;
    public static final int OBJECT_INDEX = 2;
    // Tile types ( always beneath everything )
    public static final int NOTHING = 0;
    public static final int FLOOR = 1;
    public static final int SAND = 2;
    public static final int GREEN = 3;
    // Tile blocks ( with crates )
    public static final int STONE = 4;
    public static final int STONE_BLOCK = 5;
    public static final int WOOD = 6;
    public static final int WOOD_BLOCK = 7;
    public static final int RED = 8;
    public static final int RED_BLOCK = 9;
    // Tile crates
    public static final int BOX = 10;
    public static final int PLAYER = 11;

    // Objects ( overlap with crates and player )
    public static final int WOOD_GOAL = 12;
    public static final int BLUE_GOAL = 13;
    public static final int GREEN_GOAL = 14;
    public static final int RED_GOAL = 15;
    public static final int STONE_GOAL = 16;
    public static final int COIN = 17;

    // Game controls
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Fxml paths
    public static final String GAME_PANEL_FXML = "Fxml/gamePanel.fxml";
    public static final String MENU_PANEL_FXML = "Fxml/menuPanel.fxml";
    public static final String OPTIONS_PANEL_FXML = "Fxml/optionsPanel.fxml";

    // Asset paths

    // Player
    public static final String PLAYER_UP = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Player/player_up.png";
    public static final String PLAYER_DOWN = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Player/player_down.png";
    public static final String PLAYER_LEFT = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Player/player_left.png";
    public static final String PLAYER_RIGHT = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Player/player_right.png";
    // Tiles
    public static final String TILE_SAND_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Ground/sand.png";
    public static final String TILE_GREEN_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Ground/green.png";
    public static final String TILE_FLOOR_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Ground/floor.png";
    // Tile walls
    public static final String STONE_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Blocks/stone.png";
    public static final String STONE_BLOCK_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Blocks/stone_block.png";
    public static final String WOOD_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Blocks/wood.png";
    public static final String WOOD_BLOCK_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Blocks/wood_block.png";
    public static final String RED_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Blocks/red.png";
    public static final String RED_BLOCK_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Blocks/red_block.png";
    // Tile crates
    public static final String WOOD_BOX_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Crates/wood_box.png";
    public static final String STONE_BOX_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Crates/stone_box.png";
    public static final String BLUE_BOX_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Crates/blue_box.png";
    public static final String GREEN_BOX_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Crates/green_box.png";
    public static final String RED_BOX_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Crates/red_box.png";
    // Tile objects
    public static final String WOOD_GOAL_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Objects/wood_goal.png";
    public static final String BLUE_GOAL_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Objects/blue_goal.png";
    public static final String GREEN_GOAL_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Objects/green_goal.png";
    public static final String RED_GOAL_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Objects/red_goal.png";
    public static final String STONE_GOAL_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Objects/stone_goal.png";
    public static final String COIN_GOAL_PATH = "com/puzzlegame/sokofun/Assets/image-pack/PNG/Default size/Objects/coin.png";

    // level paths
    public static final String LEVEL_DIR_PATH = "/com/puzzlegame/sokofun/Levels/";
    public static final String GROUND_LAYER_FILE_PATH = "/groundLayer.txt";
    public static final String BLOCK_LAYER_FILE_PATH = "/blockLayer.txt";
    public static final String OBJECT_LAYER_FILE_PATH = "/objectLayer.txt";
}


