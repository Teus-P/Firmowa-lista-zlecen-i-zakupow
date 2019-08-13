package pl.app.screenManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import pl.app.main.LaunchApp;

public class ScreenController extends Parent {

    public ScreenController() {
        super();
    }

    public Node loadScreen(String path) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent loadScreen = fxmlLoader.load();

            ControlledScreen myScreenController = fxmlLoader.getController();
            myScreenController.setScreenParent(this);

            return loadScreen;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setScreen(Node screen, double width, double height, String title) {

        Node screenToRemove;

        if (screen != null) {

            primaryStageOperation(width, height, title);

            if (!getChildren().isEmpty()) {
                getChildren().add(0, screen);
                screenToRemove = getChildren().get(1);
                getChildren().remove(screenToRemove);
            } else {
                getChildren().add(screen);
            }
        } else {
            throw new RuntimeException("Cos sie popsulo i nie bylo mnie slychac w ScreenController Node jest nullem");
        }
    }


    private void primaryStageOperation(double width, double height, String title) {
        LaunchApp.getPrimaryStage().setWidth(width);
        LaunchApp.getPrimaryStage().setHeight(height);
        LaunchApp.getPrimaryStage().setTitle(title);

    }


}