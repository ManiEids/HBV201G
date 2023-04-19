package is.skilaverkefni.annad;

import is.skilaverkefni.controllerar.BidMenuController;
import is.skilaverkefni.controllerar.BiddingItemCellController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class BidMenuInitializer {
    public static void initialize(BidMenuController controller, int countdownTime, String username) {
        AuctionItemProvider auctionItemProvider = new AuctionItemProvider();
        controller.itemListView.setItems(auctionItemProvider.getAvailableItems());
        controller.itemListView.setOnMouseClicked(controller::handleItemClick);
        controller.setUsername(username);
        controller.initializeUsernameLabel(); // Add this line

        // Set the custom cell factory for the itemListView
        controller.itemListView.setCellFactory(param -> new ListCell<BiddingItem>() {
            @Override
            protected void updateItem(BiddingItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/is/skilaverkefni/bidding_item_cell.fxml"));
                        Node cellContent = loader.load();
                        BiddingItemCellController cellController = loader.getController();
                        cellController.setBiddingItem(item);
                        setGraphic(cellContent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        controller.countdownTime = countdownTime; // Set the countdown time to the value passed in
        controller.timeLabel.setText(Integer.toString(controller.countdownTime)); // Set the initial value of the timeLabel
    }
}
