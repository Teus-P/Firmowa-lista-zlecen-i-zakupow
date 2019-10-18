package pl.app.controllers;

import javafx.fxml.Initializable;
import pl.app.utils.screenManager.ControlledScreen;
import pl.app.utils.screenManager.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class CurrentOrdersController implements Initializable, ControlledScreen {

    private ScreenController screenController;
    private ResourceBundle stringResources;

    public CurrentOrdersController() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }
}