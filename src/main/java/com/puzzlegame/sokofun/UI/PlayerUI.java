package com.puzzlegame.sokofun.UI;

import com.puzzlegame.sokofun.Logic.Abstract.AssetLoader;
import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Object.Move;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PlayerUI {

    private ImageView playerView;
    private GridPane boardGrid;

    public PlayerUI(GridPane boardGrid) {
        this.boardGrid = boardGrid;
    }

    public void renderInitialPlayer(int playerRow, int playerCol, double width, double height) {
        playerView = new ImageView(getPlayerImage(GameConstants.DOWN));
        playerView.setFitWidth(width);
        playerView.setFitHeight(height);
        boardGrid.add(playerView, playerCol, playerRow);
    }


    public void renderPlayer(Move move, double width, double height) {
        if (playerView == null) {
            playerView = new ImageView();
            boardGrid.add(playerView, move.getNewCol(), move.getNewRow());
        } else {
            boardGrid.getChildren().remove(playerView);
            boardGrid.add(playerView, move.getNewCol(), move.getNewRow());
        }

        playerView.setImage(getPlayerImage(move.getDirection()));
        playerView.setFitWidth(width);
        playerView.setFitHeight(height);
    }



    private Image getPlayerImage(int direction) {
        return switch (direction) {
            case GameConstants.UP -> AssetLoader.getImage(GameConstants.PLAYER_UP);
            case GameConstants.DOWN -> AssetLoader.getImage(GameConstants.PLAYER_DOWN);
            case GameConstants.LEFT -> AssetLoader.getImage(GameConstants.PLAYER_LEFT);
            case GameConstants.RIGHT -> AssetLoader.getImage(GameConstants.PLAYER_RIGHT);
            default -> null;
        };
    }

}
