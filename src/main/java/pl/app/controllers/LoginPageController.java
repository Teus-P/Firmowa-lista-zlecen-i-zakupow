package pl.app.controllers;

import com.google.common.hash.Hashing;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pl.app.api.TokenKeeper;
import pl.app.api.clients.ApiAuthorizationClient;
import pl.app.api.helpers.TokenHelper;
import pl.app.api.model.TokenModel;
import pl.app.core.BasePage;
import pl.app.core.property.ScreensProperty;


import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * view/LoginPage.fxml Controller
 */
public class LoginPageController extends BasePage {

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

    @FXML
    private void onClickLoginButton() {

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

        tokenModel = tokenHelper.getAccessToken(requestBodyGrantType, requestBodyUsername, requestBodyPassword);

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


}
