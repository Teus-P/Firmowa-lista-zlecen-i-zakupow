package pl.app.screenManager;

import javafx.scene.Node;

/**
 * wyswietlanie fxmla
 */
public class ScreenSetter {

    private ScreenController mainContainer;
    private Node nodeScreen;
    private double width;
    private double height;
    private String title;

    public ScreenSetter(ScreenController mainContainer, Node nodeScreen) {
        this.mainContainer = mainContainer;
        this.nodeScreen = nodeScreen;
    }

    private void setScreen(double height, double width, String title) {

        mainContainer.setScreen(nodeScreen, width, height, title);
    }


    /**
     * wyswietl main.fxml
     */
    public void homeScreen() {

        height = ScreensProperty.HOME_SCREEN.getHeight();
        width = ScreensProperty.HOME_SCREEN.getWidth();
        title = ScreensProperty.HOME_SCREEN.getTitle();

        setScreen(height, width, title);
    }


}

