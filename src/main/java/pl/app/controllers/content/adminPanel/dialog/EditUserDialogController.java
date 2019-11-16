package pl.app.controllers.content.adminPanel.dialog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.app.api.model.UserAccountModel;
import pl.app.core.baseComponent.BaseDialog;
import pl.app.core.utils.ResourceLoader;
import pl.app.core.property.DialogProperty;
import pl.app.launch.LaunchApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class EditUserDialogController extends BaseDialog {

    private UserAccountModel userAccountModel;

    @FXML
    private Label userNameLabel;


    public void initData(UserAccountModel userAccountModel) {
        this.userAccountModel = userAccountModel;
        initGui();
    }

    private void initGui() {
        userNameLabel.setText(userAccountModel.getFirstName() + " " + userAccountModel.getLastName());
    }

}
