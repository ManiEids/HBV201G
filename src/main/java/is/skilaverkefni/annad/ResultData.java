package is.skilaverkefni;

import is.skilaverkefni.annad.BiddingItem;
import is.skilaverkefni.annad.BidMenu;

import java.util.ArrayList;
import java.util.List;

public class ResultData {
    private String itemName;
    private String username;
    private List<BiddingItem> bids;

    public ResultData(String itemName, String username, List<BidMenu.Bid> bids) {
        this.itemName = itemName;
        this.username = username;
        this.bids = new ArrayList<>();
        for (BidMenu.Bid bid : bids) {
            BiddingItem item = new BiddingItem(
                    bid.getItem().getName(),
                    bid.getAmount(),
                    bid.getItem().getDescription(),
                    bid.getItem().getImage() // set the ImageView of the BiddingItem
            );
            this.bids.add(item);
        }
    }

    public String getItemName() {
        return itemName;
    }

    public String getUsername() {
        return username;
    }

    public List<BiddingItem> getBids() {
        return new ArrayList<>(bids);
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "itemName='" + itemName + '\'' +
                ", username='" + username + '\'' +
                ", bids=" + bids +
                '}';
    }
}
