package pl.app.core.property;

/**
 * enum with fxml paths and Stage title
 */
public enum ScreensProperty {
    MAIN_PAGE("views/screens/MainPanel.fxml", "Main page"),
    LOGIN_PAGE("views/screens/LoginPage.fxml", "LOGOWANIE"),
    ACCEPTED_ORDER_FIELD("views/AcceptedOrderField.fxml", "Accepted order field"),
    ADD_NEW_PRODUCT("views/AddNewProduct.fxml", "Add new product"),
    ADMIN_PANEL("views/context/AdminPanel.fxml", "Admin panel"),
    CHANGE_PASSWORD_PAGE("views/ChangePasswordPage.fxml", "Change password page"),
    CREATE_ORDER("views/CreateOrder.fxml", "Create order"),
    CURRENT_ORDERS("views/CurrentOrders.fxml", "Current orders"),
    DETAILS_OF_ORDER("views/DetailsOfOrder.fxml", "Details of order"),
    EDIT_ORDER("views/EditOrder.fxml", "Edit order"),
    HISTORY_OF_ORDERS("views/HistoryOfOrders.fxml", "History of orders"),
    PRODUCT_FIELD("views/ProductField.fxml", "Product field"),
    PRODUCT_FIELD_IN_ACCEPTED_ORDER("views/ProductFieldInAcceptedOrder.fxml", "Product field in accepted order"),
    PRODUCT_FIELD_IN_UNACCEPTED_ORDER("views/ProductFieldInUnacceptedOrder.fxml", "Product field in unaccepted order"),
    RESET_PASSWORD_PAGE("views/ResetPasswordPage.fxml", "Reset password page"),
    UNACCEPTED_ORDER_FIELD("views/UnacceptedOrderField.fxml", "Unaccepted order field"),
    USER_PANEL("views/UserPanel.fxml", "User panel"),
    USER_PANEL_PAGE("views/userPanelPage.fxml", "User panel page");


    private String screenPath;
    private String stageTitle;


    ScreensProperty(String screenPath, String stageTitle) {
        this.screenPath = screenPath;
        this.stageTitle = stageTitle;
    }


    public String getScreenPath() {
        return screenPath;
    }


    public String getStageTitle() {
        return stageTitle;
    }
}
