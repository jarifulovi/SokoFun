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
        // Retrieve the board from file
        // Initiate fields
        int playerRow = 1;
        int playerCol = 1;
        int totalRow = 5;
        int totalCol = 5;
        boardLogic = new BoardLogic();
        player = new Player(playerRow,playerCol);
        gameBoardUI = new GameBoardUI(boardGrid,totalRow,totalCol);
        gameBoardUI.renderInitialBoard(playerRow,playerCol, boardLogic.getBoard());


        canUpdate = true;
        // Set handler
        boardGrid.setOnKeyPressed(this::update);
        boardGrid.setFocusTraversable(true);
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