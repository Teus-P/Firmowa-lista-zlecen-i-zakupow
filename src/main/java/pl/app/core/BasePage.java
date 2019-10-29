package pl.app.core;

import javafx.fxml.Initializable;
import lombok.Getter;
import pl.app.core.screenController.ControlledScreen;
import pl.app.core.screenController.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BasePage implements ControlledScreen, Initializable {

    @Getter
    protected ScreenController screenController;
    @Getter
    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
    }

    @Override
    public void onLoadNode(ScreenController screenPage) {
        this.screenController = screenPage;
    }


}
