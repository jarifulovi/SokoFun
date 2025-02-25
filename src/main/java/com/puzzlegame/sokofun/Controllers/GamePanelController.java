package com.puzzlegame.sokofun.Controllers;

import com.puzzlegame.sokofun.Logic.Abstract.AssetLoader;
import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Logic.Abstract.ImagePathMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.puzzlegame.sokofun.Logic.Abstract.GameConstants.*;

public class GamePanelController {
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private AnchorPane boardAnchor;
    @FXML
    private AnchorPane stateAnchor;
    @FXML
    private GridPane boardGrid;
    @FXML
    private Button menuButton;
    @FXML
    private Button restartButton;

    // Controller fields
    private int[][][] board;

    @FXML
    public void initialize() {
        System.out.println("game panel started");
        // Retrieve the board from file

        createBoard(5,5);
        // Dynamically render the board ( in different method )
    }
    @FXML
    private void onMenuButton() {
        System.out.println("go back menu");
    }

    @FXML
    private void onRestartButton() {
        System.out.println("restart game");
    }

    private static final int[][] groundLayer = {
            {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR},
            {FLOOR, SAND, SAND, SAND, FLOOR},
            {FLOOR, SAND, FLOOR, SAND, FLOOR},
            {FLOOR, SAND, SAND, SAND, FLOOR},
            {FLOOR, FLOOR, FLOOR, FLOOR, FLOOR}
    };
    // Block layer (represents edges or walls)
    private static final int[][] blockLayer = {
            {STONE, STONE, STONE, STONE, STONE},
            {STONE, NOTHING, NOTHING, STONE, STONE},
            {STONE, NOTHING, NOTHING, NOTHING, NOTHING},
            {STONE, NOTHING, NOTHING, STONE, STONE},
            {STONE, STONE, STONE, STONE, STONE}
    };

    // Object layer (used for objects like crates or player)
    private static final int[][] objectLayer = {
            {NOTHING, NOTHING, NOTHING, NOTHING, NOTHING},
            {NOTHING, PLAYER, NOTHING, NOTHING, NOTHING},
            {NOTHING, NOTHING, BOX, NOTHING, NOTHING},
            {NOTHING, NOTHING, NOTHING, NOTHING, NOTHING},
            {NOTHING, NOTHING, NOTHING, NOTHING, NOTHING}
    };

    private void createBoard(int numRows, int numCols) {
        AssetLoader.loadPlayer();
        AssetLoader.loadTiles();

        double boardWidth = GameConstants.BOARD_WIDTH;
        double boardHeight = GameConstants.BOARD_HEIGHT;

        // Calculate the size of each square tile to fit within the grid
        double tileWidth = boardWidth / numCols;
        double tileHeight = boardHeight / numRows;




        boardGrid.getColumnConstraints().clear();
        boardGrid.getRowConstraints().clear();
        // Add columns
        for (int col = 0; col < numCols; col++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(tileWidth);
            boardGrid.getColumnConstraints().add(colConstraints);
        }

        // Add rows
        for (int row = 0; row < numRows; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(tileHeight);
            boardGrid.getRowConstraints().add(rowConstraints);
        }

        boardGrid.getChildren().clear();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                // Render ground layer
                Image block = ImagePathMap.getImage(groundLayer[row][col]);
                ImageView blockView = new ImageView(block);
                blockView.setFitWidth(tileWidth);
                blockView.setFitHeight(tileHeight);
                // Render block layer
                Image block2 = ImagePathMap.getImage(blockLayer[row][col]);
                ImageView blockView2 = new ImageView(block2);
                blockView2.setFitWidth(tileWidth);
                blockView2.setFitHeight(tileHeight);
                // Render env layer
                Image block3 = ImagePathMap.getImage(objectLayer[row][col]);
                ImageView blockView3 = new ImageView(block3);
                blockView3.setFitWidth(tileWidth);
                blockView3.setFitHeight(tileHeight);
//                Rectangle blockView = new Rectangle(tileWidth,tileHeight);
//                blockView.setFill(Color.rgb(54,65,32));
                boardGrid.add(blockView,col,row);
                if(blockLayer[row][col] != NOTHING)
                    boardGrid.add(blockView2,col,row);
                if(objectLayer[row][col] != NOTHING)
                    boardGrid.add(blockView3,col,row);
                // Add the element
            }
        }
    }

}