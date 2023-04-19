package is.skilaverkefni.controllerar;
import is.skilaverkefni.annad.BidMenu.Bid;
import is.skilaverkefni.annad.BiddingItem;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlaceBidDialogController {
    @FXML
    private Label itemNameLabel;
    @FXML
    private Label currentPriceLabel;
    @FXML
    private TextField yourBidTextField;

    private BiddingItem selectedItem;
    private BidMenuController bidMenuControllerInstance;
    private int currentPrice;

    public PlaceBidDialogController(BidMenuController bidMenuControllerInstance) {
        this.bidMenuControllerInstance = bidMenuControllerInstance;
    }

    public void setSelectedItem(BiddingItem item) {
        selectedItem = item;
        itemNameLabel.setText(item.getName());
        currentPrice = item.getHighestBid() > item.getStartingPrice() ? item.getHighestBid() : item.getStartingPrice();
        if (item.getHighestBid() > item.getStartingPrice()) {
            setCurrentPrice(currentPrice);
        } else {
            currentPriceLabel.setText(String.format("Upphafsverð: %d kr.", item.getStartingPrice()));
        }
    }

    public void handlePlaceBidButtonAction() {
        try {
            int yourBid = Integer.parseInt(yourBidTextField.getText());
            if (yourBid > currentPrice) {
                boolean bidExists = false;
                for (int i = 0; i < BidMenuController.staticBids.size(); i++) {
                    Bid existingBid = BidMenuController.staticBids.get(i);
                    if (existingBid.getItem().getName().equals(selectedItem.getName())) {
                        Bid updatedBid = new Bid(selectedItem, yourBid); // Create a new Bid instance with the updated amount
                        BidMenuController.staticBids.set(i, updatedBid); // Replace the old Bid with the updated one
                        bidExists = true;
                        break;
                    }
                }

                if (!bidExists) {
                    bidMenuControllerInstance.addBid(selectedItem, yourBid); // Place the new bid if it doesn't already exist
                }

                selectedItem.setHighestBid(yourBid); // Update the highest bid of the selected item
                setCurrentPrice(yourBid); // Update the current price label
                bidMenuControllerInstance.refreshBidListView(); // Refresh the bidListView
                bidMenuControllerInstance.refreshSelectedItem(); // Add this line to refresh the selected item
                closeWindow();
            } else {
                // Show an error message or warning about the bid being too low
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ógild upphæð");
                alert.setHeaderText("Of lágt boð");
                alert.setContentText("Boð verður að vera hærra en verð á hlut");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            // Handle invalid input
        }
    }
    public void handleCancelButtonAction() {
        closeWindow();
    }

    public void setCurrentPrice(int price) {
        currentPrice = price;
        currentPriceLabel.setText(String.format("Nýtt hæsta boð:*** %d kr ***", currentPrice));
    }

    private void closeWindow() {
        Stage stage = (Stage) yourBidTextField.getScene().getWindow();
        stage.close();
    }
}
