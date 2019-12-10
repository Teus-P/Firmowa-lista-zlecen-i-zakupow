package pl.app.controllers.content.realizedOrder;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import pl.app.api.model.UserOrderInfoModel;

import java.awt.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Date;
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
        statusDate.setText(userOrderInfoModel.getInformationDate());
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
    }

    private static boolean containsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }





}
