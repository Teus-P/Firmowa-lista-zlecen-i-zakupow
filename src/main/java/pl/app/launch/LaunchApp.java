package pl.app.launch;

import javafx.application.Application;

import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.app.core.screen.ScreenController;
import pl.app.core.property.ScreensProperty;


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
