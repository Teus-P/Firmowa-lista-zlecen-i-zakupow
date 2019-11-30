package pl.app.core.property;

import lombok.Getter;

/**
 * enum with fxml paths and Stage title
 */
@Getter
public enum StageProperty {
    MAIN_PAGE("views/screens/MainPanel.fxml", "Ekran główny"),
    LOGIN_PAGE("views/screens/LoginPage.fxml", "Logowanie"),
    EDIT_PRODUCT("views/content/adminPanel/dialog/EditProductDialog.fxml", "Edycja produktu"),
    EDIT_USER("views/content/adminPanel/dialog/EditUserDialog.fxml", "Edycja użytkownika"),
    NEW_PRODUCT("views/content/adminPanel/dialog/NewProductDialog.fxml", "Dodawanie produktu"),
    NEW_USER("views/content/adminPanel/dialog/NewUserDialog.fxml", "Dodawanie nowego użytkownika"),
    SET_IMPLEMENTER_CATEGORY("views/content/adminPanel/dialog/AddImplCategoryDialog.fxml", "Wybierz kategorię realizatora"),
    WAITING_ORDER_DETAILS("views/content/adminPanel/dialog/WaitingOrderDetailsDialog.fxml", "Szczegóły zamówienia"),
    ASSIGN_IMPLEMENTERS("views/content/adminPanel/dialog/AssignImplementersDialog.fxml", "Przypisywanie realizatorów do zamówienia"),
    REJECT_ORDER("views/content/adminPanel/dialog/RejectOrderDialog.fxml", "Odrzuć zamówienie");

    private String fxmlPath;
    private String stageTitle;

    StageProperty(String fxmlPath, String stageTitle) {
        this.fxmlPath = fxmlPath;
        this.stageTitle = stageTitle;
    }

}
