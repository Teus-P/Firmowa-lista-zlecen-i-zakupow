package pl.app.core.property;

public enum ContentProperty {
    ADMIN_CONTEXT("views/content/adminPanel/AdminPanel.fxml"),
    HISTORY_OF_ORDERS("views/content/HistoryOfOrders.fxml"),
    CREATE_ORDER("views/content/CreateOrder.fxml"),
    CURRENT_ORDERS("views/content/CurrentOrders.fxml"),
    USER_PANEL("views/content/UserPanel.fxml")
    ;


    private String screenPath;


    ContentProperty(String screenPath) {
        this.screenPath = screenPath;

    }


    public String getScreenPath() {
        return screenPath;
    }
}
