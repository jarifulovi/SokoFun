package com.puzzlegame.sokofun.Controllers;

import com.puzzlegame.sokofun.Logic.Abstract.FxmlLoader;
import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class MenuPanelController {

    // ========================
    // Event Handlers
    // ========================

    @FXML
    private void handleStartGame(ActionEvent event) {
        // TODO: Will implement level selection scene letter
        System.out.println("Start Game button clicked");
        FxmlLoader.loadGamePanel(GameConstants.GAME_PANEL_FXML, event, 1);
    }

    @FXML
    private void handleMapEditor() {
        // TODO: Open map editor scene
        System.out.println("Map Editor button clicked");
    }

    @FXML
    private void handleMapGenerator() {
        // TODO: Generate random map
        System.out.println("Map Generator button clicked");
    }

    @FXML
    private void handleSolverStats() {
        // TODO: Show solver statistics
        System.out.println("Solver Stats button clicked");
    }

    @FXML
    private void handleInstructions() {
        // TODO: Show instructions screen
        System.out.println("Instructions button clicked");
    }

    @FXML
    private void handleOptions() {
        // TODO: Open options/settings menu
        System.out.println("Options button clicked");
    }

    @FXML
    private void handleExit() {
        // TODO: Exit the application
        System.out.println("Exit button clicked");
        System.exit(0);
    }
}
