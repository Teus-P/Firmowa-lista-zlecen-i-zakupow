package pl.app.controllers.content.adminPanel.listItems;

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

    private int productId;
    private StringProperty product;
    private StringProperty category;
    private StringProperty unit;
    private ProductModel productModel;
    private CategoriesModel categoriesModel;
    private UnitModel unitModel;

    public ProductTableItem(ProductModel product, CategoriesModel category, UnitModel unit) {
        this.product = new SimpleStringProperty(product.getName());
        this.category = new SimpleStringProperty(category.getName());
        this.unit = new SimpleStringProperty(unit.getUnit());
        this.productId = product.getIdProduct();
        this.productModel = product;
        this.categoriesModel = product.getCategories();
        this.unitModel = product.getUnit();
    }
}
