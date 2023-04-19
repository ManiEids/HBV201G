module is.skilaverkefni {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens is.skilaverkefni to javafx.fxml;
    exports is.skilaverkefni;
    exports is.skilaverkefni.controllerar;
    opens is.skilaverkefni.controllerar to javafx.fxml;
    exports is.skilaverkefni.annad;
    opens is.skilaverkefni.annad to javafx.fxml;
}