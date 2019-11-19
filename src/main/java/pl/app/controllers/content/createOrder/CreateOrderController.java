package pl.app.controllers.content.createOrder;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.ProductHelper;
import pl.app.api.model.ProductModel;
import pl.app.controllers.ComboBoxInit;
import pl.app.controllers.content.adminPanel.listItems.ProductTableItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {

    private ResourceBundle stringResources;
    private ComboBoxInit comboBoxInit;
    private ProductHelper productHelper;
    private ObservableList<ProductTableItem> productTableItemObservableList;

    private Alert alert;
    boolean isProductOnTheList;
    List<ProductInTable> productInTableList;

    @FXML
    private JFXComboBox<ProductModel> productComboBox;

    @FXML
    private JFXTextField categoryTextField;

    @FXML
    private JFXTextField numberTextField;

    @FXML
    private JFXButton addNewProductToOrderButton;

    @FXML
    private TableView productTable;

    @FXML
    private TableColumn productNameColumn;

    @FXML
    private TableColumn categoryNameColumn;

    @FXML
    private TableColumn numberColumn;


    @FXML
    private JFXTreeTableView<ProductTableItem> productTableJFX;


    @Getter
    @Setter
    public class ProductInTable {
        String name;
        String category;
        String number;

        public ProductInTable(String name, String category, String number) {
            this.name = name;
            this.category = category;
            this.number = number;
        }
    }

    public CreateOrderController() {
        productHelper = new ProductHelper(ApiResourcesClient.getApi());
        productTableItemObservableList = FXCollections.observableArrayList();

        comboBoxInit = new ComboBoxInit();
        alert = new Alert(Alert.AlertType.ERROR);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;
        productInTableList = new ArrayList<ProductInTable>();

        setAlertContent();

        comboBoxInit.initProductComboBox(productComboBox);

        productNameColumn.setCellValueFactory(new PropertyValueFactory<ProductInTable, String>("name"));
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<ProductInTable, String>("category"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<ProductInTable, String>("number"));

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
        ProductModel selected = productModelJFXComboBox.getSelectionModel().getSelectedItem();

        categoryTextField.setText(selected.getCategories().getName());
    }

    public void addNewProductToList(ActionEvent event) {
        isProductOnTheList = false;

        if (productComboBox.getSelectionModel().getSelectedItem() != null && numberTextField.getText() != null) {
            productInTableList.forEach(product -> {
                if (product.getName() == productComboBox.getSelectionModel().getSelectedItem().getName()) {
                    alert.showAndWait();
                    isProductOnTheList = true;
                }
            });

            if (!isProductOnTheList) {
                ProductInTable productInTable = new ProductInTable(
                        productComboBox.getSelectionModel().getSelectedItem().getName(),
                        categoryTextField.getText(),
                        numberTextField.getText()
                );

                productInTableList.add(productInTable);

                productTable.getItems().add(productInTable);
            }

//            productInTableList.forEach(product -> System.out.println(product.getName()));
        }
    }

    private void setAlertContent() {
        alert.setTitle("Alert");
        alert.setHeaderText("Alert");
        alert.setContentText("Alert");
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
        productTableJFX.getColumns().setAll(productColumn, categoryColumn, unitColumn);
        productTableJFX.setRoot(root);
        productTableJFX.setShowRoot(false);


//        productSearchField.textProperty().addListener((observable, oldValue, newValue) ->
//                productTable.setPredicate(productTableItemTreeItem
//                        -> productTableItemTreeItem.getValue().getProduct().getValue().contains(newValue)
//                        || productTableItemTreeItem.getValue().getCategory().getValue().contains(newValue)
//                        || productTableItemTreeItem.getValue().getUnit().getValue().contains(newValue)));
//
//        productTable.setOnMouseClicked(click -> {
//            if (click.getClickCount() == 2) {
//                showEditProductDialog();
//            }
//        });

    }
}