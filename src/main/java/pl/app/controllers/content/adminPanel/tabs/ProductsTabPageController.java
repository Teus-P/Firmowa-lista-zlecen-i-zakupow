package pl.app.controllers.content.adminPanel.tabs;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.ProductHelper;
import pl.app.controllers.common.popupDialogs.DeleteDialogInformation;
import pl.app.controllers.common.listItems.ProductTableItem;
import pl.app.controllers.content.adminPanel.dialog.EditProductDialogController;
import pl.app.controllers.content.adminPanel.dialog.NewProductDialogController;
import pl.app.core.dialog.DialogStage;
import pl.app.core.property.StageProperty;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.app.controllers.common.popupDialogs.WarningDialog.showWarningDialog;

public class ProductsTabPageController implements Initializable {

    private ObservableList<ProductTableItem> productTableItemObservableList;
    private ProductHelper productHelper;

    @FXML
    private JFXTextField productSearchField;

    @FXML
    private JFXTreeTableView<ProductTableItem> productTable;


    public ProductsTabPageController() {
        productHelper = new ProductHelper(ApiResourcesClient.getApi());
        productTableItemObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProductTreeTableView();
    }


    private void initProductTreeTableView() {
        productHelper.getAllProducts().forEach(model -> productTableItemObservableList.add(new ProductTableItem(model)));

        JFXTreeTableColumn<ProductTableItem, String> productColumn = new JFXTreeTableColumn<>("Nazwa produktu");
        productColumn.setCellValueFactory(param -> param.getValue().getValue().getProduct());

        JFXTreeTableColumn<ProductTableItem, String> categoryColumn = new JFXTreeTableColumn<>("Kategoria");
        categoryColumn.setCellValueFactory(param -> param.getValue().getValue().getCategory());

        JFXTreeTableColumn<ProductTableItem, String> unitColumn = new JFXTreeTableColumn<>("Jednostka");
        unitColumn.setCellValueFactory(param -> param.getValue().getValue().getUnit());


        final TreeItem<ProductTableItem> root = new RecursiveTreeItem<>(productTableItemObservableList, RecursiveTreeObject::getChildren);
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

    private void showEditProductDialog() {
        DialogStage editProductDialog = new DialogStage(StageProperty.EDIT_PRODUCT);
        EditProductDialogController controller = editProductDialog.getController();
        controller.initData(productTable.getSelectionModel().getSelectedItem().getValue().getProductModel());
        controller.setOnDialogCloseListener(() -> {
            productTableItemObservableList.clear();
            productHelper.getAllProducts().forEach(model -> productTableItemObservableList.add(new ProductTableItem(model)));
        });
        editProductDialog.showAndWait();
    }

    private void showNewProductDialog() {
        DialogStage newProductDialog = new DialogStage(StageProperty.NEW_PRODUCT);
        NewProductDialogController controller = newProductDialog.getController();
        controller.setOnDialogCloseListener(() -> {
            productTableItemObservableList.clear();
            productHelper.getAllProducts().forEach(model -> productTableItemObservableList.add(new ProductTableItem(model)));
        });
        newProductDialog.showAndWait();
    }

    @FXML
    void addProductOnAction(ActionEvent event) {
        showNewProductDialog();
    }

    @FXML
    void deleteProductOnAction(ActionEvent event) {

        if (productTable.getSelectionModel().getSelectedItem() != null) {

            var productModel = productTable.getSelectionModel().getSelectedItem().getValue().getProductModel();
            String title = "Uwaga!";
            String header = "Czy na pewno chcesz usunąć produkt?";
            String content = "Produkt o następujących danych zostanie usunięty:\n\n" +
                    "Nazwa produktu: " + productModel.getName() + "\n" +
                    "Kategoria produktu: " + productModel.getCategories().getName() + "\n" +
                    "Jednostka produktu: " + productModel.getUnit().getUnit();

            boolean dialogResult = DeleteDialogInformation.showDeleteDialogInformation(title, header, content);
            if (dialogResult) {
                productTable.getSelectionModel().getSelectedItem().getParent().getChildren().remove(productTable.getSelectionModel().getSelectedItem());
                productHelper.deleteProductById(productModel.getIdProduct());
            }

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


}
