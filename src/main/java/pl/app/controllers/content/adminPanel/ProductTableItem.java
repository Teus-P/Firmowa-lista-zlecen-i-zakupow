package pl.app.controllers.content.adminPanel;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ProductModel;
import pl.app.api.model.UnitModel;

@Getter
@Setter
public class ProductTableItem extends RecursiveTreeObject<ProductTableItem> {

    private StringProperty product;
    private StringProperty category;
    private StringProperty unit;


    public ProductTableItem(ProductModel product, CategoriesModel category, UnitModel unit) {
        this.product = new SimpleStringProperty(product.getName());
        this.category = new SimpleStringProperty(category.getName());
        this.unit = new SimpleStringProperty(unit.getUnit());
    }
}
