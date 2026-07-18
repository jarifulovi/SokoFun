package com.puzzlegame.sokofun.Controllers;

import com.puzzlegame.sokofun.Logic.Abstract.FxmlLoader;
import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class MapOptionsPanelController {
    @FXML
    private Spinner<Integer> widthSpinner;

    @FXML
    private Spinner<Integer> heightSpinner;

    @FXML
    private Spinner<Integer> boxGoalSpinner;


    @FXML
    private void initialize() {
        // Set default and min/max values for spinners
        widthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 30, 10));
        heightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 20, 10));
        boxGoalSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3));

        widthSpinner.setEditable(false);
        heightSpinner.setEditable(false);
        boxGoalSpinner.setEditable(false);

    }

    @FXML
    private void handleNext(ActionEvent event) {
        System.out.println("Next button clicked");
        FxmlLoader.loadGameEditorPanel(GameConstants.GAME_EDITOR_FXML, event,
                widthSpinner.getValue(), heightSpinner.getValue(), boxGoalSpinner.getValue());
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        System.out.println("Cancel button clicked");
        FxmlLoader.loadPanel(GameConstants.MENU_PANEL_FXML, event);
    }
}
