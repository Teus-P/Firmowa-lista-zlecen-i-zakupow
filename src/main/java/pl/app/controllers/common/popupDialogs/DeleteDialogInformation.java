package pl.app.controllers.common.popupDialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class DeleteDialogInformation {

    /**
     * Show popup dialog
     *
     * @param title   window title
     * @param header  window header
     * @param content window content text
     * @return true if click delete button or false if click cancel button
     */
    public static boolean showDeleteDialogInformation(String title, String header, String content) {

        var wrapper = new Object() {
            boolean buttonClickFlag;
        };
        var delete = new ButtonType("Usuń");
        var cancel = new ButtonType("Anuluj");
        var alert = new Alert(Alert.AlertType.NONE, "", delete, cancel);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait().ifPresent(response -> {
            if (response == delete) {
                wrapper.buttonClickFlag = true;
            }
        });
        return wrapper.buttonClickFlag;
    }

}
