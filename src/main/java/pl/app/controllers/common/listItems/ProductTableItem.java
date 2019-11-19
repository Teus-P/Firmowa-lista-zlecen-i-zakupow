package pl.app.controllers.common.listItems;

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

    public ProductTableItem(ProductModel product) {
        this.productModel = product;
        this.productId = product.getIdProduct();
        this.categoriesModel = product.getCategories();
        this.unitModel = product.getUnit();

        this.product = new SimpleStringProperty(product.getName());
        this.category = new SimpleStringProperty(product.getCategories().getName());
        this.unit = new SimpleStringProperty(product.getUnit().getUnit());

    }
}
