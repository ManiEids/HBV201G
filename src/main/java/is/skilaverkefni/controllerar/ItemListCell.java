package is.skilaverkefni.controllerar;

import is.skilaverkefni.annad.BiddingItem;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ItemListCell extends ListCell<BiddingItem> {

    private final ImageView imageView;
    private final Text itemNameLabel;
    private final Text amountPaidLabel;
    private final HBox hbox;

    public ItemListCell() {
        super();
        this.imageView = new ImageView();
        this.itemNameLabel = new Text();
        this.amountPaidLabel = new Text();
        this.hbox = new HBox(10);
        hbox.getChildren().addAll(imageView, itemNameLabel, amountPaidLabel);
    }

    @Override
    protected void updateItem(BiddingItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            return;
        }
        imageView.setImage(item.getImage());
        itemNameLabel.setText(item.getName());
        amountPaidLabel.setText(String.format("%.2f", (double) item.getHighestBid()));
        setGraphic(hbox);
    }
}
