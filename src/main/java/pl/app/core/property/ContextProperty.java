package pl.app.core.property;

public enum ContextProperty {
    ADMIN_CONTEXT("views/context/AdminPanel.fxml"),
    HISTORY_OF_ORDERS("views/HistoryOfOrders.fxml");


    private String screenPath;


    ContextProperty(String screenPath) {
        this.screenPath = screenPath;

    }


    public String getScreenPath() {
        return screenPath;
    }
}
