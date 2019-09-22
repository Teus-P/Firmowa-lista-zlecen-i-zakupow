package pl.app.utils.screenManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import pl.app.core.LaunchApp;
import pl.app.utils.Utf8ResourceBundleControl;

import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ScreenController extends Parent {

    private String fxmlPath;

    private static final Locale POLISH_LOCALE = new Locale("pl", "PL");


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

        FXMLLoader fxmlLoader = new FXMLLoader();
        ResourceBundle resourceBundle = getLanguageBundleResources(POLISH_LOCALE);

        fxmlLoader.setLocation(getFxmlUrl());
        fxmlLoader.setResources(resourceBundle);
        //fxmlLoader.setResources(ResourceBundle.getBundle("bundles/language_pl_PL", new Locale("pl", "PL")));

        return fxmlLoader;
    }

    private ResourceBundle getLanguageBundleResources(Locale locale) {
        ResourceBundle resourceBundle = null;
        Utf8ResourceBundleControl resourceBundleControl = new Utf8ResourceBundleControl();
        try {
            resourceBundle = resourceBundleControl.newBundle("bundles/language", locale, "properties", getClass().getClassLoader(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resourceBundle;
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

    private void setScreen(Node screen/*, String title*/) {

        Node screenToRemove = null;

        if (screen != null) {

            // primaryStageOperation(title);


            if (!getChildren().isEmpty()) {
                getChildren().add(0, screen);
                screenToRemove = getChildren().get(1);
                getChildren().remove(screenToRemove);
                LaunchApp.getPrimaryStage().sizeToScene();
            } else {
                getChildren().add(screen);
                LaunchApp.getPrimaryStage().sizeToScene();
            }
        } else {
            throw new RuntimeException("** Something gone wrong in set screen **");
        }
    }

    public void showScreen() {
        Node screen = loadNode();
        setScreen(screen);
    }


    private void primaryStageOperation(String title) {
        LaunchApp.getPrimaryStage().setTitle(title);

    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public void setFxmlPath(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }
}