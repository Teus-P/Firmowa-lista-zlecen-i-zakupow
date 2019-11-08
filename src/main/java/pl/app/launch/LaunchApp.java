package pl.app.launch;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.app.core.property.ScreensProperty;
import pl.app.core.screen.ScreenController;


public class LaunchApp extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        setPrimaryStage(primaryStage);

        primaryStage.setTitle("Firmowa lista zleceń i zakupów");

        ScreenController screenController = ScreenController.getInstance();

        screenController.setScreenProperty(ScreensProperty.LOGIN_PAGE).show();

        Scene scene = new Scene(screenController);

        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(770);
        primaryStage.setScene(scene);
        primaryStage.show();


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
