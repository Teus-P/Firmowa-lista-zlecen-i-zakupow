package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import org.controlsfx.control.CheckComboBox;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.api.helpers.UserAccountTypeHelper;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UserAccountModel;
import pl.app.api.model.UserAccountTypeModel;
import pl.app.api.responseInterfaces.NewUserResponseListener;
import pl.app.controllers.common.FieldValidator;
import pl.app.controllers.content.adminPanel.checkComboBoxItem.UserTypeCheckBoxItem;
import pl.app.core.baseComponent.BaseDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewUserDialogController extends BaseDialog implements NewUserResponseListener {

    private UserAccountTypeHelper userAccountTypeHelper;
    private UserAccountHelper userAccountHelper;
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

    @FXML
    private JFXTextField passwordTextField;

    @FXML
    private Label responseLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        initObjects();
        initHelpers();
        initUserTypeCheckComboBox();
        setupValidators();

    }

    @FXML
    void addButtonOnAction(ActionEvent event) {

        if (peselTextField.validate() && firstNameTextField.validate() && lastNameTextField.validate()
                && phoneNumberTextField.validate() && userNameTextField.validate() && emailTextField.validate() && passwordTextField.validate()) {

            List<UserAccountTypeModel> userAccountTypeModelList = new ArrayList<>();

            userTypeCheckComboBox.getCheckModel().getCheckedItems().forEach(item ->
                    userAccountTypeModelList.add(item.getUserAccountTypeModel())
            );

            UserAccountModel userAccountModel = new UserAccountModel(
                    userNameTextField.getText(),
                    firstNameTextField.getText(),
                    passwordTextField.getText(),
                    lastNameTextField.getText(),
                    Long.valueOf(peselTextField.getText()),
                    emailTextField.getText(),
                    phoneNumberTextField.getText(),
                    userAccountTypeModelList
            );

            userAccountHelper.saveNewUserAccount(userAccountModel, this);

            getDialogStage().close();
        }

    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        getDialogStage().close();
    }


    private void initObjects() {
        userAccountTypeModelObservableList = FXCollections.observableArrayList();
    }

    private void setupValidators() {

        FieldValidator.setNumberValidator("Nieprawidłowy format!", peselTextField);
        FieldValidator.setRegexValidator("Długość peselu musi wynosić 11 znaków", peselTextField, "^[0-9]{11,11}$");
        FieldValidator.setRequiredValidator("Proszę wpisać pesel", peselTextField);

        FieldValidator.setRequiredValidator("Proszę wpisać imię", firstNameTextField);
        FieldValidator.setRequiredValidator("Proszę wpisać nazwisko", lastNameTextField);
        FieldValidator.setRequiredValidator("Proszę wpisać numer telefonu!", phoneNumberTextField);
        FieldValidator.setRequiredValidator("Proszę wpisać nazwę użytkownika!", userNameTextField);
        FieldValidator.setRequiredValidator("Proszę wpisać adres email!", emailTextField);
        FieldValidator.setRegexValidator("Nieprawidłowy adres email", emailTextField, "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        FieldValidator.setRequiredValidator("Hasło nie może być puste", passwordTextField);

        FieldValidator.setRegexValidator("Długość hasła musi wynosić od 8 do 20 znaków oraz zawierać minimum jedną dużą i jedną małą lierę", passwordTextField,
                "^(?=.*[a-z])(?=.*[A-Z]).{8,}$");

    }


    private void initHelpers() {
        userAccountTypeHelper = new UserAccountTypeHelper(ApiResourcesClient.getApi());
        userAccountHelper = new UserAccountHelper(ApiResourcesClient.getApi());
    }

    private void initUserTypeCheckComboBox() {

        userAccountTypeHelper.getAllAccountTypes().forEach(model -> userAccountTypeModelObservableList.add(new UserTypeCheckBoxItem(model)));

        userTypeCheckComboBox.getItems().setAll(userAccountTypeModelObservableList);
    }


    @Override
    public void onNewUserResponseSuccess(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder();
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        responseLabel.setText(builder.toString());
    }

    @Override
    public void onNewUserResponseFailed(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder();
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        responseLabel.setText(builder.toString());
    }
}
