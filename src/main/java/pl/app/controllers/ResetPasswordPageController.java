package pl.app.controllers;

import javafx.fxml.Initializable;
import pl.app.core.screen.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class ResetPasswordPageController implements Initializable/*, ControlledScreen*/ {

    private ScreenController screenController;
    private ResourceBundle stringResources;

    public ResetPasswordPageController() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;
    }

   /* @Override
    public void onLoadNode(ScreenController screenPage) {
        this.screenController = screenPage;
    }*/
}