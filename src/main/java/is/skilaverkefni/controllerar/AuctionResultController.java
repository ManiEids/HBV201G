package is.skilaverkefni.controllerar;

import is.skilaverkefni.ResultData;
import is.skilaverkefni.RunApplication;
import is.skilaverkefni.annad.BidMenu;
import is.skilaverkefni.annad.BiddingItem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuctionResultController {

    @FXML
    private Label usernameLabel;
    @FXML
    private ListView<BiddingItem> itemList;
    @FXML
    private Label totalAmountPaidLabel;

    private String username;
    private List<BiddingItem> items;

    private RunApplication appInstance;

    public void setAppInstance(RunApplication appInstance) {
        this.appInstance = appInstance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setItems(List<BiddingItem> items) {
        this.items = items;
    }

    public void setResultData(ResultData resultData) {
        this.username = resultData.getUsername();
        this.items = resultData.getBids();
        updateUI(username, calculateTotalAmount(items));
    }
    public int calculateTotalAmount(List<BiddingItem> items) {
        int totalAmount = 0;
        for (BiddingItem item : items) {
            totalAmount += item.getHighestBid();
        }
        return totalAmount;
    }
    public void updateUI(String username, int totalAmount) {
        if (usernameLabel != null) {
            usernameLabel.setText(username);
        }
        if (totalAmountPaidLabel != null) {
            totalAmountPaidLabel.setText("" + totalAmount);
        }
        if (itemList != null) {
            ObservableList<BiddingItem> items = itemList.getItems();
            items.clear();
            if (this.items != null) {
                items.addAll(this.items);
            }
            itemList.setItems(items);
            itemList.setCellFactory(param -> new ItemListCell());
        }
    }

    public List<BiddingItem> getWinningBids(List<BidMenu.Bid> bids) {
        List<BiddingItem> winningBids = new ArrayList<>();
        for (BidMenu.Bid bid : bids) {
            if (bid.getAmount() == bid.getItem().getHighestBid()) {
                winningBids.add(bid.getItem());
            }
        }
        return winningBids;
    }
}
