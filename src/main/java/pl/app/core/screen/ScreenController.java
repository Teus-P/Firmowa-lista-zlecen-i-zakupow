package pl.app.core.screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import pl.app.core.property.StageProperty;
import pl.app.core.utils.ResourceLoader;
import pl.app.launch.LaunchApp;

import java.io.IOException;

public class ScreenController extends AnchorPane {

    private static final ScreenController instance = new ScreenController();

    private ResourceLoader resourceLoader = ResourceLoader.getInstance();

    private StageProperty stageProperty = null;
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

            setResponsiveScene(loadedNode);

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

    private void setResponsiveScene(Node loadedNode) {
        AnchorPane.setTopAnchor(loadedNode, 0.0);
        AnchorPane.setBottomAnchor(loadedNode, 0.0);
        AnchorPane.setLeftAnchor(loadedNode, 0.0);
        AnchorPane.setRightAnchor(loadedNode, 0.0);
    }

    private void setScreen(Node screen, String title) {

        if (screen != null) {

            primaryStageOperation(title);

            if (!getChildren().isEmpty()) {
                getChildren().clear();
                getChildren().add(screen);
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

        if (stageProperty != null) {
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

    public ScreenController setScreenProperty(StageProperty stageProperty) {
        this.stageProperty = stageProperty;
        this.fxmlPath = stageProperty.getFxmlPath();
        this.pageTitle = stageProperty.getStageTitle();
        return this;
    }

    public static ScreenController getInstance() {
        return instance;
    }

    private void clearReference() {
        stageProperty = null;
        fxmlPath = null;
        pageTitle = null;
    }
}