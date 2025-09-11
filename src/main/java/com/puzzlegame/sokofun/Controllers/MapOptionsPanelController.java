package com.puzzlegame.sokofun.Controllers;

import com.puzzlegame.sokofun.Logic.Abstract.FxmlLoader;
import com.puzzlegame.sokofun.Logic.Abstract.GameConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    }

    @FXML
    private void handleNext() {
        // TODO: Open map editor scene with the selected options
        System.out.println("Next button clicked");
        System.out.println("Width: " + widthSpinner.getValue());
        System.out.println("Height: " + heightSpinner.getValue());
        System.out.println("Box/Goal Pairs: " + boxGoalSpinner.getValue());
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        System.out.println("Cancel button clicked");
        FxmlLoader.loadPanel(GameConstants.MENU_PANEL_FXML, event);
    }
}
