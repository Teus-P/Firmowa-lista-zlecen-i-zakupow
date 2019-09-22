package pl.app.controllers;

import javafx.fxml.Initializable;
import pl.app.utils.screenManager.ControlledScreen;
import pl.app.utils.screenManager.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, ControlledScreen {

    private ScreenController screenController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        screenController = screenPage;
    }
}
