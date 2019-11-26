package pl.app.controllers.common.listItems;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
import pl.app.api.model.OrderProductModel;
import pl.app.api.model.ProductModel;

@Getter
@Setter
public class OrderProductTableItem extends RecursiveTreeObject<OrderProductTableItem> {

    private OrderProductModel orderProductModel;

    private StringProperty productName;
    private StringProperty categoryName;
    private StringProperty productQuantity;
    private StringProperty unitName;

    public OrderProductTableItem(OrderProductModel orderProductModel) {
        this.orderProductModel = orderProductModel;

        this.productName = new SimpleStringProperty(orderProductModel.getProduct().getName());
        this.categoryName = new SimpleStringProperty(orderProductModel.getProduct().getCategories().getName());
        this.productQuantity = new SimpleStringProperty(orderProductModel.getQuantity().toString());
        this.unitName = new SimpleStringProperty(orderProductModel.getProduct().getUnit().getUnit());
    }

    public OrderProductTableItem(ProductModel productModel, Integer productQuantity) {
        this.orderProductModel = new OrderProductModel();
        this.orderProductModel.setProduct(productModel);
        this.orderProductModel.setQuantity(productQuantity);

        this.productName = new SimpleStringProperty(orderProductModel.getProduct().getName());
        this.categoryName = new SimpleStringProperty(orderProductModel.getProduct().getCategories().getName());
        this.productQuantity = new SimpleStringProperty(orderProductModel.getQuantity().toString());
    }
}
