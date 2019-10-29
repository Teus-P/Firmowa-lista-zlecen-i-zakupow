package pl.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import pl.app.core.BaseScreen;
import pl.app.core.property.ContextProperty;


public class MainPageController extends BaseScreen {

    @FXML
    private AnchorPane containerAnchorPane;

    public MainPageController() {

    }

    @Override
    protected void onCreateBuildContext() {
        super.onCreateBuildContext();
        getContextManager().buildContext(ContextProperty.ADMIN_CONTEXT).injectIn(containerAnchorPane).build();
    }

    @FXML
    public void mainPageOnAction() {
        getContextManager().buildContext(ContextProperty.HISTORY_OF_ORDERS).injectIn(containerAnchorPane).build();
    }

    @FXML
    public void orderButtonOnAction() {
        getContextManager().buildContext(ContextProperty.ADMIN_CONTEXT).injectIn(containerAnchorPane).build();
    }

}