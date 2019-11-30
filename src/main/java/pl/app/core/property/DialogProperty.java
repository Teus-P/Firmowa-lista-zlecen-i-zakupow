package pl.app.core.property;

import lombok.Getter;

@Getter
public enum DialogProperty {
    EDIT_PRODUCT("views/content/adminPanel/dialog/EditProductDialog.fxml", "Edycja produktu"),
    EDIT_USER("views/content/adminPanel/dialog/EditUserDialog.fxml", "Edycja użytkownika"),
    NEW_PRODUCT("views/content/adminPanel/dialog/NewProductDialog.fxml", "Dodawanie produktu"),
    NEW_USER("views/content/adminPanel/dialog/NewUserDialog.fxml", "Dodawanie nowego użytkownika"),
    SET_IMPLEMENTER_CATEGORY("views/content/adminPanel/dialog/AddImplCategoryDialog.fxml", "Wybierz kategorię realizatora"),
    WAITING_ORDER_DETAILS("views/content/adminPanel/dialog/WaitingOrderDetailsDialog.fxml", "Szczegóły zamówienia"),
    ASSIGN_IMPLEMENTERS("views/content/adminPanel/dialog/AssignImplementersDialog.fxml", "Przypisywanie realizatorów do zamówienia"),
    REJECT_ORDER("views/content/adminPanel/dialog/RejectOrderDialog.fxml","Odrzuć zamówienie");


    private String dialogFxmlPath;
    private String dialogTitle;


    DialogProperty(String dialogFxmlPath, String dialogTitle) {
        this.dialogFxmlPath = dialogFxmlPath;
        this.dialogTitle = dialogTitle;
    }


}
