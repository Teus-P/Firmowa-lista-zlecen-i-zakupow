package pl.app.controllers.content.adminPanel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.*;
import pl.app.core.baseComponent.BaseScreen;
import pl.app.core.property.ContentProperty;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanelController extends BaseScreen {

    private ResourceBundle stringResources;

    @FXML
    private AnchorPane usersTabContainer;

    @FXML
    private AnchorPane productsTabContainer;

    @FXML
    private AnchorPane ordersTabContainer;

    @FXML
    private AnchorPane unitsTabContainer;

    @FXML
    private AnchorPane categoriesTabContainer;

    @FXML
    private Tab usersTab;

    @FXML
    private Tab ordersTab;

    @FXML
    private Tab resourcesTab;

    public AdminPanelController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;

        initUsersTab();


        usersTab.setOnSelectionChanged(e -> {
            if (usersTab.isSelected()) {
                initUsersTab();
            }
        });

        ordersTab.setOnSelectionChanged(e -> {
            if (ordersTab.isSelected()) {
                initOrdersTab();
            }
        });

        resourcesTab.setOnSelectionChanged(e -> {
            if (resourcesTab.isSelected()) {
                initProductsTab();
                initUnitsTab();
                initCategoriesTab();
            }
        });

    }


    private void initUsersTab() {
        usersTabContainer.getChildren().clear();
        getContentManager().buildContext(ContentProperty.USERS_TAB_PAGE).attachTo(usersTabContainer).build();
    }

    private void initProductsTab() {
        productsTabContainer.getChildren().clear();
        getContentManager().buildContext(ContentProperty.PRODUCTS_TAB_PAGE).attachTo(productsTabContainer).build();
    }

    private void initOrdersTab() {
        ordersTabContainer.getChildren().clear();
        getContentManager().buildContext(ContentProperty.ORDERS_TAB_PAGE).attachTo(ordersTabContainer).build();
    }

    private void initUnitsTab() {
        unitsTabContainer.getChildren().clear();
        getContentManager().buildContext(ContentProperty.UNITS_TAB_PAGE).attachTo(unitsTabContainer).build();
    }

    private void initCategoriesTab() {
        categoriesTabContainer.getChildren().clear();
        getContentManager().buildContext(ContentProperty.CATEGORIES_TAB_PAGE).attachTo(categoriesTabContainer).build();
    }


}