package pl.app.controllers.common;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import pl.app.api.model.CategoriesModel;

public class CategoriesComboBoxInitializer {


    public static void init(JFXComboBox<CategoriesModel> comboBox, ObservableList<CategoriesModel> observableList) {

        comboBox.setItems(observableList);
        //TextFields.bindAutoCompletion(categoriesComboBox.getEditor(), categoriesComboBox.getItems());

        comboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<CategoriesModel> call(ListView<CategoriesModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(CategoriesModel item, boolean empty) {
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
