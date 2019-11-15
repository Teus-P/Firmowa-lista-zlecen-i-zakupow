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
import pl.app.core.ResourceLoader;
import pl.app.core.property.DialogProperty;
import pl.app.launch.LaunchApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class EditUserDialog extends Stage implements Initializable {

    private ResourceLoader resourceLoader = ResourceLoader.getInstance();
    private UserAccountModel userAccountModel;

    @FXML
    private Label userNameLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameLabel.setText(userAccountModel.getFirstName() + " " + userAccountModel.getLastName());
    }


    public EditUserDialog(UserAccountModel userAccountModel) {

        this.userAccountModel = userAccountModel;

        FXMLLoader fxmlLoader = resourceLoader.fxmlLoader(DialogProperty.EDIT_USER.getDialogFxmlPath());
        fxmlLoader.setController(this);

        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UTILITY);
        setMinWidth(1280);
        setMinHeight(720);
        setTitle(DialogProperty.EDIT_USER.getDialogTitle());
        try {
            setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setOnCloseRequest(e->{
            LaunchApp.getPrimaryStage().getScene().getRoot().setEffect(null);
            close();
        });


    }


}
