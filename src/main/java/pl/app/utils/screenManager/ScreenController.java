package pl.app.utils.screenManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import pl.app.core.LaunchApp;

import java.io.IOException;
import java.net.URL;

public class ScreenController extends Parent {

    private String fxmlPath;


    public ScreenController(String fxmlPath) {
        super();
        this.fxmlPath = fxmlPath;
    }


    private URL getFxmlUrl() {

        URL url = null;

        try {
            url = getClass().getClassLoader().getResource(fxmlPath);
            if (url == null) {
                throw new NullPointerException("** File with " + fxmlPath + " path doesn't exist **");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return url;
    }


    private FXMLLoader fxmlLoader() {

        return new FXMLLoader(getFxmlUrl());
    }


    private Node loadNode() {

        FXMLLoader fxmlLoader = fxmlLoader();

        try {
            Node loadedNode = fxmlLoader.load();

            ControlledScreen myScreenController = fxmlLoader.getController();

            myScreenController.setScreenParent(this);

            return loadedNode;
        } catch (IOException e) {
            throw new RuntimeException("** Something gone wrong in Node loader **");
        }
    }

    private void setScreen(Node screen/*double width, double height, String title*/) {

        Node screenToRemove;

        if (screen != null) {

            // primaryStageOperation(width, height, title);

            if (!getChildren().isEmpty()) {
                getChildren().add(0, screen);
                screenToRemove = getChildren().get(1);
                getChildren().remove(screenToRemove);
            } else {
                getChildren().add(screen);
            }
        } else {
            throw new RuntimeException("** Something gone wrong in set screen **");
        }
    }

    public void showScreen() {
        Node screen = loadNode();
        setScreen(screen);
    }


    private void primaryStageOperation(double width, double height, String title) {
        LaunchApp.getPrimaryStage().setWidth(width);
        LaunchApp.getPrimaryStage().setHeight(height);
        LaunchApp.getPrimaryStage().setTitle(title);

    }

}