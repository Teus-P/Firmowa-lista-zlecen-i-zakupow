package pl.app.controllers.common.informationDialog;

import javafx.scene.control.Alert;

public class WarningDialog {

    /**
     * simple popup information dialog
     *
     * @param headerText  window header text
     * @param contentText window content text
     */
    public static void showWarningDialog(String headerText, String contentText) {
        var warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setHeaderText(headerText);
        warningAlert.setContentText(contentText);
        warningAlert.showAndWait();
    }

}
