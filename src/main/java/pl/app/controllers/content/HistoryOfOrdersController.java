package pl.app.controllers.content;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;
import pl.app.api.model.ProductModel;
import pl.app.controllers.ComboBoxInit;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {

    private ResourceBundle stringResources;
    private ComboBoxInit comboBoxInit;

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


    @Getter
    @Setter
    private class ProductInTable {
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
        comboBoxInit = new ComboBoxInit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;

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
        //TODO check how to add element to table
        if(productComboBox.getSelectionModel().getSelectedItem() != null && numberTextField.getText() != null){
//            productNameColumn.setCellValueFactory(new PropertyValueFactory<>(productComboBox.getSelectionModel().getSelectedItem().getName()));
//            categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>(categoryTextField.getText()));
//            numberColumn.setCellValueFactory(new PropertyValueFactory<>(numberTextField.getText()));

//            productNameColumn.getColumns().add(productComboBox.getSelectionModel().getSelectedItem().getName());
//            categoryNameColumn.getColumns().add(categoryTextField.getText());
//            numberColumn.getColumns().add(numberTextField.getText());

            ProductInTable productInTable = new ProductInTable(
                    productComboBox.getSelectionModel().getSelectedItem().getName(),
                    categoryTextField.getText(),
                    numberTextField.getText());

            productTable.getItems().add(productInTable);
        }
    }
}