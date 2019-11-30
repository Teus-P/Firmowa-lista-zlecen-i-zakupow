package pl.app.controllers.common.listItems;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import pl.app.api.model.OrderModel;

//TODO - formatowanie daty
@Getter
public class OrderTableItem extends RecursiveTreeObject<OrderTableItem> {

    private OrderModel orderModel;

    private StringProperty orderNumberDisplayValue;
    private StringProperty acceptedStatusDisplayValue;
    private StringProperty userNameDisplayValue;
    private StringProperty createdOrderDateDisplayValue;

    public OrderTableItem(OrderModel orderModel) {
        this.orderModel = orderModel;
        this.orderNumberDisplayValue = new SimpleStringProperty(orderModel.getIdOrder().toString());

        if (orderModel.getRecipient() == null) {
            this.acceptedStatusDisplayValue = new SimpleStringProperty("Oczekuje na akceptację");
        } else {
            if (orderModel.isAccepted()) {
                this.acceptedStatusDisplayValue = new SimpleStringProperty("Zamówienie zaakceptowane");
            } else if (!orderModel.isAccepted()) {
                this.acceptedStatusDisplayValue = new SimpleStringProperty("Zamówienie odrzucone");
            }
        }


        if (orderModel.getUserAccount() != null) {
            this.userNameDisplayValue = new SimpleStringProperty(orderModel.getUserAccount().getUsername());
        }

        this.createdOrderDateDisplayValue = new SimpleStringProperty(orderModel.getCreatedDate());


    }
}
