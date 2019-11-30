package pl.app.controllers.content;

import com.google.common.hash.Hashing;
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
import pl.app.api.TokenKeeper;
import pl.app.api.clients.ApiAuthorizationClient;
import pl.app.api.helpers.TokenHelper;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.TokenModel;
import pl.app.api.responseInterfaces.LoginResponseListener;
import pl.app.core.baseComponent.BasePage;
import pl.app.core.property.ScreensProperty;


import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * view/LoginPage.fxml Controller
 */
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


    }

    private void developTimeFastLoginMethod() {
        getTokenByUserCredentials("Administrator", "admin");
        saveTokenAfterSuccessLogin();
        showMainPage();
    }

    @FXML
    private void onClickLoginButton() {

        developTimeFastLoginMethod();

        userLogin = loginTextField.getText();
        userPassword = passwordPasswordField.getText();

        if (!userLogin.equals("") && !userPassword.equals("") && userLogin.length() > 0 && userPassword.length() > 0) {
            if (getTokenByUserCredentials(userLogin, userPassword) != null) {

                LOGGER.info("TOKEN : " + tokenModel.getAccessToken());

                saveTokenAfterSuccessLogin();

                showMainPage();
            } else {
                loginAlert.showAndWait();
            }

        } else {
            loginAlert.showAndWait();
        }
    }

    private TokenModel getTokenByUserCredentials(String username, String password) {

        RequestBody requestBodyGrantType = RequestBody.create(MediaType.parse("multipart/form-data"), "password");
        RequestBody requestBodyUsername = RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("multipart/form-data"), password);

        tokenModel = tokenHelper.getAccessToken(requestBodyGrantType, requestBodyUsername, requestBodyPassword, this);

        return tokenModel;
    }

    private void showMainPage() {
        super.screenController.setScreenProperty(ScreensProperty.MAIN_PAGE).show();
    }

    private void saveTokenAfterSuccessLogin() {
        TokenKeeper.setAccessToken(tokenModel.getAccessToken());
        TokenKeeper.setRefreshToken(tokenModel.getRefreshToken());
    }

    private void setLoginAlertContent() {
        loginAlert.setTitle(getResourceBundle().getString("loginAlertTitle"));
        loginAlert.setHeaderText(getResourceBundle().getString("loginAlertHeaderText"));
        loginAlert.setContentText(getResourceBundle().getString("loginAlertContentText"));
    }


    private String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }


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
