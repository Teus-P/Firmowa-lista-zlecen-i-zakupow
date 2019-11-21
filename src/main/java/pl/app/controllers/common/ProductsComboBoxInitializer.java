package pl.app.controllers.common;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;
import pl.app.api.model.ProductModel;

public class ProductsComboBoxInitializer {

    public static void init(JFXComboBox<ProductModel> comboBox, ObservableList<ProductModel> observableList) {

        comboBox.setItems(observableList);
        TextFields.bindAutoCompletion(comboBox.getEditor(), comboBox.getEditor());

        comboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ProductModel> call(ListView<ProductModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(ProductModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }
}
