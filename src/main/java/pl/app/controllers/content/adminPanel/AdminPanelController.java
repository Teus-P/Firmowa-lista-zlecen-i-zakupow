package pl.app.controllers.content.adminPanel;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.helpers.ProductHelper;
import pl.app.api.helpers.UnitHelper;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.api.model.*;
import pl.app.controllers.content.adminPanel.dialog.EditUserDialog;
import pl.app.controllers.content.adminPanel.dialog.NewProductDialog;
import pl.app.controllers.content.adminPanel.dialog.NewUserDialog;
import pl.app.core.ResourceLoader;
import pl.app.launch.LaunchApp;


import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AdminPanelController implements Initializable {

    private ResourceBundle stringResources;
    private ProductHelper productHelper;
    private UserAccountHelper userAccountHelper;
    private List<CategoriesModel> categoriesModelsList;
    private List<UnitModel> unitModelList;
    private ObservableList<UserAccountModel> userAccountModelObservableList;
    private NewUserDialog newUserDialog;
    private NewProductDialog newProductDialog;
    private ResourceLoader resourceLoader = ResourceLoader.getInstance();
    private ObservableList<ProductTableItem> productModelObservableList;

    //user tab

    @FXML
    private JFXListView<UserAccountModel> usersListView;

    //end section

    //Product tab

    @FXML
    private JFXTextField searchField;

    @FXML
    private JFXTreeTableView<ProductTableItem> productTable;

    //end section


    public AdminPanelController() {

        productHelper = new ProductHelper(ApiResourcesClient.getApi());
        userAccountHelper = new UserAccountHelper(ApiResourcesClient.getApi());
        userAccountModelObservableList = FXCollections.observableArrayList();
        productModelObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;

        userAccountModelObservableList.addAll(userAccountHelper.getAllUsers());

        productHelper.getAllProducts().forEach(model -> productModelObservableList.add(new ProductTableItem(model, model.getCategories(), model.getUnit())));

        initUserAccountListView();
        initProductListView();

    }

    private void initUserAccountListView() {
        usersListView.setItems(userAccountModelObservableList);
        usersListView.setCellFactory(factory -> new UserAccountViewCell());


        usersListView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                setPrimaryStageBlurEffect();
                var editUserDialog = new EditUserDialog(usersListView.getSelectionModel().getSelectedItem());
                editUserDialog.showAndWait();
            }
        });
    }

    private void initProductListView() {
        JFXTreeTableColumn<ProductTableItem, String> productColumn = new JFXTreeTableColumn<>("Nazwa produktu");
        productColumn.setCellValueFactory(param -> param.getValue().getValue().getProduct());

        JFXTreeTableColumn<ProductTableItem, String> categoryColumn = new JFXTreeTableColumn<>("Kategoria");
        categoryColumn.setCellValueFactory(param -> param.getValue().getValue().getCategory());

        JFXTreeTableColumn<ProductTableItem, String> unitColumn = new JFXTreeTableColumn<>("Jednostka");
        unitColumn.setCellValueFactory(param -> param.getValue().getValue().getUnit());


        final TreeItem<ProductTableItem> root = new RecursiveTreeItem<>(productModelObservableList, RecursiveTreeObject::getChildren);
        productTable.getColumns().setAll(productColumn, categoryColumn, unitColumn);
        productTable.setRoot(root);
        productTable.setShowRoot(false);


        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                productTable.setPredicate(productTableItemTreeItem
                        -> productTableItemTreeItem.getValue().getProduct().getValue().contains(newValue)
                        || productTableItemTreeItem.getValue().getCategory().getValue().contains(newValue)
                        || productTableItemTreeItem.getValue().getUnit().getValue().contains(newValue)));
    }


    @FXML
    void addProductOnAction(ActionEvent event) {
        setPrimaryStageBlurEffect();
        newProductDialog = new NewProductDialog();
        newProductDialog.showAndWait();

    }


    @FXML
    void addNewUserOnAction(ActionEvent event) {
        setPrimaryStageBlurEffect();
        newUserDialog = new NewUserDialog();
        newUserDialog.showAndWait();
    }


    @FXML
    void editUserOnAction(ActionEvent event) {

        if (usersListView.getSelectionModel().getSelectedItem() != null) {
            setPrimaryStageBlurEffect();
            var editUserDialog = new EditUserDialog(usersListView.getSelectionModel().getSelectedItem());
            editUserDialog.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Informacja");
            alert.setHeaderText("Proszę wybrać użytkownika do edycji");
            alert.showAndWait();
        }

    }


    private void setPrimaryStageBlurEffect() {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        LaunchApp.getPrimaryStage().getScene().getRoot().setEffect(blur);
    }


}