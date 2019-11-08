package pl.app.controllers;

import javafx.fxml.Initializable;
import pl.app.core.screen.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class UnacceptedOrderFieldController implements Initializable/*, ControlledScreen*/ {

    private ScreenController screenController;
    private ResourceBundle stringResources;

    public UnacceptedOrderFieldController() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;
    }

/*    @Override
    public void onLoadNode(ScreenController screenPage) {
        this.screenController = screenPage;
    }*/
}