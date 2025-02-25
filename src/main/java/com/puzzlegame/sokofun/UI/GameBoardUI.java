package com.puzzlegame.sokofun.UI;

import com.puzzlegame.sokofun.Logic.Abstract.AssetLoader;
import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Logic.Abstract.ImagePathMap;
import com.puzzlegame.sokofun.Object.Move;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GameBoardUI {

    private int numRows;
    private int numCols;
    private double tileWidth;
    private double tileHeight;
    private GridPane boardGrid;
    private PlayerUI playerUI;

    public GameBoardUI(GridPane boardGrid,int numRows,int numCols) {
        AssetLoader.loadTiles();
        AssetLoader.loadPlayer();
        this.boardGrid = boardGrid;
        this.numRows = numRows;
        this.numCols = numCols;
        tileWidth = calculateTileWidth(numCols);
        tileHeight = calculateTileHeight(numRows);

        this.playerUI = new PlayerUI(boardGrid);
    }

    public void renderInitialBoard(int playerRow,int playerCol,int[][][] board) {

        configureGrid(numCols, numRows, tileWidth, tileHeight);
        boardGrid.getChildren().clear();

        renderLayer(board[GameConstants.GROUND_INDEX], tileWidth, tileHeight);
        renderLayer(board[GameConstants.BLOCK_INDEX], tileWidth, tileHeight);
        renderLayer(board[GameConstants.OBJECT_INDEX], tileWidth, tileHeight);
        playerUI.renderInitialPlayer(playerRow,playerCol,tileWidth,tileHeight);
    }

    public void renderBoard(Move move) {
        // render new board based on [][][] board
        // render only box and objects
        playerUI.renderPlayer(move,tileWidth,tileHeight);
        // optimize render by checking previous board (in future)
    }




    private double calculateTileWidth(int numCols) {
        return (double) GameConstants.BOARD_WIDTH / numCols;
    }


    private double calculateTileHeight(int numRows) {
        return (double) GameConstants.BOARD_HEIGHT / numRows;
    }

    private void configureGrid(int numCols, int numRows, double tileWidth, double tileHeight) {
        boardGrid.getColumnConstraints().clear();
        boardGrid.getRowConstraints().clear();

        for (int col = 0; col < numCols; col++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(tileWidth);
            boardGrid.getColumnConstraints().add(colConstraints);
        }

        for (int row = 0; row < numRows; row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(tileHeight);
            boardGrid.getRowConstraints().add(rowConstraints);
        }
    }
    // Renders a given layer (ground, block, or object)
    private void renderLayer(int[][] layer, double tileWidth, double tileHeight) {
        for (int row = 0; row < layer.length; row++) {
            for (int col = 0; col < layer[row].length; col++) {
                if (willRender(layer[row][col])) {
                    Image block = ImagePathMap.getImage(layer[row][col]);
                    ImageView blockView = new ImageView(block);
                    blockView.setFitWidth(tileWidth);
                    blockView.setFitHeight(tileHeight);
                    boardGrid.add(blockView, col, row);
                }
            }
        }
    }
    private boolean willRender(int block) {
        return block != GameConstants.NOTHING && block != GameConstants.PLAYER;
    }

}
