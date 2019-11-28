package pl.app.controllers.common.listItems;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import pl.app.api.model.OrderModel;

@Getter
public class OrderTableItem extends RecursiveTreeObject<OrderTableItem> {

    private OrderModel orderModel;

    private StringProperty orderNumberDisplayValue;
    private StringProperty acceptedStatusDisplayValue;
    private StringProperty userNameDisplayValue;
    private StringProperty acceptOrderDateDisplayValue;

    public OrderTableItem(OrderModel orderModel) {
        this.orderModel = orderModel;
        this.orderNumberDisplayValue = new SimpleStringProperty(orderModel.getIdOrder().toString());
        this.acceptedStatusDisplayValue = new SimpleStringProperty(orderModel.getOrderStatus().getName());
        if (orderModel.getUserAccount() != null) {
            this.userNameDisplayValue = new SimpleStringProperty(orderModel.getUserAccount().getUsername());
        }
        if (orderModel.getAcceptedDate() != null) {
            this.acceptOrderDateDisplayValue = new SimpleStringProperty(orderModel.getAcceptedDate());
        } else {
            this.acceptOrderDateDisplayValue = new SimpleStringProperty("Brak daty akceptacji");
        }

    }
}
