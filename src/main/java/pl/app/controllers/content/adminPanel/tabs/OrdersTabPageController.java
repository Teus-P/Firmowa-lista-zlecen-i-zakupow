package pl.app.controllers.content.adminPanel.tabs;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.OrderHelper;
import pl.app.controllers.common.listItems.OrderTableItem;
import pl.app.controllers.content.adminPanel.dialog.WaitingOrderDialogDetails;
import pl.app.core.dialog.DialogStage;
import pl.app.core.property.DialogProperty;

import java.net.URL;
import java.util.ResourceBundle;

public class OrdersTabPageController implements Initializable {

    private OrderHelper orderHelper;
    private ObservableList<OrderTableItem> orderTableItemObservableList;

    @FXML
    private JFXTextField orderSearchField;

    @FXML
    private JFXTreeTableView<OrderTableItem> orderTable;


    public OrdersTabPageController() {
        orderHelper = new OrderHelper(ApiResourcesClient.getApi());
        orderTableItemObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initOrderTreeTableView();
    }

    //TODO - do dokończenia
    private void initOrderTreeTableView() {
        orderHelper.getAllOrders().forEach(orderModel -> orderTableItemObservableList.add(new OrderTableItem(orderModel)));

        JFXTreeTableColumn<OrderTableItem, String> orderNumberColumn = new JFXTreeTableColumn<>("Numer zamówienia");
        orderNumberColumn.setCellValueFactory(param -> param.getValue().getValue().getOrderNumberDisplayValue());

        JFXTreeTableColumn<OrderTableItem, String> userNameColumn = new JFXTreeTableColumn<>("Użytkownik");
        userNameColumn.setCellValueFactory(param -> param.getValue().getValue().getUserNameDisplayValue());

        JFXTreeTableColumn<OrderTableItem, String> orderAcceptStatusColumn = new JFXTreeTableColumn<>("Status akceptacji");
        orderAcceptStatusColumn.setCellValueFactory(param -> param.getValue().getValue().getAcceptedStatusDisplayValue());

        final TreeItem<OrderTableItem> root = new RecursiveTreeItem<>(orderTableItemObservableList, RecursiveTreeObject::getChildren);
        orderTable.getColumns().setAll(orderNumberColumn, userNameColumn, orderAcceptStatusColumn);
        orderTable.setRoot(root);
        orderTable.setShowRoot(false);

        orderTable.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                showOrderDetails();
            }
        });

    }

    private void showOrderDetails() {
        DialogStage waitingOrderDetails = new DialogStage(DialogProperty.WAITING_ORDER_DETAILS);
        WaitingOrderDialogDetails controller = waitingOrderDetails.getController();
        controller.initData(orderTable.getSelectionModel().getSelectedItem().getValue().getOrderModel());
        controller.setOnDialogCloseListener(() -> {

        });
        waitingOrderDetails.showAndWait();
    }


}
