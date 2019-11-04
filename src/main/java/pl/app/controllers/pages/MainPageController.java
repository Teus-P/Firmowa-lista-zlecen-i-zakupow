package pl.app.controllers.pages;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pl.app.core.BaseScreen;
import pl.app.core.property.ContentProperty;
import pl.app.core.screen.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;


public class MainPageController extends BaseScreen {

    @FXML
    private Pane containerPane;

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
        getContentManager().buildContext(ContentProperty.CURRENT_ORDERS).attachTo(containerPane).build();
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
        getContentManager().buildContext(ContentProperty.CREATE_ORDER).attachTo(containerPane).build();
    }

    @FXML
    void orderHistoryButtonOnAction(ActionEvent event) {
        getContentManager().buildContext(ContentProperty.HISTORY_OF_ORDERS).attachTo(containerPane).build();
    }

    @FXML
    void profileButtonOnAction(ActionEvent event) {

    }

    @FXML
    void adminButtonOnAction(ActionEvent event) {

    }


    private void initUi() {

        adminButton.setVisible(false);

        Image homeButtonIc = new Image(getClass().getResourceAsStream("/drawable/2x/baseline_home_white_18dp.png"));
        homeButton.setGraphic(new ImageView(homeButtonIc));

        Image orderHistoryButtonIc = new Image(getClass().getResourceAsStream("/drawable/2x/baseline_library_books_white_18dp.png"));
        orderHistoryButton.setGraphic(new ImageView(orderHistoryButtonIc));

        Image orderButtonIc = new Image(getClass().getResourceAsStream("/drawable/2x/baseline_screen_share_white_18dp.png"));
        orderButton.setGraphic(new ImageView(orderButtonIc));

        Image currentOrdersButtonIc = new Image(getClass().getResourceAsStream("/drawable/2x/baseline_access_time_white_18dp.png"));
        currentOrdersButton.setGraphic(new ImageView(currentOrdersButtonIc));



    }


}