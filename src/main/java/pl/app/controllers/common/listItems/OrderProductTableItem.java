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

    OrderProductModel orderProductModel;
    StringProperty name;
    StringProperty category;
    StringProperty quantity;

    public OrderProductTableItem(OrderProductModel orderProductModel) {
        this.orderProductModel = orderProductModel;

        this.name = new SimpleStringProperty(orderProductModel.getProduct().getName());
        this.category = new SimpleStringProperty(orderProductModel.getProduct().getCategories().getName());
        this.quantity = new SimpleStringProperty(orderProductModel.getQuantity().toString());
    }

    public OrderProductTableItem(ProductModel productModel, Integer quantity) {
        this.orderProductModel = new OrderProductModel();
        this.orderProductModel.setProduct(productModel);
        this.orderProductModel.setQuantity(quantity);

        this.name = new SimpleStringProperty(orderProductModel.getProduct().getName());
        this.category = new SimpleStringProperty(orderProductModel.getProduct().getCategories().getName());
        this.quantity = new SimpleStringProperty(orderProductModel.getQuantity().toString());
    }
}
