package pl.app.controllers.content;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pl.app.api.UserSession;
import pl.app.api.clients.ApiAuthorizationClient;
import pl.app.api.helpers.TokenHelper;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.TokenModel;
import pl.app.api.responseInterfaces.LoginResponseListener;
import pl.app.controllers.common.FieldValidator;
import pl.app.core.baseComponent.BasePage;
import pl.app.core.property.StageProperty;


import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static com.google.common.hash.Hashing.*;

/**
 * view/LoginPage.fxml Controller
 */
//login Administrator
//hasło YzdhZDQ0Y2JhZDc2MmE1ZGEwYTQ1MmY5ZTg1NGZkYzFlMGU3YTUyYTM4MDE1ZjIzZjNlYWIxZDgwYjkzMWRkNDcyNjM0ZGZhYzcxY2QzNGViYzM1ZDE2YWI3ZmI4YTkwYzgxZjk3NTExM2Q2Yzc1MzhkYzY5ZGQ4ZGU5MDc3ZWM=
public class LoginPageController extends BasePage implements LoginResponseListener {

    private static final Logger LOGGER = Logger.getLogger(LoginPageController.class.getName());

    private TokenHelper tokenHelper;
    private TokenModel tokenModel;
    private String userLogin;
    private String userPassword;
    private Alert loginAlert;

    @FXML
    private JFXTextField loginTextField;

    @FXML
    private JFXPasswordField passwordPasswordField;

    public LoginPageController() {
        tokenHelper = new TokenHelper(ApiAuthorizationClient.getApi());
        loginAlert = new Alert(Alert.AlertType.ERROR);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        setLoginAlertContent();
        setValidators();
    }


    private void setValidators() {
        FieldValidator.setRequiredValidator("Proszę wprowadzić login", loginTextField);
        FieldValidator.setRequiredValidator("Proszę wprowadzić hasło", passwordPasswordField);
    }

    @FXML
    private void onClickLoginButton() {

        if (loginTextField.validate() && passwordPasswordField.validate()) {
            userLogin = loginTextField.getText();
            userPassword = passwordPasswordField.getText();
            String encryptedPassword = userPassword;
            LOGGER.info("PASS : " + encryptedPassword);
            if (getTokenByUserCredentials(userLogin, encryptedPassword) != null) {

                LOGGER.info("TOKEN : " + tokenModel.getAccessToken());
                UserSession.setSession(tokenModel.getAccessToken(), tokenModel.getRefreshToken());

                showMainPage();
            } else {
                loginAlert.showAndWait();
            }
        } else {
            loginAlert.showAndWait();
        }

    }

    private TokenModel getTokenByUserCredentials(String username, String password) {

        RequestBody requestBodyGrantType = RequestBody.create("password", MediaType.parse("multipart/form-data"));
        RequestBody requestBodyUsername = RequestBody.create(username, MediaType.parse("multipart/form-data"));
        RequestBody requestBodyPassword = RequestBody.create(password, MediaType.parse("multipart/form-data"));

        tokenModel = tokenHelper.getAccessToken(requestBodyGrantType, requestBodyUsername, requestBodyPassword, this);

        return tokenModel;
    }

    private void showMainPage() {
        super.screenController.setScreenProperty(StageProperty.MAIN_PAGE).show();
    }


    private void setLoginAlertContent() {
        loginAlert.setTitle(getResourceBundle().getString("loginAlertTitle"));
        loginAlert.setHeaderText(getResourceBundle().getString("loginAlertHeaderText"));
        loginAlert.setContentText(getResourceBundle().getString("loginAlertContentText"));
    }


//    private String encryptPassword(String password) {
//
//        String pass = sha512().hashString(password, StandardCharsets.UTF_8).toString();
//        return Base64.getEncoder().encodeToString(pass.getBytes());
//    }


    @Override
    public void onFailedServerConnection(ResponseModel errorResponse) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorResponse.getMessage());
        alert.setHeaderText(errorResponse.getStatus());
        alert.setContentText(errorResponse.getTimestamp());
        Label label = new Label("The exception stacktrace was:");
        TextArea textArea = new TextArea(errorResponse.getDetails().get(0));
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
