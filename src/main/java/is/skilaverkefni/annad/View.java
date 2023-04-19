package is.skilaverkefni.annad;

public enum View {
    BIDDING_MENU("/is/skilaverkefni/BidMenu.fxml"),
    START_MENU("/is/skilaverkefni/StartMenu.fxml"),
    RESULT_MENU("/is/skilaverkefni/ResultMenu.fxml");

    private final String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}