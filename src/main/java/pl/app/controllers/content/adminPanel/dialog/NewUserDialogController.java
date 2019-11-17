package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.controlsfx.control.CheckComboBox;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.UserAccountTypeHelper;
import pl.app.controllers.content.adminPanel.checkComboBoxItem.UserTypeCheckBoxItem;
import pl.app.core.baseComponent.BaseDialog;

import java.net.URL;
import java.util.ResourceBundle;

public class NewUserDialogController extends BaseDialog {

    private UserAccountTypeHelper userAccountTypeHelper;
    private ObservableList<UserTypeCheckBoxItem> userAccountTypeModelObservableList;

    @FXML
    private JFXTextField firstNameTextField;

    @FXML
    private JFXTextField emailTextField;

    @FXML
    private JFXTextField peselTextField;

    @FXML
    private JFXTextField userNameTextField;

    @FXML
    private CheckComboBox<UserTypeCheckBoxItem> userTypeCheckComboBox;

    @FXML
    private JFXTextField lastNameTextField;

    @FXML
    private JFXTextField phoneNumberTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        initObjects();
        initHelpers();
        initUserTypeCheckComboBox();
    }

    @FXML
    void addButtonOnAction(ActionEvent event) {

    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        getDialogStage().close();
    }



    private void initObjects() {
        userAccountTypeModelObservableList = FXCollections.observableArrayList();
    }

    private void initHelpers() {
        userAccountTypeHelper = new UserAccountTypeHelper(ApiResourcesClient.getApi());
    }

    private void initUserTypeCheckComboBox() {

        userAccountTypeHelper.getAllAccountTypes().forEach(model -> userAccountTypeModelObservableList.add(new UserTypeCheckBoxItem(model)));

        userTypeCheckComboBox.getItems().setAll(userAccountTypeModelObservableList);
    }

}
