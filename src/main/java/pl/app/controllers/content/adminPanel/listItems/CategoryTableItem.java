package pl.app.controllers.content.adminPanel.listItems;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
import pl.app.api.model.CategoriesModel;

@Getter
@Setter
public class CategoryTableItem extends RecursiveTreeObject<CategoryTableItem> {

    private CategoriesModel categoriesModel;
    private StringProperty categoryName;


    public CategoryTableItem(CategoriesModel categoriesModel) {
        this.categoriesModel = categoriesModel;
        this.categoryName = new SimpleStringProperty(categoriesModel.getName());
    }
}
