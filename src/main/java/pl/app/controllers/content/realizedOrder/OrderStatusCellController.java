package pl.app.controllers.content.realizedOrder;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pl.app.api.model.UserOrderInfoModel;
import pl.app.controllers.common.DateParser;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderStatusCellController implements Initializable {

    private UserOrderInfoModel userOrderInfoModel;

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
        this.userOrderInfoModel = item;


        initUI();
        initStatus();
    }

    private void initUI() {
        statusName.setText(userOrderInfoModel.getOrderStatus().getName());
        statusDate.setText(DateParser.parseDate(userOrderInfoModel.getInformationDate()));
    }


    private void initStatus() {
        if (containsIgnoreCase(userOrderInfoModel.getOrderStatus().getName(), "oczekuje")) {
            statusShape.setFill(Color.ORANGE);
        }

        if (containsIgnoreCase(userOrderInfoModel.getOrderStatus().getName(), "odrzucone")) {
            statusShape.setFill(Color.RED);
        }

        if (containsIgnoreCase(userOrderInfoModel.getOrderStatus().getName(), "realizacji")) {
            statusShape.setFill(Color.BLUE);
        }
        if (containsIgnoreCase(userOrderInfoModel.getOrderStatus().getName(), "zaakceptowane")) {
            statusShape.setFill(Color.GREEN);
        }
        if (containsIgnoreCase(userOrderInfoModel.getOrderStatus().getName(), "odbiór")) {
            statusShape.setFill(Color.BLUEVIOLET);
        }
    }

    private static boolean containsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }


}
