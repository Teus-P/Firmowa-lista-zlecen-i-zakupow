package pl.app.core;

import javafx.application.Application;

import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.app.api.RestClient;
import pl.app.api.helpers.TokenHelper;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.api.model.TokenModel;
import pl.app.api.model.UserAccountModel;
import pl.app.utils.screenManager.ScreenController;
import pl.app.utils.screenManager.ScreensProperty;


import java.util.List;

//TODO
//     - dodać hasowanie hasła po stronie clienta + odbiór dehash hasła po stronie serwera

public class LaunchApp extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        setPrimaryStage(primaryStage);


        apiInitTest();


        ScreenController screenController = new ScreenController(ScreensProperty.LOGIN_PAGE.getScreenPath());

        screenController.showScreen();


        Group root = new Group();
        root.getChildren().addAll(screenController);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(e ->
        {
            e.consume();

            primaryStage.close();
        });

    }

    private void apiInitTest() {
        try {
            List<UserAccountModel> userAccountModelList;
            TokenHelper tokenHelper = new TokenHelper(RestClient.getApi());
            TokenModel tokenModel = tokenHelper.getToken("admin", "admin");
            System.out.println(tokenModel.getToken());
            RestClient.setToken(tokenModel.getToken());
            UserAccountHelper userAccountHelper = new UserAccountHelper(RestClient.getApi());
            userAccountModelList = userAccountHelper.getAllUsers();
            System.out.println(userAccountModelList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static void setPrimaryStage(Stage primaryStage) {
        LaunchApp.primaryStage = primaryStage;
    }

}
