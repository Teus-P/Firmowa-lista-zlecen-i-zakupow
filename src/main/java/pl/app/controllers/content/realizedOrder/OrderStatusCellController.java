package pl.app.controllers.content.realizedOrder;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import pl.app.api.model.UserOrderInfoModel;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderStatusCellController implements Initializable {


    @FXML
    private Circle statusShape;

    @FXML
    private Label statusName;

    @FXML
    private Label statusDate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void initData(UserOrderInfoModel item) {
        statusName.setText(item.getOrderStatus().getName());
        statusDate.setText(item.getInformationDate());
    }

}
