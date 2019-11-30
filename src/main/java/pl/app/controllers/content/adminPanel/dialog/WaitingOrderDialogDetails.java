package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.paint.Color;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.OrderHelper;
import pl.app.api.model.OrderModel;
import pl.app.controllers.common.listItems.OrderProductTableItem;
import pl.app.core.baseComponent.BaseDialog;
import pl.app.core.dialog.DialogStage;
import pl.app.core.property.DialogProperty;

public class WaitingOrderDialogDetails extends BaseDialog {

    private OrderModel orderModel;
    private ObservableList<OrderProductTableItem> orderProductTableItemObservableList;
    private OrderHelper orderHelper;


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

    @FXML
    private JFXButton acceptButton;

    @FXML
    private JFXButton rejectButton;

    public void initData(OrderModel orderModel) {

        this.orderModel = orderModel;

        initObjects();
        initOrderProductTreeTableView();
        initUI();
    }

    private void initUI() {
        orderNumberLabel.setText(orderModel.getIdOrder().toString());

        if (orderModel.getRecipient() == null) {
            orderStatus.setText("Zamówienie oczekuje na akceptację");
            orderStatus.setTextFill(Color.ORANGE);
        } else {
            if (orderModel.isAccepted()) {
                orderStatus.setText("Zamówienie zaakceptowane");
                orderStatus.setTextFill(Color.GREEN);
            } else if (!orderModel.isAccepted()) {
                orderStatus.setText("Zamówienie odrzucone");
                orderStatus.setTextFill(Color.RED);
            }
        }


        orderUserNameLabel.setText(orderModel.getUserAccount().getUsername());
        orderDateLabel.setText(orderModel.getCreatedDate());
        productCountLabel.setText(Integer.toString(orderModel.getOrderProductModels().size()));

        if (orderModel.getRecipient() != null) {
            acceptButton.setVisible(false);
            rejectButton.setVisible(false);
        }

    }

    private void initObjects() {
        orderProductTableItemObservableList = FXCollections.observableArrayList();
        orderHelper = new OrderHelper(ApiResourcesClient.getApi());
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
        AssignImplementersDialog controller = assignImplementersDialog.getController();
        controller.initData(orderModel);
        controller.setOnDialogCloseListener(() -> {
            refreshOrderModel();
            initUI();
        });
        assignImplementersDialog.showAndWait();
    }

    @FXML
    void rejectOrderButtonOnAction(ActionEvent event) {
        DialogStage rejectOrderDialog = new DialogStage(DialogProperty.REJECT_ORDER);
        RejectOrderDialogController controller = rejectOrderDialog.getController();
        controller.initData(orderModel);
        controller.setOnDialogCloseListener(() -> {
            refreshOrderModel();
            initUI();
        });
        rejectOrderDialog.showAndWait();
    }

    private void refreshOrderModel() {
        this.orderModel = orderHelper.getOrderById(orderModel.getIdOrder());
    }


}
