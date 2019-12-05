package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.controlsfx.control.CheckComboBox;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.api.helpers.UserAccountTypeHelper;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UserAccountModel;
import pl.app.api.model.UserAccountTypeModel;
import pl.app.api.responseInterfaces.NewUserResponseListener;
import pl.app.controllers.common.FieldValidator;
import pl.app.controllers.common.checkComboBoxItem.UserTypeCheckBoxItem;
import pl.app.core.baseComponent.BaseDialog;
import pl.app.core.dialog.DialogStage;
import pl.app.core.property.StageProperty;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewUserDialogController extends BaseDialog implements NewUserResponseListener {

    private static final String IMPLEMENTER_ROLE = "Role_IMPLEMENTERS";

    private UserAccountTypeHelper userAccountTypeHelper;
    private UserAccountHelper userAccountHelper;
    private ObservableList<UserTypeCheckBoxItem> userAccountTypeModelObservableList;
    private UserAccountModel newUserAccount;
    private List<UserAccountTypeModel> userAccountTypeModelList;

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
                && phoneNumberTextField.validate() && userNameTextField.validate() && emailTextField.validate()) {


            userAccountTypeModelList = new ArrayList<>();

            userTypeCheckComboBox.getCheckModel().getCheckedItems().forEach(item ->
                    userAccountTypeModelList.add(item.getUserAccountTypeModel())
            );

            newUserAccount = new UserAccountModel(
                    userNameTextField.getText(),
                    firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    peselTextField.getText(),
                    emailTextField.getText(),
                    phoneNumberTextField.getText(),
                    userAccountTypeModelList
            );

            userAccountHelper.saveNewUserAccount(newUserAccount, this);

            if (userAccountTypeModelList.stream().anyMatch(item -> item.getName().equals(IMPLEMENTER_ROLE))) {
                showChooseImplementerCategory();
            }


            getDialogStage().close();
        }

    }


    private void showChooseImplementerCategory() {
        DialogStage implementerCategories = new DialogStage(StageProperty.SET_IMPLEMENTER_CATEGORY);
        AddImplCategoryDialogController controller = implementerCategories.getController();
        controller.initData(this.newUserAccount);
        controller.setOnDialogCloseListener(() -> {
        });
        implementerCategories.showAndWait();

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
        FieldValidator.setPeselValidator(peselTextField);

        FieldValidator.setRequiredValidator("Proszę wpisać pesel", peselTextField);
        FieldValidator.setRequiredValidator("Proszę wpisać imię", firstNameTextField);
        FieldValidator.setRequiredValidator("Proszę wpisać nazwisko", lastNameTextField);
        FieldValidator.setRequiredValidator("Proszę wpisać numer telefonu", phoneNumberTextField);
        FieldValidator.setRequiredValidator("Proszę wpisać nazwę użytkownika", userNameTextField);

        FieldValidator.setRequiredValidator("Proszę wpisać adres email", emailTextField);
        FieldValidator.setEmailValidator(emailTextField);
    }


    private void initHelpers() {
        userAccountTypeHelper = new UserAccountTypeHelper(ApiResourcesClient.getApi());
        userAccountHelper = new UserAccountHelper(ApiResourcesClient.getApi());
    }

    private void initUserTypeCheckComboBox() {

        userAccountTypeHelper.getExtraAccountTypes().forEach(model -> userAccountTypeModelObservableList.add(new UserTypeCheckBoxItem(model)));

        userTypeCheckComboBox.getItems().setAll(userAccountTypeModelObservableList);
    }


    @Override
    public void onNewUserResponseSuccess(UserAccountModel userAccountModel) {
        this.newUserAccount = userAccountModel;
    }

    @Override
    public void onNewUserResponseFailed(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder();
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        responseLabel.setText(builder.toString());
    }
}
