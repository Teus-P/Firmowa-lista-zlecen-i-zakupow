package pl.app.controllers.content.adminPanel.dialog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.app.api.model.UserAccountModel;
import pl.app.core.ResourceLoader;

import java.io.IOException;

public class EditUserDialog {

    private ResourceLoader resourceLoader = ResourceLoader.getInstance();
    private Stage window;
    private UserAccountModel userAccountModel;

    @FXML
    private Label userNameLabel;

    public EditUserDialog(UserAccountModel userAccountModel) {

        this.userAccountModel = userAccountModel;

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        window.setMinWidth(1280);
        window.setMinHeight(720);

        FXMLLoader fxmlLoader = resourceLoader.fxmlLoader("views/content/adminPanel/EditUserDialog.fxml");
        fxmlLoader.setController(this);
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setScene(scene);

        initView();

    }


    private void initView() {
        userNameLabel.setText(userAccountModel.getFirstName() + " " + userAccountModel.getLastName());
    }


    public void showAndWait() {
        window.showAndWait();
    }


}
