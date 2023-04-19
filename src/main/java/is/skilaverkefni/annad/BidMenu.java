package is.skilaverkefni.annad;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class BidMenu {

    protected ObservableList<BiddingItem> items = FXCollections.observableArrayList();
    private List<Bid> bids = new ArrayList<>();
    private AuctionItemProvider auctionItemProvider;
    private Runnable onTimeUpCallback;
    private IntegerProperty timeRemaining = new SimpleIntegerProperty(0);
    private ObservableList<Bid> bidList = FXCollections.observableArrayList();


    public BidMenu() {
        auctionItemProvider = new AuctionItemProvider();
        items.addAll(auctionItemProvider.getAvailableItems());
    }

    public void setOnTimeUpCallback(Runnable callback) {
        this.onTimeUpCallback = callback;
    }

    public void startBidding(int durationSeconds) {
        // Reset the list of bids and start the timer
        bids.clear();
        timeRemaining.set(durationSeconds);

        // Create a new Timeline for the countdown
        Timeline countdownTimeline = new Timeline();
        countdownTimeline.setCycleCount(Timeline.INDEFINITE);

        // Create a KeyFrame that updates the timeRemaining property every second
        KeyFrame countdownKeyFrame = new KeyFrame(Duration.seconds(1), event -> {
            timeRemaining.set(timeRemaining.get() - 1);

            // Stop the countdown when timeRemaining reaches 0
            if (timeRemaining.get() <= 0) {
                countdownTimeline.stop();
                if (onTimeUpCallback != null) {
                    onTimeUpCallback.run();
                }
            }
        });

        // Add the KeyFrame to the Timeline and start the countdown
        countdownTimeline.getKeyFrames().add(countdownKeyFrame);
        countdownTimeline.play();
    }

    public void placeBid(BiddingItem item, int amount) {
        // Add the bid to the list
        bids.add(new Bid(item, amount));
        if (amount > item.getAskingPrice()) {
            item.setAskingPrice(amount);
        }
        item.setHighestBid(amount);
    }

    public ObservableList<BiddingItem> getItems() {
        return items;
    }


    public IntegerProperty getTimeRemaining() {
        return timeRemaining;
    }

    public static class Bid {
        private final BiddingItem item;
        private final int amount;

        public Bid(BiddingItem item, int amount) {
            this.item = item;
            this.amount = amount;
        }

        public BiddingItem getItem() {
            return item;
        }

        public int getAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return item.getName() + " - " + amount;
        }
    }
}