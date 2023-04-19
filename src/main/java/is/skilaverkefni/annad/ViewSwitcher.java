package is.skilaverkefni.annad;

import is.skilaverkefni.ResultData;
import is.skilaverkefni.RunApplication;
import is.skilaverkefni.controllerar.AuctionResultController;
import is.skilaverkefni.controllerar.BidMenuController;
import is.skilaverkefni.controllerar.StartMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewSwitcher {
    private static final Map<View, Parent> cache = new HashMap<>();
    private static final Map<View, Object> controllers = new HashMap<>();
    private static Scene scene;
    private static RunApplication appInstance;

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        ViewSwitcher.scene = scene;
    }

    public static void setAppInstance(RunApplication appInstance) {
        ViewSwitcher.appInstance = appInstance;
    }

    public static void switchTo(View view, Object data) {
        if (scene == null) {
            throw new RuntimeException("Scene has not been set");
        }

        try {
            Parent root;
            FXMLLoader loader;
            if (cache.containsKey(view)) {
                System.out.println("Loading from cache");
                root = cache.get(view);
                loader = null;
            } else {
                System.out.println("Loading from FXML");
                loader = new FXMLLoader(ViewSwitcher.class.getResource(view.getFileName()));
                root = loader.load();
                cache.put(view, root);
                controllers.put(view, loader.getController());
            }

            Object controller = controllers.get(view);
            if (controller instanceof StartMenuController) {
                initializeStartMenuController(controller);
            } else if (controller instanceof BidMenuController) {
                initializeBidMenuController(controller, data);
            } else if (controller instanceof AuctionResultController) {
                initializeAuctionResultController(controller, data);
            }

            scene.setRoot(root);
        } catch (IOException e) {
            System.err.println("Error loading view: " + view);
            e.printStackTrace();
        }
    }

    private static void initializeStartMenuController(Object controller) {
        StartMenuController startMenuController = (StartMenuController) controller;
        startMenuController.setAppInstance(appInstance);
    }

    private static void initializeBidMenuController(Object controller, Object data) {
        BidMenuController bidMenuController = (BidMenuController) controller;
        bidMenuController.setAppInstance(appInstance);
        Pair<String, Integer> dataPair = (Pair<String, Integer>) data;
        String username = dataPair.getKey();
        Integer countdown = dataPair.getValue();
        bidMenuController.setUsername(username);
        bidMenuController.initialize(new Pair<>(username, countdown));
        bidMenuController.startBidding(new Pair<>(username, countdown));
    }

    private static void initializeAuctionResultController(Object controller, Object data) {
        AuctionResultController auctionResultController = (AuctionResultController) controller;
        auctionResultController.setAppInstance(appInstance);
        if (data instanceof ResultData) {
            ResultData resultData = (ResultData) data;
            auctionResultController.setResultData(resultData);
            int totalAmount = auctionResultController.calculateTotalAmount(resultData.getBids());
            auctionResultController.updateUI(resultData.getUsername(), totalAmount);
        }
    }
}
