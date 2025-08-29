module com.puzzlegame.sokofun {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.puzzlegame.sokofun to javafx.fxml;
    exports com.puzzlegame.sokofun;
    exports com.puzzlegame.sokofun.Controllers;
    opens com.puzzlegame.sokofun.Controllers to javafx.fxml;
}