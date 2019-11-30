package pl.app.controllers.content;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.api.model.UserAccountModel;

import java.net.URL;
import java.util.ResourceBundle;

public class HelpScreenController implements Initializable {

    private UserAccountHelper userAccountHelper;
    private UserAccountModel userAccountModel;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneNumberLabel;

    public HelpScreenController() {

        userAccountHelper = new UserAccountHelper(ApiResourcesClient.getApi());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userAccountModel = userAccountHelper.getAdministratorInfo();

        initUI();
    }

    private void initUI() {
        fullNameLabel.setText(userAccountModel.getFirstName() + " " + userAccountModel.getLastName());
        emailLabel.setText(userAccountModel.getEmail());
        phoneNumberLabel.setText(userAccountModel.getPhoneNumber());
    }
}
