package com.puzzlegame.sokofun.UI;

import com.puzzlegame.sokofun.Logic.Abstract.AssetLoader;
import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Logic.Abstract.ImagePathMap;
import com.puzzlegame.sokofun.Object.Move;
import javafx.scene.Node;
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
        loadAssets();
        this.boardGrid = boardGrid;
        this.numRows = numRows;
        this.numCols = numCols;
        tileWidth = calculateTileWidth(numCols);
        tileHeight = calculateTileHeight(numRows);

        this.playerUI = new PlayerUI(boardGrid);
    }

    public void restartUI(int playerRow,int playerCol,int[][][] board) {
        this.playerUI.setBoardGrid(boardGrid);
        renderInitialBoard(playerRow,playerCol,board);
    }

    public void renderInitialBoard(int playerRow,int playerCol,int[][][] board) {

        configureGrid();
        boardGrid.getChildren().clear();

        renderLayer(board[GameConstants.GROUND_INDEX]);
        renderLayer(board[GameConstants.BLOCK_INDEX]);
        renderLayer(board[GameConstants.OBJECT_INDEX]);
        renderBox(board);
        playerUI.renderInitialPlayer(playerRow,playerCol,tileWidth,tileHeight);
    }

    public void renderBoard(int[][][] board,Move move) {
        // the board is updated
        moveBox(board,move);
        playerUI.renderPlayer(move,tileWidth,tileHeight);
    }

    private void moveBox(int[][][] board,Move move) {

        int newRow = move.getNewRow();
        int newCol = move.getNewCol();


        if(move.getIsPushed()) {
            // it must be a box in this position
            ImageView boxView = (ImageView) getChildAt(GameConstants.BOX,newRow,newCol);

            boardGrid.getChildren().remove(boxView);
            int nextRow = move.getPushedRow();
            int nextCol = move.getPushedCol();
            int goalType = board[GameConstants.OBJECT_INDEX][nextRow][nextCol];


            boxView = getValidBox(goalType,nextRow,nextCol);

            boardGrid.add(boxView,nextRow,nextCol);
            GridPane.setRowIndex(boxView, nextRow);
            GridPane.setColumnIndex(boxView, nextCol);
        }
    }

    private ImageView getValidBox(int goalType,int row,int col) {
        Image boxImage = switch (goalType) {
            case GameConstants.STONE_GOAL -> AssetLoader.getImage(GameConstants.STONE_BOX_PATH);
            case GameConstants.BLUE_GOAL -> AssetLoader.getImage(GameConstants.BLUE_BOX_PATH);
            case GameConstants.GREEN_GOAL -> AssetLoader.getImage(GameConstants.GREEN_BOX_PATH);
            case GameConstants.RED_GOAL -> AssetLoader.getImage(GameConstants.RED_BOX_PATH);
            default -> AssetLoader.getImage(GameConstants.WOOD_BOX_PATH);
        };
        ImageView boxView = getTileView(boxImage,GameConstants.BOX,row,col);

        return boxView;
    }

    private Node getChildAt(int type, int row, int col) {
        for (Node child : boardGrid.getChildren()) {
            if(GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                String id = child.getId();
                if (id != null) {
                    String[] parts = id.split("_");
                    int childType = Integer.parseInt(parts[0]);

                    if (childType == type) {
                        return child;
                    }
                }
            }
        }

        return null;
    }


    // Render except box and player
    private void renderLayer(int[][] layer) {
        for (int row = 0; row < layer.length; row++) {
            for (int col = 0; col < layer[row].length; col++) {
                if (willRender(layer[row][col])) {
                    Image tileImage = ImagePathMap.getImage(layer[row][col]);
                    ImageView tileView = getTileView(tileImage,layer[row][col],row,col);
                    boardGrid.add(tileView, col, row);
                }
            }
        }
    }

    private void renderBox(int[][][] board) {
        int[][] blockLayer = board[GameConstants.BLOCK_INDEX];
        for (int row = 0; row < blockLayer.length; row++) {
            for (int col = 0; col < blockLayer[row].length; col++) {
                if(blockLayer[row][col] == GameConstants.BOX) {
                    int goalType = board[GameConstants.OBJECT_INDEX][row][col];
                    ImageView boxView = getValidBox(goalType,row,col);
                    boardGrid.add(boxView,col,row);
                }
            }
        }
    }


    private void configureGrid() {

        if (boardGrid.getColumnConstraints().isEmpty()) {
            for (int col = 0; col < numCols; col++) {
                ColumnConstraints colConstraints = new ColumnConstraints();
                colConstraints.setMinWidth(tileWidth);
                boardGrid.getColumnConstraints().add(colConstraints);
            }
        }


        if (boardGrid.getRowConstraints().isEmpty()) {
            for (int row = 0; row < numRows; row++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setMinHeight(tileHeight);
                boardGrid.getRowConstraints().add(rowConstraints);
            }
        }
    }



    private ImageView getTileView(Image tileImage,int type, int row, int col) {
        ImageView tileView = new ImageView(tileImage);
        tileView.setFitWidth(tileWidth);
        tileView.setFitHeight(tileHeight);
        tileView.setId(type + "_" + row + "_" + col);
        return tileView;
    }

    private boolean willRender(int block) {
        return block != GameConstants.NOTHING && block != GameConstants.PLAYER
                && block != GameConstants.BOX;
    }

    private void loadAssets() {
        AssetLoader.loadPlayer();
        AssetLoader.loadTiles();
    }

    private double calculateTileWidth(int numCols) {
        return (double) GameConstants.BOARD_WIDTH / numCols;
    }


    private double calculateTileHeight(int numRows) {
        return (double) GameConstants.BOARD_HEIGHT / numRows;
    }

}
