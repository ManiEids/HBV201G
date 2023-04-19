package is.skilaverkefni;

import is.skilaverkefni.annad.View;
import is.skilaverkefni.annad.ViewSwitcher;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RunApplication extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        ViewSwitcher.setScene(new Scene(new Pane())); // Set the scene in the ViewSwitcher
        ViewSwitcher.setAppInstance(this); // Pass the app instance to the ViewSwitcher
        ViewSwitcher.switchTo(View.START_MENU, null); // Switch to START_MENU using ViewSwitcher
        primaryStage.setScene(ViewSwitcher.getScene());
        primaryStage.show();
    }
}
