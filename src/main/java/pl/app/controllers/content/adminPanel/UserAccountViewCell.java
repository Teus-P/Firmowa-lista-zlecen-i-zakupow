package pl.app.controllers.content.adminPanel;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import pl.app.api.model.UserAccountModel;

import pl.app.controllers.content.adminPanel.dialog.EditUserDialog;
import pl.app.core.ResourceLoader;

import java.io.IOException;

public class UserAccountViewCell extends ListCell<UserAccountModel> {

    @FXML
    private Label userNameLabel;

    @FXML
    private AnchorPane rowContainer;


    private ResourceLoader resourceLoader = ResourceLoader.getInstance();

    @Override
    protected void updateItem(UserAccountModel userModel, boolean empty) {
        super.updateItem(userModel, empty);

        if (empty || userModel == null) {
            setText(null);
            setGraphic(null);
        } else {

            FXMLLoader fxmlLoader = resourceLoader.fxmlLoader("views/content/adminPanel/UserListViewRow.fxml");
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (userModel != null) {
            userNameLabel.setText(userModel.getFirstName() + " " + userModel.getLastName());
        }


        setText(null);
        setGraphic(rowContainer);




    }
}
