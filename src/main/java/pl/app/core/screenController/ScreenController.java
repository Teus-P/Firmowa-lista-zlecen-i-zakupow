package pl.app.core.screenController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import pl.app.core.property.ScreensProperty;
import pl.app.launch.LaunchApp;
import pl.app.utils.Utf8ResourceBundleControl;

import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ScreenController extends Parent {

    private static final ScreenController instance = new ScreenController();

    private static final Logger LOGGER = Logger.getLogger(ScreenController.class.getName());
    private static final Locale POLISH_LOCALE = new Locale("pl", "PL");

    private ScreensProperty screenProperty = null;
    private String fxmlPath = null;
    private String pageTitle = null;

    private ScreenController() {
    }


    private URL getFxmlUrl() {

        URL url = null;

        try {
            url = getClass().getClassLoader().getResource(fxmlPath);
            //url = LaunchApp.class.getResource(fxmlPath);
            LOGGER.info("FXML URL : " + url);
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

        return fxmlLoader;
    }

    private ResourceBundle getLanguageBundleResources(Locale locale) {

        Utf8ResourceBundleControl resourceBundleControl = new Utf8ResourceBundleControl();
        ResourceBundle resourceBundle = null;

        try {
            resourceBundle = resourceBundleControl.newBundle("bundles/language", locale, "properties", getClass().getClassLoader(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resourceBundle;
    }


    private Node loadNode() {

        FXMLLoader fxmlLoader = fxmlLoader();
        Node loadedNode = null;
        ControlledScreen myScreenController = null;

        try {
            loadedNode = fxmlLoader.load();
            myScreenController = fxmlLoader.getController();
            myScreenController.setScreenParent(this);

            return loadedNode;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "\nEXCEPTION INFO: " + ScreenController.class.getName()
                            + "\nEXCEPTION INFO: Controller doesn't exist! Controller : " + fxmlLoader.getController());
        } catch (NullPointerException e) {
            throw new NullPointerException(
                    "\nEXCEPTION INFO: " + ScreenController.class.getName()
                            + "\nEXCEPTION INFO: Maybe Controller class is not attach to fxml: " + fxmlLoader.getLocation().getPath());
        } catch (ClassCastException e) {
            throw new RuntimeException(
                    "\nEXCEPTION INFO: " + ScreenController.class.getName()
                            + "\nEXCEPTION INFO: Controller not implement ControlledScreen Interface : Controller " + fxmlLoader.getController().getClass());
        }
    }

    private void setScreen(Node screen, String title) {

        Node screenToRemove = null;

        if (screen != null) {

            primaryStageOperation(title);

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
            throw new RuntimeException("** Something gone wrong in set screen ** - " + ScreenController.class.getName());
        }
    }

    public void show() {

        if (screenProperty != null) {
            Node screen = loadNode();
            setScreen(screen, pageTitle);
            clearReference();
        } else {
            throw new NullPointerException("\nEXCEPTION INFO: " + ScreenController.class.getName()
                    + "\nEXCEPTION INFO: Can't call show() method before setScreenProperty() method");
        }
    }

    private void primaryStageOperation(String title) {
        LaunchApp.getPrimaryStage().setTitle(title);
    }

    public ScreenController setScreenProperty(ScreensProperty screenProperty) {
        this.screenProperty = screenProperty;
        this.fxmlPath = screenProperty.getScreenPath();
        this.pageTitle = screenProperty.getStageTitle();
        return this;
    }

    public static ScreenController getInstance() {
        return instance;
    }

    private void clearReference() {
        screenProperty = null;
        fxmlPath = null;
        pageTitle = null;
    }
}