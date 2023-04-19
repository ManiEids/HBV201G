package is.skilaverkefni.annad;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class BiddingItem {
    private final String name;
    private SimpleIntegerProperty startingPrice;
    private SimpleIntegerProperty highestBid;
    private SimpleIntegerProperty askingPrice;
    private Image image;
    private StringProperty description;
    private StringProperty highestBidString;

    public BiddingItem(String name, int startingPrice, String description, Image image) {
        this.name = name;
        this.startingPrice = new SimpleIntegerProperty(startingPrice);
        this.highestBid = new SimpleIntegerProperty(startingPrice);
        this.askingPrice = new SimpleIntegerProperty(startingPrice);
        this.description = new SimpleStringProperty(description);
        this.image = image;
        this.highestBidString = new SimpleStringProperty("Nýtt hæsta boð *** " + startingPrice + " kr ***");
        this.highestBid.addListener((obs, oldValue, newValue) -> this.highestBidString.set("Nýtt hæsta boð *** " + newValue + " kr ***"));
    }

    public String getName() {
        return name;
    }

    public int getStartingPrice() {
        return startingPrice.get();
    }

    public void updateStartingPrice(int newPrice) {
        this.startingPrice.set(newPrice);
        this.highestBid.set(newPrice);
        this.askingPrice.set(newPrice);
    }

    public int getAskingPrice() {
        return askingPrice.get();
    }

    public void setAskingPrice(int askingPrice) {
        this.askingPrice.set(askingPrice);
    }

    public int getHighestBid() {
        return highestBid.get();
    }

    public void setHighestBid(int bidAmount) {
        highestBid.set(bidAmount);
    }

    public SimpleIntegerProperty highestBidProperty() {
        return highestBid;
    }

    public int getBidAmount() {
        return highestBid.get() > startingPrice.get() ? highestBid.get() : 0;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public StringProperty highestBidStringProperty() {
        return highestBidString;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
