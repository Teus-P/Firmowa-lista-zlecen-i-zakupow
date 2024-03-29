package pl.app.core.baseComponent;

import javafx.fxml.Initializable;
import lombok.Getter;
import pl.app.core.screen.ControlledScreen;
import pl.app.core.screen.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BasePage implements ControlledScreen, Initializable {

    @Getter
    protected ScreenController screenController;
    @Getter
    protected ResourceBundle resourceBundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
    }

    @Override
    public void onLoadNode(ScreenController screenPage) {
        this.screenController = screenPage;
    }


}
