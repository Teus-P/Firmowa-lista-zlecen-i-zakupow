package pl.app.core.screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import pl.app.core.ResourceLoader;
import pl.app.core.property.ScreensProperty;
import pl.app.launch.LaunchApp;

import java.io.*;

public class ScreenController extends AnchorPane {

    private static final ScreenController instance = new ScreenController();

    private ResourceLoader resourceLoader = ResourceLoader.getInstance();

    private ScreensProperty screenProperty = null;
    private String fxmlPath = null;
    private String pageTitle = null;

    private ScreenController() {
    }


    private Node loadNode(String fxmlPath) {

        FXMLLoader fxmlLoader = resourceLoader.fxmlLoader(fxmlPath);
        Node loadedNode = null;
        ControlledScreen myScreenController = null;

        try {
            loadedNode = fxmlLoader.load();

            AnchorPane.setTopAnchor(loadedNode, 0.0);
            AnchorPane.setBottomAnchor(loadedNode, 0.0);
            AnchorPane.setLeftAnchor(loadedNode, 0.0);
            AnchorPane.setRightAnchor(loadedNode, 0.0);

            myScreenController = fxmlLoader.getController();
            myScreenController.onLoadNode(this);
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

        if (screen != null) {

            primaryStageOperation(title);

            if (!getChildren().isEmpty()) {
                getChildren().clear();
                getChildren().add(screen);
                LaunchApp.getPrimaryStage().sizeToScene();
                //LaunchApp.getPrimaryStage().setMinHeight(screen.getScene().getHeight());
                //LaunchApp.getPrimaryStage().setMinWidth(screen.getScene().getWidth());
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
            Node screen = loadNode(fxmlPath);
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