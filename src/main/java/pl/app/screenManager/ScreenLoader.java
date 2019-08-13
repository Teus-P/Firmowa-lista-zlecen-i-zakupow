package pl.app.screenManager;

import javafx.scene.Node;

public class ScreenLoader {

    private ScreenController mainContainer;
    private String screenFxmlPath;

    public ScreenLoader(ScreenController mainContainer, String screenFxmlPath) {
        this.mainContainer = mainContainer;
        this.screenFxmlPath = screenFxmlPath;
    }

    public Node loadScreen() {
        return mainContainer.loadScreen(screenFxmlPath);
    }

}
