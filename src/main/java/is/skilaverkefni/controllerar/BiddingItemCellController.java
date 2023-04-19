package is.skilaverkefni.controllerar;

import is.skilaverkefni.annad.BiddingItem;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class BiddingItemCellController {
    @FXML
    private ImageView itemImageView;
    @FXML
    private Text itemPriceText;

    @FXML
    private Text itemNameText;

    @FXML
    private Text itemDescriptionText;

    public void setBiddingItem(BiddingItem item) {
        itemImageView.setImage(item.getImage());
        itemNameText.setText(item.getName());
        itemDescriptionText.setText(item.getDescription());

        if (item.getAskingPrice() >= item.getHighestBid()) {
            itemPriceText.setText(String.format("Price: %d kr.", item.getAskingPrice()));
        } else {
            itemPriceText.setText(String.format("Nýtt hæsta boð:*** %d kr ***", item.getHighestBid(), item.getStartingPrice()));
        }
    }
}