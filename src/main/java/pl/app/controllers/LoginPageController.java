package pl.app.controllers;

import com.google.common.hash.Hashing;
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
import pl.app.utils.screenManager.ControlledScreen;
import pl.app.utils.screenManager.ScreenController;
import pl.app.utils.screenManager.ScreensProperty;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class LoginPageController implements ControlledScreen, Initializable {

    private ScreenController screenController;
    private TokenHelper tokenHelper;
    private TokenModel tokenModel;
    private String userLogin;
    private String userPassword;
    private ResourceBundle stringResources;
    private Alert loginAlert;


    @FXML
    private JFXTextField loginTextField;

    @FXML
    private JFXTextField passwordTextField;


    public LoginPageController() {
        tokenHelper = new TokenHelper(ApiAuthorizationClient.getApi());
        loginAlert = new Alert(Alert.AlertType.ERROR);
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;
        setLoginAlertContent();
    }

    @FXML
    private void onClickLoginButton() throws IOException {

        userLogin = loginTextField.getText();
        userPassword = passwordTextField.getText();

        if (!userLogin.equals("") && !userPassword.equals("")) {
            RequestBody requestBodyGrantType = RequestBody.create(MediaType.parse("multipart/form-data"), "password");
            RequestBody requestBodyUsername = RequestBody.create(MediaType.parse("multipart/form-data"), userLogin);
            RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("multipart/form-data"), userPassword);

            tokenModel = tokenHelper.getAccessToken(requestBodyGrantType, requestBodyUsername, requestBodyPassword);


            if (tokenModel != null) {
                System.out.println("LOGIN TOKEN " + tokenModel.getAccessToken());
                TokenKeeper.setAccessToken(tokenModel.getAccessToken());
                TokenKeeper.setRefreshToken(tokenModel.getRefreshToken());

                screenController.setFxmlPath(ScreensProperty.MAIN_PAGE.getScreenPath());
                screenController.showScreen();

            } else
                loginAlert.showAndWait();

        }
    }

    private String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    private void setLoginAlertContent() {
        loginAlert.setTitle(stringResources.getString("loginAlertTitle"));
        loginAlert.setHeaderText(stringResources.getString("loginAlertHeaderText"));
        loginAlert.setContentText(stringResources.getString("loginAlertContentText"));
    }
}
