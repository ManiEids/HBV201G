package is.skilaverkefni.controllerar;

import is.skilaverkefni.RunApplication;
import is.skilaverkefni.annad.View;
import is.skilaverkefni.annad.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class StartMenuController {
    @FXML
    private Button startBiddingButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField auctionTime;

    private RunApplication appInstance;

    public void setAppInstance(RunApplication appInstance) {
        this.appInstance = appInstance;
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleStartBiddingButtonAction() {
        String username = getUsername();
        if (username.isEmpty()) {
            showAlert("Error", "Please enter a valid username.");
            return;
        }

        String auctionTimeString = auctionTime.getText();
        int auctionTime = 30;
        if (!auctionTimeString.isEmpty()) {
            try {
                auctionTime = Integer.parseInt(auctionTimeString);
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid integer for auction time.");
                return;
            }
        }

        ViewSwitcher.switchTo(View.BIDDING_MENU, new Pair<>(username, auctionTime));
    }

}