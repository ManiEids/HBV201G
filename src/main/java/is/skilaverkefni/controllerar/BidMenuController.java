package is.skilaverkefni.controllerar;

import is.skilaverkefni.ResultData;
import is.skilaverkefni.RunApplication;
import is.skilaverkefni.annad.*;
import is.skilaverkefni.annad.BidMenu.Bid;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BidMenuController {

    public static List<Bid> staticBids = new ArrayList<>();
    public boolean isBiddingStarted = false;
    @FXML
    public ListView<BiddingItem> itemListView;
    @FXML
    public Label timeLabel;
    public String username;
    public int countdownTime;
    private BidMenu menu = new BidMenu(); // Initialize menu here
    private RunApplication appInstance;
    @FXML
    private BorderPane root;
    public void initializeUsernameLabel() {
        usernameLabel.setText(username);
    }
    @FXML
    private Label usernameLabel;
    private Timer countdownTimer;
    @FXML
    private TextField auctionTime;
    @FXML
    private TextField bidAmountTextField;
    @FXML
    private ListView<Bid> bidListView;
    // Default constructor added
    public BidMenuController() {
    }
    public BidMenuController(RunApplication appInstance) {
        this.appInstance = appInstance;
    }
    public List<Bid> getStaticBids() {
        return staticBids;
    }
    public static void addBid(BiddingItem item, int amount) {
        Bid newBid = new Bid(item, amount);
        staticBids.add(newBid);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAppInstance(RunApplication appInstance) {
        this.appInstance = appInstance;
    }

    public void refreshBidListView() {
        bidListView.setItems(FXCollections.observableArrayList(staticBids));
    }

    public void setUsernameLabel(String username) {
        usernameLabel.setText(username);
    }

    public void initialize(Pair<String, Integer> data) {
        BidMenuInitializer.initialize(this, data.getValue(), getUsername());
    }

    public void startBidding(Pair<String, Integer> params) {
        isBiddingStarted = true;

        if (params != null) {
            countdownTime = params.getValue();
        }

        // Start the countdown timer
        countdownTimer = new Timer();
        countdownTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    countdownTime--;
                    timeLabel.setText(Integer.toString(countdownTime));
                    if (countdownTime <= 0) {
                        countdownTimer.cancel();
                        onTimeUp();
                    }
                });
            }
        }, 1000, 1000);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public int calculateTotalAmount(List<Bid> bids, String itemName) {
        int totalAmount = 0;
        for (Bid bid : bids) {
            if (bid.getItem().getName().equals(itemName)) {
                totalAmount += bid.getAmount();
            }
        }
        return totalAmount;
    }

    public void refreshSelectedItem() {
        BiddingItem selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemListView.refresh(); // Refresh the view
        }
    }


    private void onTimeUp() {
        Platform.runLater(() -> {
            try {
                List<Bid> bids = bidListView.getItems();
                BiddingItem selectedItem = itemListView.getSelectionModel().getSelectedItem();
                String itemName = selectedItem != null ? selectedItem.getName() : "";

                ResultData resultData = new ResultData(itemName, usernameLabel.getText(), bids);

                // Log the information being used
                Logger logger = Logger.getLogger("auction");
                logger.log(Level.INFO, "ResultData: " + resultData.toString());

                // Use the ViewSwitcher to switch to the RESULT_MENU and pass the result data
                ViewSwitcher.switchTo(View.RESULT_MENU, resultData);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    private void handlePlaceBidButtonAction() {
        BiddingItem selectedItem = itemListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            showPlaceBidDialog(selectedItem);
        }
    }


    private void showPlaceBidDialog(BiddingItem selectedItem) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(c -> new PlaceBidDialogController(this));
            fxmlLoader.setLocation(getClass().getResource("/is/skilaverkefni/placeBidDialog.fxml"));
            Parent root = fxmlLoader.load();
            PlaceBidDialogController controller = fxmlLoader.getController();
            controller.setSelectedItem(selectedItem);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Place Bid");
            stage.setScene(new Scene(root));

            stage.showAndWait();
            bidListView.setItems(FXCollections.observableArrayList(staticBids));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleItemClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            BiddingItem selectedItem = itemListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                showPlaceBidDialog(selectedItem);
            }
        }
    }
}