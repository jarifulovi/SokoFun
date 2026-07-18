package com.puzzlegame.sokofun.Controllers;

import com.puzzlegame.sokofun.Logic.Abstract.AssetLoader;
import com.puzzlegame.sokofun.Logic.Abstract.FxmlLoader;
import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Logic.Abstract.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class GameEditorPanelController {
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private Canvas mapCanvas;
    @FXML
    private VBox stateBox;
    @FXML
    private Button floorTileButton;
    @FXML
    private Button sandTileButton;
    @FXML
    private Button greenTileButton;
    @FXML
    private Button stoneTileButton;
    @FXML
    private Button woodTileButton;
    @FXML
    private Button redTileButton;
    @FXML
    private Button stoneBlockTileButton;
    @FXML
    private Button woodBlockTileButton;
    @FXML
    private Button redBlockTileButton;
    @FXML
    private Button woodGoalButton;
    @FXML
    private Button greenGoalButton;
    @FXML
    private Button stoneGoalButton;
    @FXML
    private Button blueGoalButton;
    @FXML
    private Button redGoalButton;
    @FXML
    private Button coinButton;
    @FXML
    private Button addPlayerButton;
    @FXML
    private Button addBoxButton;
    @FXML
    private Button quitButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button doneButton;

    // Controller fields
    private int mapWidth;
    private int mapHeight;
    private int boxGoal;
    private int[][][] map;
    private Image selectedTileImage;
    private int selectedType = GameConstants.NOTHING;
    private int selectedLayer = GameConstants.GROUND_INDEX;

    @FXML
    public void initialize() {
        AssetLoader.loadTiles();
        AssetLoader.loadPlayer();
        this.selectedTileImage = null;
    }


    public void setMapConstraints(int mapWidth, int mapHeight, int boxGoal) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.boxGoal = boxGoal;
        // Board format: [layer][row][col]
        this.map = new int[3][this.mapHeight][this.mapWidth];
        renderInitialMap();
        setupMouseClickHandler();
    }

    public void handleGroundTile(ActionEvent event) {
        this.selectedLayer = GameConstants.GROUND_INDEX;
        switch (((Button)event.getSource()).getId()) {
            case "floorTileButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.TILE_FLOOR_PATH); this.selectedType = GameConstants.FLOOR; }
            case "sandTileButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.TILE_SAND_PATH); this.selectedType = GameConstants.SAND; }
            case "greenTileButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.TILE_GREEN_PATH); this.selectedType = GameConstants.GREEN; }
        }
    }

    public void handleBlockTile(ActionEvent event) {
        this.selectedLayer = GameConstants.BLOCK_INDEX;
        switch (((Button)event.getSource()).getId()) {
            case "stoneTileButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.STONE_PATH); this.selectedType = GameConstants.STONE; }
            case "stoneBlockTileButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.STONE_BLOCK_PATH); this.selectedType = GameConstants.STONE_BLOCK; }
            case "woodTileButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.WOOD_PATH); this.selectedType = GameConstants.WOOD; }
            case "woodBlockTileButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.WOOD_BLOCK_PATH); this.selectedType = GameConstants.WOOD_BLOCK; }
            case "redTileButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.RED_PATH); this.selectedType = GameConstants.RED; }
            case "redBlockTileButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.RED_BLOCK_PATH); this.selectedType = GameConstants.RED_BLOCK; }
        }
    }

    public void handleGoalTile(ActionEvent event) {
        this.selectedLayer = GameConstants.OBJECT_INDEX;
        switch (((Button)event.getSource()).getId()) {
            case "woodGoalButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.WOOD_GOAL_PATH); this.selectedType = GameConstants.WOOD_GOAL; }
            case "greenGoalButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.GREEN_GOAL_PATH); this.selectedType = GameConstants.GREEN_GOAL; }
            case "stoneGoalButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.STONE_GOAL_PATH); this.selectedType = GameConstants.STONE_GOAL; }
            case "blueGoalButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.BLUE_GOAL_PATH); this.selectedType = GameConstants.BLUE_GOAL; }
            case "redGoalButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.RED_GOAL_PATH); this.selectedType = GameConstants.RED_GOAL; }
            case "coinButton" -> { this.selectedTileImage = AssetLoader.getImage(GameConstants.COIN_GOAL_PATH); this.selectedType = GameConstants.COIN; }
        }
    }

    public void handleAddPlayer() {
        this.selectedTileImage = AssetLoader.getImage(GameConstants.PLAYER_DOWN);
        this.selectedType = GameConstants.PLAYER;
        this.selectedLayer = GameConstants.BLOCK_INDEX;
    }

    public void handleAddBox() {
        this.selectedTileImage = AssetLoader.getImage(GameConstants.WOOD_BOX_PATH);
        this.selectedType = GameConstants.BOX;
        this.selectedLayer = GameConstants.BLOCK_INDEX;
    }


    private void renderInitialMap() {
        var gc = this.mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, this.mapCanvas.getWidth(), this.mapCanvas.getHeight());

        double tileSize = getTileSize(this.mapWidth, this.mapHeight);

        // Fill background with a soft, non-intrusive light tone that won't clash with tiles
        gc.setFill(javafx.scene.paint.Color.web("#E9EEF5")); // light desaturated blue-gray
        gc.fillRect(0, 0, this.mapCanvas.getWidth(), this.mapCanvas.getHeight());

        // Draw grid lines slightly darker for visibility
        gc.setStroke(javafx.scene.paint.Color.web("#8A8F98"));
        for (int x = 0; x <= this.mapWidth; x++) {
            gc.strokeLine(x * tileSize, 0, x * tileSize, this.mapHeight * tileSize);
        }
        for (int y = 0; y <= this.mapHeight; y++) {
            gc.strokeLine(0, y * tileSize, this.mapWidth * tileSize, y * tileSize);
        }
    }

    private void setupMouseClickHandler() {
        mapCanvas.setOnMouseClicked(event -> {

            if (this.selectedTileImage == null) return;

            double tileSize = getTileSize(this.mapWidth, this.mapHeight);
            int col = (int)(event.getX() / tileSize);
            int row = (int)(event.getY() / tileSize);

            // Bounds safety
            if (row < 0 || row >= mapHeight || col < 0 || col >= mapWidth) return;

            // Enforce box placement limit
            if (selectedLayer == GameConstants.BLOCK_INDEX && selectedType == GameConstants.BOX) {
                boolean placingNewBox = map[GameConstants.BLOCK_INDEX][row][col] != GameConstants.BOX;
                if (placingNewBox) {
                    int currentBoxes = countBoxes();
                    if (currentBoxes >= boxGoal) {
                        System.out.println("Box limit reached (" + boxGoal + "). You cannot place more boxes.");
                        return;
                    }
                }
            }

            // Enforce goals placement limit to match boxes goal
            if (selectedLayer == GameConstants.OBJECT_INDEX && Utils.isGoal(selectedType)) {
                boolean placingNewGoal = !Utils.isGoal(map[GameConstants.OBJECT_INDEX][row][col]);
                if (placingNewGoal) {
                    int currentGoals = countGoals();
                    if (currentGoals >= boxGoal) {
                        System.out.println("Goal limit reached (" + boxGoal + "). You cannot place more goals.");
                        return;
                    }
                }
            }

            var gc = mapCanvas.getGraphicsContext2D();
            gc.drawImage(this.selectedTileImage, col * tileSize, row * tileSize, tileSize, tileSize);

            // redraw grid border on top
            gc.setStroke(javafx.scene.paint.Color.GRAY);
            gc.strokeRect(col * tileSize, row * tileSize, tileSize, tileSize);

            // Update underlying map model [layer][row][col]
            if (selectedType == GameConstants.PLAYER) {
                // ensure only one player exists: clear previous and redraw those cells
                for (int r = 0; r < mapHeight; r++) {
                    for (int c = 0; c < mapWidth; c++) {
                        if (map[GameConstants.BLOCK_INDEX][r][c] == GameConstants.PLAYER) {
                            map[GameConstants.BLOCK_INDEX][r][c] = GameConstants.NOTHING;
                            // redraw previous player cell from map contents
                            drawCellFromMap(r, c);
                        }
                    }
                }
            }
            map[selectedLayer][row][col] = selectedType;
        });
    }



    private double getTileSize(int mapWidth, int mapHeight) {
        double tileWidth = mapCanvas.getWidth() / mapWidth;
        double tileHeight = mapCanvas.getHeight() / mapHeight;
        return Math.min(tileWidth, tileHeight);
    }


    // Helpers to count entities on the map
    private int countBoxes() {
        int count = 0;
        for (int r = 0; r < mapHeight; r++) {
            for (int c = 0; c < mapWidth; c++) {
                if (map[GameConstants.BLOCK_INDEX][r][c] == GameConstants.BOX) count++;
            }
        }
        return count;
    }

    private int countPlayers() {
        int count = 0;
        for (int r = 0; r < mapHeight; r++) {
            for (int c = 0; c < mapWidth; c++) {
                if (map[GameConstants.BLOCK_INDEX][r][c] == GameConstants.PLAYER) count++;
            }
        }
        return count;
    }

    private int countGoals() {
        int count = 0;
        for (int r = 0; r < mapHeight; r++) {
            for (int c = 0; c < mapWidth; c++) {
                if (Utils.isGoal(map[GameConstants.OBJECT_INDEX][r][c])) count++;
            }
        }
        return count;
    }

    // Redraw a single cell from map layers (without rendering PLAYER)
    private void drawCellFromMap(int row, int col) {
        var gc = mapCanvas.getGraphicsContext2D();
        double tileSize = getTileSize(this.mapWidth, this.mapHeight);
        // Clear cell area by painting background and grid
        gc.setFill(javafx.scene.paint.Color.web("#E9EEF5"));
        gc.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
        gc.setStroke(javafx.scene.paint.Color.web("#8A8F98"));
        gc.strokeRect(col * tileSize, row * tileSize, tileSize, tileSize);

        // Draw ground layer if any
        int groundType = map[GameConstants.GROUND_INDEX][row][col];
        if (groundType != GameConstants.NOTHING) {
            Image img = getImageForType(groundType, row, col);
            if (img != null) gc.drawImage(img, col * tileSize, row * tileSize, tileSize, tileSize);
        }
        // Draw block layer if any (except PLAYER, which we intentionally skip here)
        int blockType = map[GameConstants.BLOCK_INDEX][row][col];
        if (blockType != GameConstants.NOTHING && blockType != GameConstants.PLAYER) {
            Image img = getImageForType(blockType, row, col);
            if (img != null) gc.drawImage(img, col * tileSize, row * tileSize, tileSize, tileSize);
        }
        // Draw object layer if any
        int objType = map[GameConstants.OBJECT_INDEX][row][col];
        if (objType != GameConstants.NOTHING) {
            Image img = getImageForType(objType, row, col);
            if (img != null) gc.drawImage(img, col * tileSize, row * tileSize, tileSize, tileSize);
        }
    }

    // Map a type to its image. For BOX we use default wood box; coin uses coin image.
    private Image getImageForType(int type, int row, int col) {
        return switch (type) {
            case GameConstants.FLOOR -> AssetLoader.getImage(GameConstants.TILE_FLOOR_PATH);
            case GameConstants.SAND -> AssetLoader.getImage(GameConstants.TILE_SAND_PATH);
            case GameConstants.GREEN -> AssetLoader.getImage(GameConstants.TILE_GREEN_PATH);
            case GameConstants.STONE -> AssetLoader.getImage(GameConstants.STONE_PATH);
            case GameConstants.STONE_BLOCK -> AssetLoader.getImage(GameConstants.STONE_BLOCK_PATH);
            case GameConstants.WOOD -> AssetLoader.getImage(GameConstants.WOOD_PATH);
            case GameConstants.WOOD_BLOCK -> AssetLoader.getImage(GameConstants.WOOD_BLOCK_PATH);
            case GameConstants.RED -> AssetLoader.getImage(GameConstants.RED_PATH);
            case GameConstants.RED_BLOCK -> AssetLoader.getImage(GameConstants.RED_BLOCK_PATH);
            case GameConstants.BOX -> AssetLoader.getImage(GameConstants.WOOD_BOX_PATH);
            case GameConstants.WOOD_GOAL -> AssetLoader.getImage(GameConstants.WOOD_GOAL_PATH);
            case GameConstants.BLUE_GOAL -> AssetLoader.getImage(GameConstants.BLUE_GOAL_PATH);
            case GameConstants.GREEN_GOAL -> AssetLoader.getImage(GameConstants.GREEN_GOAL_PATH);
            case GameConstants.RED_GOAL -> AssetLoader.getImage(GameConstants.RED_GOAL_PATH);
            case GameConstants.STONE_GOAL -> AssetLoader.getImage(GameConstants.STONE_GOAL_PATH);
            case GameConstants.COIN -> AssetLoader.getImage(GameConstants.COIN_GOAL_PATH);
            default -> null;
        };
    }

    private void fillMissingGroundWithDefault() {
        if (map == null) return;
        for (int r = 0; r < mapHeight; r++) {
            for (int c = 0; c < mapWidth; c++) {
                if (map[GameConstants.GROUND_INDEX][r][c] == GameConstants.NOTHING) {
                    map[GameConstants.GROUND_INDEX][r][c] = GameConstants.GREEN; // default green grass
                }
            }
        }
    }

    public void handleReset() {
        // Clear the canvas and model
        renderInitialMap();
        for (int l = 0; l < 3; l++) {
            for (int r = 0; r < mapHeight; r++) {
                for (int c = 0; c < mapWidth; c++) {
                    map[l][r][c] = GameConstants.NOTHING;
                }
            }
        }
    }


    public void handleDone(javafx.event.ActionEvent event) {
        // Ensure all missing ground tiles are filled with a default green grass tile
        fillMissingGroundWithDefault();

        int playerCount = countPlayers();
        int boxCount = countBoxes();
        int goalCount = countGoals();

        if (playerCount != 1) {
            System.out.println("Please place exactly one Player. Current: " + playerCount);
            return;
        }
        if (boxCount != boxGoal) {
            System.out.println("Boxes must equal the required amount. Required: " + boxGoal + ", Placed: " + boxCount);
            return;
        }
        if (goalCount != boxGoal) {
            System.out.println("Goals must equal the required amount. Required: " + boxGoal + ", Placed: " + goalCount);
            return;
        }
        // Launch game panel with current map
        FxmlLoader.loadGamePanelWithBoard(GameConstants.GAME_PANEL_FXML, event, map);
    }

    public void handleQuit(ActionEvent event) {
        FxmlLoader.loadPanel(GameConstants.MAP_OPTIONS_FXML, event);
    }

}
