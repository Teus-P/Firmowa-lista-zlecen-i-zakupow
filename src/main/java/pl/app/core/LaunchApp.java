package pl.app.core;

import javafx.application.Application;

import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.app.utils.screenManager.ScreenController;
import pl.app.utils.screenManager.ScreensProperty;


public class LaunchApp extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        setPrimaryStage(primaryStage);


        ScreenController screenController = new ScreenController(ScreensProperty.LOGIN_PAGE.getScreenPath());
        screenController.showScreen();


        Group root = new Group();
        root.getChildren().addAll(screenController);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


        //primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(e ->
        {
            e.consume();

            primaryStage.close();
        });

    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static void setPrimaryStage(Stage primaryStage) {
        LaunchApp.primaryStage = primaryStage;
    }

}
