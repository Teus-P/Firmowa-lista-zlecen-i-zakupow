package pl.app.utils.screenManager;

/**
 * enum ze sciezka do fxmla , rozmiary okna , tytul okna
 */
public enum ScreensProperty {
    MAIN_PAGE("mainPage", "views/MainPanel.fxml", "Main page"),
    LOGIN_PAGE("loginPage", "views/LoginPage.fxml", "LOGOWANIE"),
    ACCEPTED_ORDER_FIELD("acceptedOrderField", "views/AcceptedOrderField.fxml", "Accepted order field"),
    ADD_NEW_PRODUCT("addNewProduct", "views/AddNewProduct.fxml", "Add new product"),
    ADMIN_PANEL("adminPanel", "views/AdminPanel.fxml", "Admin panel"),
    CHANGE_PASSWORD_PAGE("changePasswordPage", "views/ChangePasswordPage.fxml", "Change password page"),
    CREATE_ORDER("createOrder", "views/CreateOrder.fxml","Create order"),
    CURRENT_ORDERS("currentOrders", "views/CurrentOrders.fxml","Current orders"),
    DETAILS_OF_ORDER("detailsOfOrder", "views/DetailsOfOrder.fxml","Details of order"),
    EDIT_ORDER("editOrder", "views/EditOrder.fxml","Edit order"),
    HISTORY_OF_ORDERS("historyOfOrders", "views/HistoryOfOrders.fxml","History of orders"),
    PRODUCT_FIELD("productField", "views/ProductField.fxml","Product field"),
    PRODUCT_FIELD_IN_ACCEPTED_ORDER("productFieldInAcceptedOrder", "views/ProductFieldInAcceptedOrder.fxml","Product field in accepted order"),
    PRODUCT_FIELD_IN_UNACCEPTED_ORDER("productFieldInUnacceptedOrder", "views/ProductFieldInUnacceptedOrder.fxml","Product field in unaccepted order"),
    RESET_PASSWORD_PAGE("resetPasswordPage", "views/ResetPasswordPage.fxml","Reset password page"),
    UNACCEPTED_ORDER_FIELD("unacceptedOrderField", "views/UnacceptedOrderField.fxml","Unaccepted order field"),
    USER_PANEL("userPanel", "views/UserPanel.fxml","User panel"),
    USER_PANEL_PAGE("userPanelPage", "views/userPanelPage.fxml", "User panel page");

    private String screenId;
    private String screenPath;

    private String title;


    ScreensProperty(String screenId, String screenPath, String title) {
        this.screenId = screenId;
        this.screenPath = screenPath;
        this.title = title;
    }

    public String getScreenId() {
        return screenId;
    }

    public String getScreenPath() {
        return screenPath;
    }


    public String getTitle() {
        return title;
    }
}
