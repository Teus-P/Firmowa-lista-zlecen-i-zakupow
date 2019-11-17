package pl.app.controllers.common;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import pl.app.api.model.UnitModel;

public class UnitComboBoxInitializer {

    public static void init(JFXComboBox<UnitModel> comboBox, ObservableList<UnitModel> observableList) {

        comboBox.setItems(observableList);
        //TextFields.bindAutoCompletion(categoriesComboBox.getEditor(), categoriesComboBox.getItems());

        comboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<UnitModel> call(ListView<UnitModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(UnitModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getUnit());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }


}
