package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.OrderHelper;
import pl.app.api.model.OrderModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.responseInterfaces.RejectOrderResponseListener;
import pl.app.core.baseComponent.BaseDialog;

import java.util.Optional;

public class RejectOrderDialogController extends BaseDialog implements RejectOrderResponseListener {

    private OrderModel orderModel;
    private OrderHelper orderHelper;


    @FXML
    private JFXTextArea rejectReasonTxArea;

    @FXML
    private Label responseLabel;


    public void initData(OrderModel orderModel) {
        this.orderModel = orderModel;

        initObjects();
    }

    private void initObjects() {
        orderHelper = new OrderHelper(ApiResourcesClient.getApi());
    }

    @FXML
    void cancelOnAction(ActionEvent event) {
        getDialogStage().close();
    }

    @FXML
    void rejectOrderOnAction(ActionEvent event) {
        if (rejectReasonTxArea.getText().length() > 0) {
            orderHelper.rejectOrderById(orderModel.getIdOrder(), rejectReasonTxArea.getText(), this);
        } else {
            responseLabel.setText("Proszę wprowadzić uzasadnienie odrzucenia");
        }
    }

    @Override
    public void onRejectOrderSuccessResponse(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder();
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(responseModel.getMessage());
        alert.setHeaderText(responseModel.getTimestamp());
        alert.setContentText(builder.toString());
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType buttonType = result.orElse(ButtonType.OK);
        if (buttonType == ButtonType.OK) {
            getDialogStage().close();
        }


    }

    @Override
    public void onRejectOrderFailedResponse(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder();

        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));

        responseLabel.setText(builder.toString());
    }
}
