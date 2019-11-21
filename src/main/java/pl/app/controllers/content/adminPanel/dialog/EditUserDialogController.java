package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.CheckComboBox;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.api.helpers.UserAccountTypeHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UserAccountModel;
import pl.app.api.model.UserAccountTypeModel;
import pl.app.api.responseInterfaces.EditUserResponseListener;
import pl.app.controllers.common.FieldValidator;
import pl.app.controllers.common.checkComboBoxItem.UserTypeCheckBoxItem;
import pl.app.core.baseComponent.BaseDialog;
import pl.app.core.utils.ResourceLoader;
import pl.app.core.property.DialogProperty;
import pl.app.launch.LaunchApp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class EditUserDialogController extends BaseDialog implements EditUserResponseListener {

    private UserAccountModel userAccountModel;
    private UserAccountTypeHelper userAccountTypeHelper;
    private UserAccountHelper userAccountHelper;
    private ObservableList<UserTypeCheckBoxItem> userAccountTypeModelObservableList;

    @FXML
    private Label userNameLabel;

    @FXML
    private JFXTextField userNameTextField;

    @FXML
    private CheckComboBox<UserTypeCheckBoxItem> userTypeCheckComboBox;

    @FXML
    private JFXTextField firstNameTextField;

    @FXML
    private JFXTextField lastNameTextField;

    @FXML
    private JFXTextField emailTextField;

    @FXML
    private JFXTextField peselTextField;

    @FXML
    private JFXTextField phoneNumberTextField;

    @FXML
    private Label responseLabel;

    @FXML
    private JFXComboBox<CategoriesModel> categoriesComboBox;


    public void initData(UserAccountModel userAccountModel) {
        this.userAccountModel = userAccountModel;
        initObjects();
        initHelpers();
        initUserTypeCheckComboBox();
        initGui();
        initValidators();
    }

    private void initGui() {
        userNameLabel.setText(userAccountModel.getFirstName() + " " + userAccountModel.getLastName());
        userNameTextField.setText(userAccountModel.getUsername());
        phoneNumberTextField.setText(userAccountModel.getPhoneNumber());
        emailTextField.setText(userAccountModel.getEmail());
        firstNameTextField.setText(userAccountModel.getFirstName());
        lastNameTextField.setText(userAccountModel.getLastName());
        peselTextField.setText(userAccountModel.getPesel());

        userAccountTypeModelObservableList.forEach(item -> {
            userAccountModel.getUserAccountTypeModels().forEach(type -> {
                if (item.getUserAccountTypeModel().equals(type)) {
                    userTypeCheckComboBox.getCheckModel().check(item);
                }
            });
        });



    }

    private void initUserTypeCheckComboBox() {

        userAccountTypeHelper.getExtraAccountTypes().forEach(model -> userAccountTypeModelObservableList.add(new UserTypeCheckBoxItem(model)));

        userTypeCheckComboBox.getItems().setAll(userAccountTypeModelObservableList);

    }


    private void initHelpers() {
        userAccountTypeHelper = new UserAccountTypeHelper(ApiResourcesClient.getApi());
        userAccountHelper = new UserAccountHelper(ApiResourcesClient.getApi());
    }

    private void initObjects() {
        userAccountTypeModelObservableList = FXCollections.observableArrayList();
    }

    private void initValidators() {
        FieldValidator.setNumberValidator("Nieprawidłowy format!", peselTextField);
        FieldValidator.setRegexValidator("Długość peselu musi wynosić 11 znaków", peselTextField, "^[0-9]{11,11}$");
        FieldValidator.setRequiredValidator("Proszę wpisać pesel", peselTextField);
        FieldValidator.setRequiredValidator("Proszę wpisać adres email!", emailTextField);
        FieldValidator.setRegexValidator("Nieprawidłowy adres email", emailTextField, "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        FieldValidator.setRequiredValidator("Proszę wpisać imię", firstNameTextField);
        FieldValidator.setRequiredValidator("Proszę wpisać nazwisko", lastNameTextField);
        FieldValidator.setRequiredValidator("Proszę wpisać numer telefonu!", phoneNumberTextField);
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        getDialogStage().close();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        if (peselTextField.validate() && firstNameTextField.validate() && lastNameTextField.validate()
                && phoneNumberTextField.validate() && userNameTextField.validate() && emailTextField.validate()) {
            List<UserAccountTypeModel> userAccountTypeModelList = new ArrayList<>();

            userTypeCheckComboBox.getCheckModel().getCheckedItems().forEach(item ->
                    userAccountTypeModelList.add(item.getUserAccountTypeModel())
            );


            userAccountModel.setPhoneNumber(phoneNumberTextField.getText());
            userAccountModel.setLastName(lastNameTextField.getText());
            userAccountModel.setFirstName(firstNameTextField.getText());
            userAccountModel.setEmail(emailTextField.getText());
            userAccountModel.setPesel(peselTextField.getText());
            userAccountModel.setUserAccountTypeModels(userAccountTypeModelList);
            userAccountHelper.editUserAccountById(userAccountModel, this);

            getDialogStage().close();
        }

    }

    @Override
    public void onEditUserSuccessResponse(ResponseModel responseModel) {
        responseLabel.setText(responseModel.getMessage());
    }

    @Override
    public void onEditUserFailedResponse(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder(responseModel.getMessage() + "\n");
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        responseLabel.setText(builder.toString());
    }
}