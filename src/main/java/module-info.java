module com.puzzlegame.sokofun {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.puzzlegame.sokofun to javafx.fxml;
    exports com.puzzlegame.sokofun;
}