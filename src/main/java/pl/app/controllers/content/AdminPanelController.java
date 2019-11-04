package pl.app.controllers.content;

import javafx.fxml.Initializable;
import pl.app.core.screen.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {

    private ResourceBundle stringResources;

    public AdminPanelController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;
    }

}