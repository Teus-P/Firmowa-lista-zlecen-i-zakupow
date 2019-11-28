package pl.app.controllers.common.popupDialogs;

import javafx.scene.control.Alert;

public class InformationDialog {

    public static void showInformationDialog(String headerText, String contentText) {
        var warningAlert = new Alert(Alert.AlertType.INFORMATION);
        warningAlert.setHeaderText(headerText);
        warningAlert.setContentText(contentText);
        warningAlert.showAndWait();
    }
}
