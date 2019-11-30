package pl.app.controllers.content.realizedOrder;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import lombok.Setter;
import pl.app.api.model.OrderModel;
import pl.app.api.model.UserOrderInfoModel;
import pl.app.controllers.common.listItems.OrderProductTableItem;
import pl.app.controllers.common.listViewItems.OrderStatusCell;
import pl.app.controllers.content.OrdersHistoryController;
import pl.app.controllers.content.currentOrders.CurrentOrdersController;
import pl.app.core.baseComponent.BaseScreen;
import pl.app.core.property.ContentProperty;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class RealizedOrderController extends BaseScreen {

    private ObservableList<UserOrderInfoModel> userOrderInfoModelObservableList;
    private ObservableList<OrderProductTableItem> orderProductTableItemObsList;

    private String parentControllerArguments = null;

    @Setter
    private OrderModel orderModel;

    @FXML
    private JFXTreeTableView<OrderProductTableItem> orderProductTable;

    @FXML
    private AnchorPane pageContainer;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label recipientNameLabel;

    @FXML
    private JFXListView<UserOrderInfoModel> orderStatusListView;


    public RealizedOrderController() {
        userOrderInfoModelObservableList = FXCollections.observableArrayList();
        orderProductTableItemObsList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        if (parentControllerArguments.equals(OrdersHistoryController.HISTORY_PAGE)) {
            getContentManager().buildContext(ContentProperty.HISTORY_OF_ORDERS).attachTo(pageContainer).build();
        }
        if (parentControllerArguments.equals(CurrentOrdersController.CURRENT_ORDER_PAGE)) {
            getContentManager().buildContext(ContentProperty.CURRENT_ORDERS).attachTo(pageContainer).build();
        }

    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        initUI();
    }


    private void initUI() {

        orderNumberLabel.setText(orderModel.getIdOrder().toString());
        if (orderModel.getRecipient() != null) {
            recipientNameLabel.setText(orderModel.getRecipient().getUsername());
        } else {
            recipientNameLabel.setText("Brak");
        }

        Collections.sort(orderModel.getUserOrderInfoModels());
        userOrderInfoModelObservableList.addAll(orderModel.getUserOrderInfoModels());
        orderStatusListView.setCellFactory(param -> new OrderStatusCell());
        orderStatusListView.getItems().addAll(userOrderInfoModelObservableList);
        orderStatusListView.setItems(userOrderInfoModelObservableList);

        initOrderProductTable();

    }


    private void initOrderProductTable() {
        orderModel.getOrderProductModels().forEach(model -> orderProductTableItemObsList.add(new OrderProductTableItem(model)));

        JFXTreeTableColumn<OrderProductTableItem, String> productNameColumn = new JFXTreeTableColumn<>("Nazwa produktu");
        productNameColumn.setCellValueFactory(param -> param.getValue().getValue().getProductName());

        JFXTreeTableColumn<OrderProductTableItem, String> categoryNameColumn = new JFXTreeTableColumn<>("Kategoria produktu");
        categoryNameColumn.setCellValueFactory(param -> param.getValue().getValue().getCategoryName());

        JFXTreeTableColumn<OrderProductTableItem, String> unitNameColumn = new JFXTreeTableColumn<>("Jednostka produktu");
        unitNameColumn.setCellValueFactory(param -> param.getValue().getValue().getUnitName());

        JFXTreeTableColumn<OrderProductTableItem, String> productQuantityColumn = new JFXTreeTableColumn<>("Ilość");
        productQuantityColumn.setCellValueFactory(param -> param.getValue().getValue().getProductQuantity());

        final TreeItem<OrderProductTableItem> root = new RecursiveTreeItem<>(orderProductTableItemObsList, RecursiveTreeObject::getChildren);
        orderProductTable.getColumns().setAll(productNameColumn, categoryNameColumn, unitNameColumn, productQuantityColumn);
        orderProductTable.setRoot(root);
        orderProductTable.setShowRoot(false);
    }

    public void setArguments(String args) {
        parentControllerArguments = args;
    }

}
