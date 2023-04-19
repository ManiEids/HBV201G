package is.skilaverkefni.annad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.InputStream;

public class AuctionItemProvider {
    private final ObservableList<BiddingItem> availableItems;

    public AuctionItemProvider() {
        availableItems = FXCollections.observableArrayList();
        loadItems();
    }

    private void loadItems() {
        addItem("Málverk", 2500, "Frægt málverk eftir Leonardo da Vinci", "/is/skilaverkefni/mynd.png");
        addItem("Bíll", 1400, "BMW m5 breyttur e39 station ", "/is/skilaverkefni/bill.png");
        addItem("Bók", 1500, "Saga Íslands í 20. öld", "/is/skilaverkefni/bok.png");
        addItem("Ljósmyndavél", 3500, "Canon EOS R6", "/is/skilaverkefni/camera.png");
        addItem("Hjól", 1000, "Carbon fiber hjól , aðeins 4 kg", "/is/skilaverkefni/hjol.png");
    }

    private void addItem(String name, int startingPrice, String description, String imagePath) {
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            availableItems.add(new BiddingItem(name, startingPrice, description, image));
        } else {
            System.err.println("Error loading " + imagePath + " for " + name);
        }
    }

    public ObservableList<BiddingItem> getAvailableItems() {
        return availableItems;
    }
}