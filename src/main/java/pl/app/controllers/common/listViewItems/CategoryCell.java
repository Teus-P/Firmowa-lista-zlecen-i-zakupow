package pl.app.controllers.common.listViewItems;

import javafx.scene.control.ListCell;
import pl.app.api.model.CategoriesModel;

public class CategoryCell extends ListCell<CategoriesModel> {

    @Override
    protected void updateItem(CategoriesModel item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
            setGraphic(null);
        }


        if (item != null) {
            setText(item.getName());
        }
        setGraphic(null);


    }
}
