package pl.app.controllers.common.listItems;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import pl.app.api.model.OrderModel;

@Getter
public class OrderTableItem extends RecursiveTreeObject<OrderTableItem> {

    private OrderModel orderModel;

    private StringProperty orderNumber;
    private StringProperty acceptedStatus;
    private StringProperty userName;

    public OrderTableItem(OrderModel orderModel) {
        this.orderModel = orderModel;
        this.orderNumber = new SimpleStringProperty(orderModel.getIdOrder().toString());
        if (orderModel.isAccepted()) {
            this.acceptedStatus = new SimpleStringProperty("Zaakceptowany");
        } else {
            this.acceptedStatus = new SimpleStringProperty("Niezaakceptowany");
        }
        this.userName = new SimpleStringProperty(orderModel.getUserAccount().getUsername());
    }
}
