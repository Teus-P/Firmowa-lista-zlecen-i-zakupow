package pl.app.controllers.content;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.OrderHelper;
import pl.app.controllers.common.listItems.OrderTableItem;
import pl.app.core.baseComponent.BaseScreen;
import pl.app.core.screen.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

//TODO - do dokończenia
public class CurrentOrdersController extends BaseScreen {

    private OrderHelper orderHelper;
    private ObservableList<OrderTableItem> orderTableItemObsList;

    @FXML
    private JFXTreeTableView<OrderTableItem> currentOrdersTable;

    public CurrentOrdersController() {
        orderHelper = new OrderHelper(ApiResourcesClient.getApi());
        orderTableItemObsList = FXCollections.observableArrayList();
    }

    private void initCurrentOrdersTable() {
        currentOrdersTable.setPlaceholder(new Label("Brak aktualnych zamówień"));

        orderHelper.getAllUserOrders().forEach(model -> orderTableItemObsList.add(new OrderTableItem(model)));

        JFXTreeTableColumn<OrderTableItem, String> orderNumberColumn = new JFXTreeTableColumn<>("Numer zamówienia");
        orderNumberColumn.setCellValueFactory(param -> param.getValue().getValue().getOrderNumberDisplayValue());

        JFXTreeTableColumn<OrderTableItem, String> orderAcceptStatusColumn = new JFXTreeTableColumn<>("Status akceptacji");
        orderAcceptStatusColumn.setCellValueFactory(param -> param.getValue().getValue().getAcceptedStatusDisplayValue());

        JFXTreeTableColumn<OrderTableItem, String> orderAcceptedDateColumn = new JFXTreeTableColumn<>("Data akceptacji");
        orderAcceptedDateColumn.setCellValueFactory(param -> param.getValue().getValue().getAcceptOrderDateDisplayValue());

        JFXTreeTableColumn<OrderTableItem, String> orderProductCountColumn = new JFXTreeTableColumn<>("Liczba produktów w zamówieniu");
        orderProductCountColumn.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getOrderModel().getOrderProductModels().size())));

        final TreeItem<OrderTableItem> root = new RecursiveTreeItem<>(orderTableItemObsList, RecursiveTreeObject::getChildren);
        currentOrdersTable.getColumns().setAll(orderNumberColumn, orderAcceptStatusColumn, orderAcceptedDateColumn, orderProductCountColumn);
        currentOrdersTable.setRoot(root);
        currentOrdersTable.setShowRoot(false);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        initCurrentOrdersTable();
    }
}