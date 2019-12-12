package pl.app.core.property;

public enum ContentProperty {
    ADMIN_CONTEXT("views/content/adminPanel/AdminPanel.fxml"),
    HISTORY_OF_ORDERS("views/content/OrdersHistory.fxml"),
    CREATE_ORDER("views/content/CreateOrder.fxml"),
    CURRENT_ORDERS("views/content/CurrentOrders.fxml"),
    USER_PANEL("views/content/UserPanel.fxml"),
    USERS_TAB_PAGE("views/content/adminPanel/tabs/UsersTabPage.fxml"),
    PRODUCTS_TAB_PAGE("views/content/adminPanel/tabs/ProductsTabPage.fxml"),
    ORDERS_TAB_PAGE("views/content/adminPanel/tabs/OrdersTabPage.fxml"),
    UNITS_TAB_PAGE("views/content/adminPanel/tabs/UnitsTabPage.fxml"),
    CATEGORIES_TAB_PAGE("views/content/adminPanel/tabs/CategoriesTabPage.fxml"),
    REALIZED_ORDER("views/content/RealizedOrder.fxml"),
    HELP_SCREEN("views/content/HelpScreen.fxml"),
    HOME_PAGE("views/content/HomePage.fxml");


    private String screenPath;


    ContentProperty(String screenPath) {
        this.screenPath = screenPath;

    }


    public String getScreenPath() {
        return screenPath;
    }
}
