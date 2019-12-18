package pl.app.controllers.content;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import pl.app.api.UserSession;
import pl.app.core.baseComponent.BaseScreen;
import pl.app.core.property.ContentProperty;
import pl.app.core.property.StageProperty;
import pl.app.core.screen.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends BaseScreen {

    @FXML
    private AnchorPane container;

    @FXML
    private JFXButton adminButton;

    public MainPageController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

    }


    @Override
    public void onLoadNode(ScreenController screenPage) {
        super.onLoadNode(screenPage);
        initUi();
        getContentManager().buildContext(ContentProperty.HOME_PAGE).attachTo(container).build();
    }

    @Override
    protected void onCreateBuildContext() {
        super.onCreateBuildContext();

    }

    @FXML
    void currentOrdersButtonOnAction(ActionEvent event) {
        getContentManager().buildContext(ContentProperty.CURRENT_ORDERS).attachTo(container).build();
    }

    @FXML
    void helpButtonOnAction(ActionEvent event) {
        getContentManager().buildContext(ContentProperty.HELP_SCREEN).attachTo(container).build();
    }

    @FXML
    void homeButtonOnAction(ActionEvent event) {
        getContentManager().buildContext(ContentProperty.HOME_PAGE).attachTo(container).build();
    }

    @FXML
    void logoutButtonOnAction(ActionEvent event) {
        UserSession.clearSession();
        super.screenController.setScreenProperty(StageProperty.LOGIN_PAGE).show();
    }

    @FXML
    void orderButtonOnAction(ActionEvent event) {
        getContentManager().buildContext(ContentProperty.CREATE_ORDER).attachTo(container).build();
    }

    @FXML
    void orderHistoryButtonOnAction(ActionEvent event) {
        getContentManager().buildContext(ContentProperty.HISTORY_OF_ORDERS).attachTo(container).build();
    }

    @FXML
    void profileButtonOnAction(ActionEvent event) {
        getContentManager().buildContext(ContentProperty.USER_PANEL).attachTo(container).build();
    }

    @FXML
    void adminButtonOnAction(ActionEvent event) {
        getContentManager().buildContext(ContentProperty.ADMIN_CONTEXT).attachTo(container).build();
    }


    private void initUi() {

        if (UserSession.getLoggedUser().getUserAccountTypeModels().stream().anyMatch(item -> item.getName().equals("Role_RECIPIENT")
                || item.getName().equals("Role_ADMIN"))) {
            adminButton.setVisible(true);
        } else {
            adminButton.setVisible(false);
        }

    }

}