package pl.app.controllers.content.currentOrders;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.OrderHelper;
import pl.app.api.model.OrderModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.responseInterfaces.CurrentUserOrdersResponseListener;
import pl.app.controllers.common.listItems.OrderTableItem;
import pl.app.controllers.content.realizedOrder.RealizedOrderController;
import pl.app.core.baseComponent.BaseScreen;
import pl.app.core.property.ContentProperty;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CurrentOrdersController extends BaseScreen implements CurrentUserOrdersResponseListener {

    public static final String CURRENT_ORDER_PAGE = "current controller";
    private OrderHelper orderHelper;
    private ObservableList<OrderTableItem> orderTableItemObsList;

    @FXML
    private JFXTreeTableView<OrderTableItem> currentOrdersTable;

    @FXML
    private AnchorPane pageContainer;

    public CurrentOrdersController() {
        orderHelper = new OrderHelper(ApiResourcesClient.getApi());
        orderTableItemObsList = FXCollections.observableArrayList();
    }


    private void fetchData() {
        orderHelper.getAllCurrentUserOrders(this);
    }

    private void initCurrentOrdersTable(List<OrderModel> orderModelList) {

        orderModelList.forEach(model -> orderTableItemObsList.add(new OrderTableItem(model)));

        JFXTreeTableColumn<OrderTableItem, String> orderNumberColumn = new JFXTreeTableColumn<>("Numer zamówienia");
        orderNumberColumn.setCellValueFactory(param -> param.getValue().getValue().getOrderNumberDisplayValue());

        JFXTreeTableColumn<OrderTableItem, String> orderAcceptStatusColumn = new JFXTreeTableColumn<>("Status akceptacji");
        orderAcceptStatusColumn.setCellValueFactory(param -> param.getValue().getValue().getAcceptedStatusDisplayValue());

        JFXTreeTableColumn<OrderTableItem, String> orderCreatedDateColumn = new JFXTreeTableColumn<>("Data zamówienia");
        orderCreatedDateColumn.setCellValueFactory(param -> param.getValue().getValue().getCreatedOrderDateDisplayValue());

        JFXTreeTableColumn<OrderTableItem, String> orderProductCountColumn = new JFXTreeTableColumn<>("Liczba produktów w zamówieniu");
        orderProductCountColumn.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getOrderModel().getOrderProductModels().size())));

        final TreeItem<OrderTableItem> root = new RecursiveTreeItem<>(orderTableItemObsList, RecursiveTreeObject::getChildren);
        currentOrdersTable.getColumns().setAll(orderNumberColumn, orderAcceptStatusColumn, orderCreatedDateColumn, orderProductCountColumn);
        currentOrdersTable.setRoot(root);
        currentOrdersTable.setShowRoot(false);

        currentOrdersTable.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {

                if (currentOrdersTable.getSelectionModel().getSelectedItem().getValue().getOrderModel().getRecipient() == null) {
                    getContentManager().buildContext(ContentProperty.CURRENT_WAITING_ORDER).attachTo(pageContainer).build();
                } else {
                    RealizedOrderController controller = getContentManager().buildContext(ContentProperty.REALIZED_ORDER).attachTo(pageContainer).build().getController();
                    controller.setOrderModel(currentOrdersTable.getSelectionModel().getSelectedItem().getValue().getOrderModel());
                    controller.setArguments(CURRENT_ORDER_PAGE);
                    controller.notifyDataSetChanged();
                }


            }
        });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        //initCurrentOrdersTable();
        fetchData();
    }

    @Override
    public void onCurrentUserOrdersSuccessResponse(List<OrderModel> orderModelList) {
        initCurrentOrdersTable(orderModelList);
    }

    @Override
    public void onCurrentUserOrdersFailedResponse(ResponseModel responseModel) {
        currentOrdersTable.setPlaceholder(new Label("Brak aktualnych zamówień"));
    }
}