package pl.app.controllers.pages;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import pl.app.core.baseComponent.BaseScreen;
import pl.app.core.property.ContentProperty;
import pl.app.core.screen.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;


public class MainPageController extends BaseScreen {

    @FXML
    private AnchorPane container;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton orderButton;

    @FXML
    private JFXButton currentOrdersButton;

    @FXML
    private JFXButton orderHistoryButton;

    @FXML
    private JFXButton profileButton;

    @FXML
    private JFXButton helpButton;

    @FXML
    private JFXButton logoutButton;

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

    }

    @FXML
    void homeButtonOnAction(ActionEvent event) {
    }

    @FXML
    void logoutButtonOnAction(ActionEvent event) {

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

        //adminButton.setVisible(false);


    }


}