package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.helpers.ImplementersHelper;
import pl.app.api.model.ImplementerModel;
import pl.app.api.model.OrderModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.responseInterfaces.OrderImplementersResponseListener;
import pl.app.controllers.common.listItems.CategoryTableItem;
import pl.app.controllers.common.listItems.ImplementersTableItem;
import pl.app.controllers.common.listItems.UserTableItem;
import pl.app.core.baseComponent.BaseDialog;

import java.util.List;

public class AssignImplementersResponseListenerDialog extends BaseDialog implements OrderImplementersResponseListener {

    private OrderModel orderModel;
    private CategoriesHelper categoriesHelper;
    private ImplementersHelper implementersHelper;
    private ObservableList<ImplementersTableItem> implementersTableItemObservableList;

    @FXML
    private JFXTreeTableView<ImplementersTableItem> availableImplTable;

    @FXML
    private JFXTreeTableView<UserTableItem> choosenImplTable;

    @FXML
    private JFXTreeTableView<CategoryTableItem> categoryInfoTable;

    public void initData(OrderModel orderModel) {
        this.orderModel = orderModel;
        initHelpers();
        initObjects();
        initOrderCategoriesTable();
        initAvailableImplTable();
    }

    private void initObjects() {
        implementersTableItemObservableList = FXCollections.observableArrayList();
    }

    private void initHelpers() {
        categoriesHelper = new CategoriesHelper(ApiResourcesClient.getApi());
        implementersHelper = new ImplementersHelper(ApiResourcesClient.getApi());
    }

    private void initOrderCategoriesTable() {

    }

    private void initAvailableImplTable() {

        implementersHelper.getImplementersForOrder(orderModel.getIdOrder(), this);

        JFXTreeTableColumn<ImplementersTableItem, String> implementersNameColumn = new JFXTreeTableColumn<>("Realizator");
        implementersNameColumn.setCellValueFactory(param -> param.getValue().getValue().getUserFullNameDisplayValue());

        JFXTreeTableColumn<ImplementersTableItem, String> categoryNameColumn = new JFXTreeTableColumn<>("Kategoria");
        categoryNameColumn.setCellValueFactory(param -> param.getValue().getValue().getCategoriesDisplayValue());


        final TreeItem<ImplementersTableItem> root = new RecursiveTreeItem<>(implementersTableItemObservableList, RecursiveTreeObject::getChildren);
        availableImplTable.getColumns().setAll(implementersNameColumn, categoryNameColumn);
        availableImplTable.setRoot(root);
        availableImplTable.setShowRoot(false);

    }


    @FXML
    void choseImplementer(ActionEvent event) {

    }

    @FXML
    void deleteImplementer(ActionEvent event) {

    }

    @FXML
    void acceptButtonOnAction(ActionEvent event) {

    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {

    }

    @Override
    public void onOrderImplementersResponseSuccess(List<ImplementerModel> implementerModelList) {
        implementerModelList.forEach(implementerModel -> implementersTableItemObservableList.add(new ImplementersTableItem(implementerModel)));
    }

    @Override
    public void onOrderImplementersResponseFailed(ResponseModel responseModel) {

    }
}
