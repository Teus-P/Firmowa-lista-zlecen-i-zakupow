package pl.app.core.property;

import lombok.Getter;

@Getter
public enum DialogProperty {
    EDIT_PRODUCT("views/content/adminPanel/dialog/EditProductDialog.fxml", "Edycja produktu"),
    EDIT_USER("views/content/adminPanel/dialog/EditUserDialog.fxml", "Edycja użytkownika"),
    NEW_PRODUCT("views/content/adminPanel/dialog/NewProductDialog.fxml", "Dodawanie produktu"),
    NEW_USER("views/content/adminPanel/dialog/NewUserDialog.fxml", "Dodawanie nowego użytkownika"
    );


    private String dialogFxmlPath;
    private String dialogTitle;


    DialogProperty(String dialogFxmlPath, String dialogTitle) {
        this.dialogFxmlPath = dialogFxmlPath;
        this.dialogTitle = dialogTitle;
    }


}
