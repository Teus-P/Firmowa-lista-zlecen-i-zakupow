package pl.app.controllers;

import javafx.fxml.Initializable;
import pl.app.core.screenController.ControlledScreen;
import pl.app.core.screenController.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductInFieldInUnacceptedOrderController implements Initializable, ControlledScreen {

    private ScreenController screenController;
    private ResourceBundle stringResources;

    public ProductInFieldInUnacceptedOrderController() {

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