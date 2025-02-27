package com.puzzlegame.sokofun.Logic.Abstract;

import com.puzzlegame.sokofun.App;
import com.puzzlegame.sokofun.Controllers.GamePanelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class FxmlLoader {

    public static void loadGamePanel(String fxmlFile, ActionEvent event,int level) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlFile));
            Parent root = loader.load();


            GamePanelController controller = loader.getController();
            if (controller != null) {
                controller.initializeGame(level);
            }

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("SokoFun");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
