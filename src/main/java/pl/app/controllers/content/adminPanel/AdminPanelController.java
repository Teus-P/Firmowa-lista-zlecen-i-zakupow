package pl.app.controllers.content.adminPanel;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.ProductHelper;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.api.model.*;
import pl.app.controllers.content.adminPanel.dialog.*;
import pl.app.controllers.content.adminPanel.listItems.ProductTableItem;
import pl.app.core.dialog.DialogStage;
import pl.app.core.dialog.OnDialogCloseListener;
import pl.app.core.property.DialogProperty;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {

    private ResourceBundle stringResources;
    private ProductHelper productHelper;
    private UserAccountHelper userAccountHelper;
    private ObservableList<UserAccountModel> userAccountModelObservableList;
    private ObservableList<ProductTableItem> productModelObservableList;
    private DialogStage newUserDialog;
    private DialogStage editProductDialog;
    private DialogStage editUserDialog;
    private DialogStage newProductDialog;

    //user tab

    @FXML
    private JFXListView<UserAccountModel> usersListView;

    //end section

    //Product tab

    @FXML
    private JFXTextField productSearchField;

    @FXML
    private JFXTreeTableView<ProductTableItem> productTable;

    //end section

    //Unit tab

    @FXML
    private JFXTextField unitSearchField;

    @FXML
    private JFXTreeTableView<?> unitTable;

    //end section

    //category tab

    @FXML
    private JFXTextField categorySearchField;

    @FXML
    private JFXTreeTableView<?> categoryTable;

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
        initDialogs();
    }

    @FXML
    void addProductOnAction(ActionEvent event) {
        newProductDialog.showAndWait();
    }

    @FXML
    void addNewUserOnAction(ActionEvent event) {
        newUserDialog.showAndWait();
    }

    @FXML
    void editUserOnAction(ActionEvent event) {

        if (usersListView.getSelectionModel().getSelectedItem() != null) {

            showEditUserDialog();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Informacja");
            alert.setHeaderText("Proszę wybrać użytkownika do edycji");
            alert.showAndWait();
        }
    }

    @FXML
    void addNewUnitOnAction(ActionEvent event) {
    }

    @FXML
    void addCategoryOnAction(ActionEvent event) {

    }

    @FXML
    void deleteProductOnAction(ActionEvent event) {

        if (productTable.getSelectionModel().getSelectedItem() != null) {
            showDeleteDialogInformation();
        } else {
            String header = "Uwaga";
            String contentText = "Proszę wybrać produkt do usunięcia";
            showWarningDialog(header, contentText);
        }
    }

    @FXML
    void editProductOnAction(ActionEvent event) {
        if (productTable.getSelectionModel().getSelectedItem() != null) {

            showEditProductDialog();

        } else {
            String header = "Uwaga";
            String contentText = "Proszę wybrać produkt do edycji";
            showWarningDialog(header, contentText);
        }

    }

    private void initUserAccountListView() {
        usersListView.setItems(userAccountModelObservableList);
        usersListView.setCellFactory(factory -> new UserAccountViewCell());


        usersListView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                showEditUserDialog();
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


        productSearchField.textProperty().addListener((observable, oldValue, newValue) ->
                productTable.setPredicate(productTableItemTreeItem
                        -> productTableItemTreeItem.getValue().getProduct().getValue().contains(newValue)
                        || productTableItemTreeItem.getValue().getCategory().getValue().contains(newValue)
                        || productTableItemTreeItem.getValue().getUnit().getValue().contains(newValue)));

        productTable.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                showEditProductDialog();
            }
        });

    }

    private void showEditUserDialog() {
        EditUserDialogController controller = editUserDialog.getController();
        controller.initData(usersListView.getSelectionModel().getSelectedItem());
        editUserDialog.showAndWait();
    }

    private void showEditProductDialog() {
        EditEditProductDialogController controller = editProductDialog.getController();
        controller.initData(productTable.getSelectionModel().getSelectedItem().getValue().getProductModel());
        controller.setOnDialogCloseListener(() -> {
            productModelObservableList.clear();
            productHelper.getAllProducts().forEach(model -> productModelObservableList.add(new ProductTableItem(model, model.getCategories(), model.getUnit())));
        });
        editProductDialog.showAndWait();
    }

    private void showWarningDialog(String headerText, String contentText) {
        var warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setHeaderText(headerText);
        warningAlert.setContentText(contentText);
        warningAlert.showAndWait();
    }

    private void showDeleteDialogInformation() {

        var delete = new ButtonType("Usuń");
        var cancel = new ButtonType("Anuluj");
        var productModel = productTable.getSelectionModel().getSelectedItem().getValue().getProductModel();
        var alert = new Alert(Alert.AlertType.NONE, "", delete, cancel);
        alert.setTitle("Uwaga!");
        alert.setHeaderText("Czy na pewno chcesz usunąć produkt?");
        alert.setContentText("Produkt o następujących danych zostanie usunięty:\n\n" +
                "Nazwa produktu: " + productModel.getName() + "\n" +
                "Kategoria produktu: " + productModel.getCategories().getName() + "\n" +
                "Jednostka produktu: " + productModel.getUnit().getUnit());
        alert.showAndWait().ifPresent(response -> {
            if (response == delete) {
                productTable.getSelectionModel().getSelectedItem().getParent().getChildren().remove(productTable.getSelectionModel().getSelectedItem());
                productHelper.deleteProductById(productModel.getIdProduct());
            }
        });

    }

    private void initDialogs() {
        newUserDialog = new DialogStage(DialogProperty.NEW_USER);
        editProductDialog = new DialogStage(DialogProperty.EDIT_PRODUCT);
        editUserDialog = new DialogStage(DialogProperty.EDIT_USER);
        newProductDialog = new DialogStage(DialogProperty.NEW_PRODUCT);
    }

}