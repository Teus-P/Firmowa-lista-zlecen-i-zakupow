package pl.app.controllers.content.adminPanel.dialog;

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
import pl.app.api.model.OrderModel;
import pl.app.controllers.common.listItems.OrderProductTableItem;
import pl.app.core.baseComponent.BaseDialog;
import pl.app.core.dialog.DialogStage;
import pl.app.core.property.DialogProperty;

public class WaitingOrderDialogDetails extends BaseDialog {

    private OrderModel orderModel;
    private ObservableList<OrderProductTableItem> orderProductTableItemObservableList;


    @FXML
    private JFXTreeTableView<OrderProductTableItem> ordersProductTable;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label orderStatus;

    @FXML
    private Label responseLabel;

    @FXML
    private Label orderUserNameLabel;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label productSuggestionCountLabel;

    @FXML
    private Label productCountLabel;

    public void initData(OrderModel orderModel) {

        this.orderModel = orderModel;

        initObjects();
        initOrderProductTreeTableView();
        initUI();
    }

    private void initUI() {
        orderNumberLabel.setText(orderModel.getIdOrder().toString());
        orderStatus.setText(orderModel.getOrderStatus().getName());
        orderUserNameLabel.setText(orderModel.getUserAccount().getUsername());
        orderDateLabel.setText(orderModel.getDate());
        productCountLabel.setText(Integer.toString(orderModel.getOrderProductModels().size()));
    }

    private void initObjects() {
        orderProductTableItemObservableList = FXCollections.observableArrayList();
    }

    private void initOrderProductTreeTableView() {
        if (!orderModel.getOrderProductModels().isEmpty()) {

            orderModel.getOrderProductModels().forEach(orderProductModel -> orderProductTableItemObservableList.add(new OrderProductTableItem(orderProductModel)));


            JFXTreeTableColumn<OrderProductTableItem, String> productNameColumn = new JFXTreeTableColumn<>("Nazwa produktu");
            productNameColumn.setCellValueFactory(param -> param.getValue().getValue().getProductName());

            JFXTreeTableColumn<OrderProductTableItem, String> categoryNameColumn = new JFXTreeTableColumn<>("Kategoria produktu");
            categoryNameColumn.setCellValueFactory(param -> param.getValue().getValue().getCategoryName());

            JFXTreeTableColumn<OrderProductTableItem, String> unitNameColumn = new JFXTreeTableColumn<>("Jednostka produktu");
            unitNameColumn.setCellValueFactory(param -> param.getValue().getValue().getUnitName());

            JFXTreeTableColumn<OrderProductTableItem, String> productQuantityColumn = new JFXTreeTableColumn<>("Ilość");
            productQuantityColumn.setCellValueFactory(param -> param.getValue().getValue().getProductQuantity());

            final TreeItem<OrderProductTableItem> root = new RecursiveTreeItem<>(orderProductTableItemObservableList, RecursiveTreeObject::getChildren);
            ordersProductTable.getColumns().setAll(productNameColumn, categoryNameColumn, unitNameColumn, productQuantityColumn);
            ordersProductTable.setRoot(root);
            ordersProductTable.setShowRoot(false);

        }
    }

    @FXML
    void acceptOrderButtonOnAction(ActionEvent event) {
        DialogStage assignImplementersDialog = new DialogStage(DialogProperty.ASSIGN_IMPLEMENTERS);
        AssignImplementersResponseListenerDialog controller = assignImplementersDialog.getController();
        controller.initData(orderModel);
        controller.setOnDialogCloseListener(() -> {

        });
        assignImplementersDialog.showAndWait();
    }

    @FXML
    void rejectOrderButtonOnAction(ActionEvent event) {

    }



}
