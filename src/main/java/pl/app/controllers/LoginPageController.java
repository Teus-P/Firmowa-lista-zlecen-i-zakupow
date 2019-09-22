package pl.app.controllers;

import com.google.common.hash.Hashing;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pl.app.api.RestClient;
import pl.app.api.helpers.TokenHelper;
import pl.app.api.model.TokenModel;
import pl.app.core.LaunchApp;
import pl.app.utils.screenManager.ControlledScreen;
import pl.app.utils.screenManager.ScreenController;
import pl.app.utils.screenManager.ScreensProperty;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class LoginPageController implements ControlledScreen, Initializable {

    private ScreenController screenController;
    private TokenHelper tokenHelper;
    private TokenModel tokenModel;
    private String userLogin;
    private String userPassword;


    @FXML
    private JFXTextField loginTextField;

    @FXML
    private JFXTextField passwordTextField;


    public LoginPageController() {
        tokenHelper = new TokenHelper(RestClient.getApi());
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onClickLoginButton() throws IOException {

        userLogin = loginTextField.getText();
        userPassword = passwordTextField.getText();

        if (!userLogin.equals("") && !userPassword.equals("")) {

            tokenModel = tokenHelper.getToken(loginTextField.getText(), hashPassword(userPassword));
            System.out.println("LOGIN TOKEN " + tokenModel.getToken());
            RestClient.setToken(tokenModel.getToken());

            screenController.setFxmlPath(ScreensProperty.MAIN_PAGE.getScreenPath());
            screenController.showScreen();
        }
    }

    private String hashPassword(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }
}
