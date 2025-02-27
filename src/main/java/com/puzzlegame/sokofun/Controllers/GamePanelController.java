package com.puzzlegame.sokofun.Controllers;


import com.puzzlegame.sokofun.Logic.Abstract.FxmlLoader;
import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import com.puzzlegame.sokofun.Logic.GameLogic.BoardLogic;
import com.puzzlegame.sokofun.Logic.GameLogic.Player;
import com.puzzlegame.sokofun.Object.Move;
import com.puzzlegame.sokofun.UI.GameBoardUI;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
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
    @FXML
    private AnchorPane gameOverAnchor;
    @FXML
    private Button playAgainButton;
    @FXML
    private Button nextLevelButton;

    // Controller fields
    private final int updateDelay = 100;
    private Player player;
    private GameBoardUI gameBoardUI;
    private BoardLogic boardLogic;
    private boolean canUpdate;
    private boolean isGameOver;
    private int level;

    public void initializeGame(int level) {
        this.level = level;
        canUpdate = true;
        isGameOver = false;
        boardLogic = new BoardLogic(level);
        int[] playerPosition = boardLogic.getPlayerPosition();

        int totalRow = boardLogic.getTotalRows();
        int totalCol = boardLogic.getTotalCols();

        player = new Player(playerPosition[0],playerPosition[1]);
        gameBoardUI = new GameBoardUI(boardGrid,totalRow,totalCol);
        resetGameOverUI();

        // Render the board
        gameBoardUI.renderInitialBoard(playerPosition[0],playerPosition[1], boardLogic.getBoard());

    }
    @FXML
    public void initialize() {
        System.out.println("game panel started");
        resetAndSetFocus();
        // Set handler
        boardGrid.setOnKeyPressed(this::update);

    }

    // Update
    private void update(KeyEvent event) {

        if(!canUpdate || isGameOver) return;

        Move move = player.getMove(event);
        boolean isValidMove = boardLogic.isValidMove(move);
        if(!isValidMove) return;

        boardLogic.updateBoard(move);
        player.updatePosition(move);
        gameBoardUI.renderBoard(boardLogic.getBoard(),move);

        isGameOver = boardLogic.isLevelComplete();

        if(isGameOver)
            setGameOverUI();

        applyUpdateDelay();

    }

    private void restartGame() {
        resetAndSetFocus();
        isGameOver = false;
        boardLogic.resetBoard();
        int[] playerPosition = boardLogic.getPlayerPosition();


        player = new Player(playerPosition[0],playerPosition[1]);
        gameBoardUI.restartUI(playerPosition[0],playerPosition[1], boardLogic.getBoard());
        resetGameOverUI();
    }



    @FXML
    private void onMenuButton() {
        System.out.println("go back menu");
    }

    @FXML
    private void onRestartButton() {
        System.out.println("restart game");
        restartGame();
    }

    @FXML
    private void onNextLevelButton(ActionEvent event) {
        if(level < GameConstants.TOTAL_LEVELS) {
            FxmlLoader.loadGamePanel(GameConstants.GAME_PANEL_FXML,event,level+1);
        } else {
            System.out.println("Reacted total level");
        }

    }

    private void applyUpdateDelay() {
        canUpdate = false;
        PauseTransition pause = new PauseTransition(Duration.millis(updateDelay));
        pause.setOnFinished(e -> canUpdate = true);
        pause.play();
    }

    private void setGameOverUI() {
        gameOverAnchor.setVisible(true);
        gameOverAnchor.setMouseTransparent(false);
    }

    private void resetGameOverUI() {
        gameOverAnchor.setVisible(false);
        gameOverAnchor.setMouseTransparent(true);
    }

    private void resetAndSetFocus() {
        menuButton.setFocusTraversable(false);
        restartButton.setFocusTraversable(false);
        boardGrid.setFocusTraversable(true);
    }
}