package pl.app.controllers;

import pl.app.utils.screenManager.ControlledScreen;
import pl.app.utils.screenManager.ScreenController;

public class LoginPageController implements ControlledScreen {

    private ScreenController screenController;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }
}
