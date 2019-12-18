package pl.app.controllers.content;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.OrderHelper;
import pl.app.api.model.OrderModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.responseInterfaces.OrderHistoryResponseListener;
import pl.app.controllers.common.listItems.OrderTableItem;
import pl.app.controllers.content.realizedOrder.RealizedOrderController;
import pl.app.core.baseComponent.BaseScreen;
import pl.app.core.property.ContentProperty;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrdersHistoryController extends BaseScreen implements OrderHistoryResponseListener, Initializable {

    public static final String HISTORY_PAGE = "history controller";

    private OrderHelper orderHelper;
    private ObservableList<OrderTableItem> orderTableItemObsList;

    @FXML
    private JFXTreeTableView<OrderTableItem> orderHistoryTable;

    @FXML
    private AnchorPane pageContainer;


    public OrdersHistoryController() {
        orderTableItemObsList = FXCollections.observableArrayList();
        orderHelper = new OrderHelper(ApiResourcesClient.getApi());
    }


    @Override
    public void onOrderHistorySuccessResponse(List<OrderModel> orderModelList) {
        initOrderHistoryTable(orderModelList);
    }

    @Override
    public void onOrderHistoryFailedResponse(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder();

        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        orderHistoryTable.setPlaceholder(new Label(builder.toString()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchData();
    }

    private void initOrderHistoryTable(List<OrderModel> orderModelList) {
        orderModelList.forEach(model -> orderTableItemObsList.add(new OrderTableItem(model)));

        JFXTreeTableColumn<OrderTableItem, String> orderNumberColumn = new JFXTreeTableColumn<>("Numer zamówienia");
        orderNumberColumn.setCellValueFactory(param -> param.getValue().getValue().getOrderNumberDisplayValue());

        JFXTreeTableColumn<OrderTableItem, String> orderAcceptStatusColumn = new JFXTreeTableColumn<>("Status akceptacji");
        orderAcceptStatusColumn.setCellValueFactory(param -> param.getValue().getValue().getAcceptedStatusDisplayValue());

        JFXTreeTableColumn<OrderTableItem, String> orderCreatedDateColumn = new JFXTreeTableColumn<>("Data zamówienia");
        orderCreatedDateColumn.setCellValueFactory(param -> param.getValue().getValue().getCreatedOrderDateDisplayValue());

        final TreeItem<OrderTableItem> root = new RecursiveTreeItem<>(orderTableItemObsList, RecursiveTreeObject::getChildren);
        orderHistoryTable.getColumns().setAll(orderNumberColumn, orderAcceptStatusColumn, orderCreatedDateColumn);
        orderHistoryTable.setRoot(root);
        orderHistoryTable.setShowRoot(false);

        orderHistoryTable.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {


                RealizedOrderController controller = getContentManager().buildContext(ContentProperty.REALIZED_ORDER).attachTo(pageContainer).build().getController();
                controller.setOrderModel(orderHistoryTable.getSelectionModel().getSelectedItem().getValue().getOrderModel());
                controller.setArguments(HISTORY_PAGE);
                controller.notifyDataSetChanged();


            }
        });
    }

    private void fetchData() {
        orderHelper.getAllUserOrderHistory(this);
    }
}
