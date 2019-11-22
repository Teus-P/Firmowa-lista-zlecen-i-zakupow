package pl.app.controllers.content.createOrder;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.OrderHelper;
import pl.app.api.helpers.ProductHelper;
import pl.app.api.model.OrderProductModel;
import pl.app.api.model.ProductModel;
import pl.app.controllers.common.ProductsComboBoxInitializer;
import pl.app.controllers.common.listItems.OrderProductTableItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {

    private ResourceBundle stringResources;
    private ObservableList<OrderProductTableItem> productTableItemObservableList;

    private ProductHelper productHelper;
    private OrderHelper orderHelper;

    private Alert alert;
    boolean isProductOnTheList;

    @FXML
    private JFXComboBox<ProductModel> productComboBox;

    @FXML
    private JFXTextField categoryTextField;

    @FXML
    private JFXTextField numberTextField;

    @FXML
    private JFXTreeTableView<OrderProductTableItem> productTable;

    @FXML
    private JFXButton addNewProductToOrderButton;

    @FXML
    private JFXButton addNewOrderButton;

    @FXML
    private JFXButton deleteRowButton;

    public CreateOrderController() {
        productTableItemObservableList = FXCollections.observableArrayList();
        alert = new Alert(Alert.AlertType.NONE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;

        initHelpers();
        initProductTreeTableView();

        ProductsComboBoxInitializer.init(productComboBox, FXCollections.observableList(productHelper.getAllProducts()));

        //TODO move this code to separate method
        numberTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    numberTextField.setText(oldValue);
                }
            }
        });
    }

    public void productComboBoxActionPerformed(javafx.event.ActionEvent event) {
        JFXComboBox<ProductModel> productModelJFXComboBox = (JFXComboBox<ProductModel>) event.getSource();

        ProductModel productModel = productModelJFXComboBox.getSelectionModel().getSelectedItem();
        if (productModel != null) {
            categoryTextField.setText(productModelJFXComboBox.getSelectionModel().getSelectedItem().getCategories().getName());
        }
    }

    public void addNewProductToList(ActionEvent event) {
        isProductOnTheList = false;
        String quantity = numberTextField.getText();


        if (productComboBox.getSelectionModel().getSelectedItem() != null && quantity != null) {

            productTableItemObservableList.forEach(product -> {
                if (product.getOrderProductModel().getProduct().getName() == productComboBox.getSelectionModel().getSelectedItem().getName()) {
                    Integer newQuantityValue = product.getOrderProductModel().getQuantity() + Integer.valueOf(quantity);

                    product.getOrderProductModel().setQuantity(newQuantityValue);
                    product.setQuantity(new SimpleStringProperty(newQuantityValue.toString()));
                    isProductOnTheList = true;
                }
            });

            if (!isProductOnTheList) {
                OrderProductTableItem productInTable = new OrderProductTableItem(
                        productComboBox.getSelectionModel().getSelectedItem(),
                        Integer.valueOf(numberTextField.getText())
                );

                productTableItemObservableList.add(productInTable);


            }

            final TreeItem<OrderProductTableItem> root = new RecursiveTreeItem<>(productTableItemObservableList, RecursiveTreeObject::getChildren);
            productTable.setRoot(root);
        }

        productComboBox.getSelectionModel().clearSelection();
        categoryTextField.setText("Kategoria");
        numberTextField.clear();
    }

    public void addNewOrder(ActionEvent event) {
        if(productTableItemObservableList.size() != 0){
            List<OrderProductModel> orderProductModelList = new ArrayList<>();
            productTableItemObservableList.forEach(product -> {
                orderProductModelList.add(product.getOrderProductModel());
            });

            orderHelper.createNewOrder(orderProductModelList);
            productTableItemObservableList.clear();

            setAlertContent(Alert.AlertType.CONFIRMATION, "Złożono zamówienie", "Zamówienie zostało złożone.", "Złożone przez Ciebie zamówienie zostało wysłane i oczekuje na akceptacje.");
            alert.show();
        }else {
            setAlertContent(Alert.AlertType.ERROR,"Błędne zamówienie", "Zamówienie nie zostało złożone.", "Składane zamówienie musi zawierać listę produktów.");
            alert.show();
        }
    }

    private void initHelpers() {
        productHelper = new ProductHelper(ApiResourcesClient.getApi());
        orderHelper = new OrderHelper(ApiResourcesClient.getApi());
    }

    private void setAlertContent(Alert.AlertType alertType, String title, String header, String content) {
        alert.setAlertType(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
    }

    private void initProductTreeTableView() {
        JFXTreeTableColumn<OrderProductTableItem, String> productColumn = new JFXTreeTableColumn<>("Nazwa produktu");
        productColumn.setCellValueFactory(param -> param.getValue().getValue().getName());


        JFXTreeTableColumn<OrderProductTableItem, String> categoryColumn = new JFXTreeTableColumn<>("Kategoria");
        categoryColumn.setCellValueFactory(param -> param.getValue().getValue().getCategory());

        JFXTreeTableColumn<OrderProductTableItem, String> quantityColumn = new JFXTreeTableColumn<>("Liczba sztuk");
        quantityColumn.setCellValueFactory(param -> param.getValue().getValue().getQuantity());
        quantityColumn.setEditable(true);

        quantityColumn.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        quantityColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<OrderProductTableItem, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<OrderProductTableItem, String> event) {
                TreeItem<OrderProductTableItem> item = productTable.getTreeItem(event.getTreeTablePosition().getRow());

                if (!event.getNewValue().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    item.getValue().setQuantity(new SimpleStringProperty(event.getOldValue()));
                } else {
                    item.getValue().setQuantity(new SimpleStringProperty(event.getNewValue()));
                    item.getValue().getOrderProductModel().setQuantity(Integer.valueOf(event.getNewValue()));
                }

                productTable.refresh();
            }
        });


        final TreeItem<OrderProductTableItem> root = new RecursiveTreeItem<>(productTableItemObservableList, RecursiveTreeObject::getChildren);
        productTable.setEditable(true);
        productTable.getColumns().setAll(productColumn, categoryColumn, quantityColumn);
        productTable.setRoot(root);
        productTable.setShowRoot(false);
    }

    public void deleteRow(ActionEvent event) {
        TreeItem<OrderProductTableItem> selectedItem = productTable.getSelectionModel().getSelectedItem();

        for (OrderProductTableItem product : productTableItemObservableList) {
            if (product.getName() == selectedItem.getValue().getName()) {
                productTableItemObservableList.remove(product);
                break;
            }
        }
    }
}