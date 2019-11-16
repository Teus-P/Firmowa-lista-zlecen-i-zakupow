package pl.app.controllers.content.adminPanel.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.app.core.baseComponent.BaseDialog;

public class NewUserDialogController extends BaseDialog {

    @FXML
    void addButtonOnAction(ActionEvent event) {

    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        getDialogStage().close();
    }

}
