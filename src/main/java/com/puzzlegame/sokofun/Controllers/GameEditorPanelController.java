package com.puzzlegame.sokofun.Controllers;

import com.puzzlegame.sokofun.Logic.Abstract.AssetLoader;
import com.puzzlegame.sokofun.Logic.Abstract.FxmlLoader;
import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
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
        this.map = new int[3][this.mapWidth][this.mapHeight];
        renderInitialMap();
        setupMouseClickHandler();
    }

    public void handleGroundTile(ActionEvent event) {
        switch (((Button)event.getSource()).getId()) {
            case "floorTileButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.TILE_FLOOR_PATH);
            case "sandTileButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.TILE_SAND_PATH);
            case "greenTileButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.TILE_GREEN_PATH);
        }
    }

    public void handleBlockTile(ActionEvent event) {
        switch (((Button)event.getSource()).getId()) {
            case "stoneTileButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.STONE_PATH);
            case "stoneBlockTileButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.STONE_BLOCK_PATH);
            case "woodTileButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.WOOD_PATH);
            case "woodBlockTileButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.WOOD_BLOCK_PATH);
            case "redTileButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.RED_PATH);
            case "redBlockTileButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.RED_BLOCK_PATH);
        }
    }

    public void handleGoalTile(ActionEvent event) {
        switch (((Button)event.getSource()).getId()) {
            case "woodGoalButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.WOOD_GOAL_PATH);
            case "greenGoalButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.GREEN_GOAL_PATH);
            case "stoneGoalButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.STONE_GOAL_PATH);
            case "blueGoalButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.BLUE_GOAL_PATH);
            case "redGoalButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.RED_GOAL_PATH);
            case "coinButton" -> this.selectedTileImage = AssetLoader.getImage(GameConstants.COIN_GOAL_PATH);
        }
    }

    public void handleAddPlayer() {
        this.selectedTileImage = AssetLoader.getImage(GameConstants.PLAYER_DOWN);
    }

    public void handleAddBox() {
        this.selectedTileImage = AssetLoader.getImage(GameConstants.WOOD_BOX_PATH);
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

            var gc = mapCanvas.getGraphicsContext2D();
            gc.drawImage(this.selectedTileImage, col * tileSize, row * tileSize, tileSize, tileSize);

            // redraw grid border on top
            gc.setStroke(javafx.scene.paint.Color.GRAY);
            gc.strokeRect(col * tileSize, row * tileSize, tileSize, tileSize);

            // store in tile map for logic/saving map
            // tileMap[col][row] = selectedTileImage;
        });
    }



    private double getTileSize(int mapWidth, int mapHeight) {
        double tileWidth = mapCanvas.getWidth() / mapWidth;
        double tileHeight = mapCanvas.getHeight() / mapHeight;
        return Math.min(tileWidth, tileHeight);
    }


    public void handleReset() {
        System.out.println("Reset button pressed");
    }


    public void handleDone() {
        System.out.println("Done button pressed");
    }

    public void handleQuit(ActionEvent event) {
        FxmlLoader.loadPanel(GameConstants.MAP_OPTIONS_FXML, event);
    }

}
