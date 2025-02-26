package com.puzzlegame.sokofun.Controllers;


import com.puzzlegame.sokofun.Logic.GameLogic.BoardLogic;
import com.puzzlegame.sokofun.Logic.GameLogic.Player;
import com.puzzlegame.sokofun.Object.Move;
import com.puzzlegame.sokofun.UI.GameBoardUI;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

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
    private Player player;
    private GameBoardUI gameBoardUI;
    private BoardLogic boardLogic;
    private boolean canUpdate;
    private final int updateDelay = 100;

    @FXML
    public void initialize() {
        System.out.println("game panel started");
        // Reset elements
        menuButton.setFocusTraversable(false);
        restartButton.setFocusTraversable(false);
        // Set handler
        boardGrid.setOnKeyPressed(this::update);
        boardGrid.setFocusTraversable(true);
        // Initiate fields
        int level = 1;
        canUpdate = true;
        boardLogic = new BoardLogic(level);
        int[] playerPosition = boardLogic.getPlayerPosition();

        int totalRow = boardLogic.getTotalRows();
        int totalCol = boardLogic.getTotalCols();

        player = new Player(playerPosition[0],playerPosition[1]);
        gameBoardUI = new GameBoardUI(boardGrid,totalRow,totalCol);
        gameBoardUI.renderInitialBoard(playerPosition[0],playerPosition[1], boardLogic.getBoard());

    }

    // Update
    private void update(KeyEvent event) {

        if(!canUpdate) return;

        Move move = player.getMove(event);
        boolean isValidMove = boardLogic.isValidMove(move);
        if(!isValidMove) return;

        boardLogic.updateBoard(move);
        player.updatePosition(move);
        gameBoardUI.renderBoard(boardLogic.getBoard(),move);
        boolean isGameOver = boardLogic.isLevelComplete();
        System.out.println(isGameOver);

        applyUpdateDelay();

    }



    @FXML
    private void onMenuButton() {
        System.out.println("go back menu");
    }

    @FXML
    private void onRestartButton() {
        System.out.println("restart game");
        initialize();
    }



    private void applyUpdateDelay() {
        canUpdate = false;
        PauseTransition pause = new PauseTransition(Duration.millis(updateDelay));
        pause.setOnFinished(e -> canUpdate = true);
        pause.play();
    }


}