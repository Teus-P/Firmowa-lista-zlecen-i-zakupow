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
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.helpers.ImplementersHelper;
import pl.app.api.helpers.OrderHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ImplementerModel;
import pl.app.api.model.OrderModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.responseInterfaces.AcceptOrderResponseListener;
import pl.app.api.responseInterfaces.OrderImplementersResponseListener;
import pl.app.controllers.common.popupDialogs.WarningDialog;
import pl.app.controllers.common.listItems.CategoryTableItem;
import pl.app.controllers.common.listItems.ImplementersTableItem;
import pl.app.core.baseComponent.BaseDialog;

import java.util.*;

public class AssignImplementersDialog extends BaseDialog implements OrderImplementersResponseListener, AcceptOrderResponseListener {

    private static final Map<CategoriesModel, Set<ImplementersTableItem>> TABLE_ITEMS_CONTRACT = new HashMap<>();

    private OrderModel orderModel;
    private CategoriesHelper categoriesHelper;
    private OrderHelper orderHelper;
    private ImplementersHelper implementersHelper;
    private ObservableList<ImplementersTableItem> availableImplementersObsList;
    private ObservableList<ImplementersTableItem> chosenImplementersItemObsList;
    private ObservableList<CategoryTableItem> orderCategoriesObsList;
    private Set<CategoriesModel> categoriesModelSet;

    @FXML
    private JFXTreeTableView<ImplementersTableItem> availableImplTable;

    @FXML
    private JFXTreeTableView<ImplementersTableItem> chosenImplTable;

    @FXML
    private JFXTreeTableView<CategoryTableItem> categoryInfoTable;

    public void initData(OrderModel orderModel) {
        this.orderModel = orderModel;
        initHelpers();
        initObjects();
        initAvailableImplTable();
        initChosenImplTable();
        initOrderCategoriesTable();
    }

    private void initObjects() {
        availableImplementersObsList = FXCollections.observableArrayList();
        chosenImplementersItemObsList = FXCollections.observableArrayList();
        orderCategoriesObsList = FXCollections.observableArrayList();
        categoriesModelSet = new HashSet<>();
    }

    private void initHelpers() {
        categoriesHelper = new CategoriesHelper(ApiResourcesClient.getApi());
        implementersHelper = new ImplementersHelper(ApiResourcesClient.getApi());
        orderHelper = new OrderHelper(ApiResourcesClient.getApi());
    }

    private void initOrderCategoriesTable() {


        availableImplementersObsList.forEach(item -> categoriesModelSet.add(item.getImplementerModel().getCategoriesModel()));

        categoriesModelSet.forEach(item -> orderCategoriesObsList.add(new CategoryTableItem(item)));

        JFXTreeTableColumn<CategoryTableItem, String> categoryNameColumn = new JFXTreeTableColumn<>("Kategoria");
        categoryNameColumn.setCellValueFactory(param -> param.getValue().getValue().getCategoryName());

        final TreeItem<CategoryTableItem> root = new RecursiveTreeItem<>(orderCategoriesObsList, RecursiveTreeObject::getChildren);

        categoryInfoTable.getColumns().setAll(categoryNameColumn);
        categoryInfoTable.setRoot(root);
        categoryInfoTable.setShowRoot(false);
        categoryInfoTable.setPlaceholder(new Label("Pomyślnie przypisano wszystkich realizatorów"));

    }

    private void refreshCategoriesTable() {
        orderCategoriesObsList.clear();
        categoriesModelSet.clear();
        categoryInfoTable.getSelectionModel().clearSelection();
        availableImplementersObsList.forEach(item -> categoriesModelSet.add(item.getImplementerModel().getCategoriesModel()));
        categoriesModelSet.forEach(item -> orderCategoriesObsList.add(new CategoryTableItem(item)));
    }

    private void initAvailableImplTable() {

        implementersHelper.getImplementersForOrder(orderModel.getIdOrder(), this);

        initImplementersTables(availableImplementersObsList, availableImplTable);
        availableImplTable.setPlaceholder(new Label("Brak dostępnych realizatorów"));

    }


    private void initChosenImplTable() {

        initImplementersTables(chosenImplementersItemObsList, chosenImplTable);
        chosenImplTable.setPlaceholder(new Label("Brak wybranych realizatorów"));

    }

    private void initImplementersTables(ObservableList<ImplementersTableItem> observableList, JFXTreeTableView<ImplementersTableItem> table) {

        JFXTreeTableColumn<ImplementersTableItem, String> implementersNameColumn = new JFXTreeTableColumn<>("Realizator");
        implementersNameColumn.setCellValueFactory(param -> param.getValue().getValue().getUserFullNameDisplayValue());

        JFXTreeTableColumn<ImplementersTableItem, String> categoryNameColumn = new JFXTreeTableColumn<>("Kategoria");
        categoryNameColumn.setCellValueFactory(param -> param.getValue().getValue().getCategoriesDisplayValue());
        final TreeItem<ImplementersTableItem> root = new RecursiveTreeItem<>(observableList, RecursiveTreeObject::getChildren);

        table.getColumns().setAll(implementersNameColumn, categoryNameColumn);
        table.setRoot(root);
        table.setShowRoot(false);

    }


    @FXML
    void choseImplementer(ActionEvent event) {

        if (availableImplTable.getSelectionModel().getSelectedItem() != null) {

            ImplementersTableItem chosenImplementer = availableImplTable.getSelectionModel().getSelectedItem().getValue();

            if (chosenImplementer != null) {

                implementersTableContract(chosenImplementer, false);

            }
        }

    }

    @FXML
    void removeChosenImplementer(ActionEvent event) {
        if (chosenImplTable.getSelectionModel().getSelectedItem() != null && chosenImplementersItemObsList.size() > 0) {

            ImplementersTableItem removeChosenImplementer = chosenImplTable.getSelectionModel().getSelectedItem().getValue();

            if (removeChosenImplementer != null) {
                implementersTableContract(removeChosenImplementer, true);
            }
        }
    }

    private void implementersTableContract(ImplementersTableItem chosenImplementer, boolean restoreAvailableItems) {

        if (restoreAvailableItems) {
            chosenImplTable.getSelectionModel().clearSelection();
            chosenImplementersItemObsList.remove(chosenImplementer);
            availableImplementersObsList.addAll(TABLE_ITEMS_CONTRACT.get(chosenImplementer.getImplementerModel().getCategoriesModel()));
            TABLE_ITEMS_CONTRACT.get(chosenImplementer.getImplementerModel().getCategoriesModel()).clear();
            refreshCategoriesTable();

        } else {
            availableImplTable.getSelectionModel().clearSelection();
            Set<ImplementersTableItem> removedItemSet = new HashSet<>();
            availableImplementersObsList.forEach(item -> {
                if (item.getImplementerModel().getCategoriesModel().equals(chosenImplementer.getImplementerModel().getCategoriesModel())) {
                    removedItemSet.add(item);
                }
            });
            TABLE_ITEMS_CONTRACT.put(chosenImplementer.getImplementerModel().getCategoriesModel(), removedItemSet);
            availableImplementersObsList.removeAll(TABLE_ITEMS_CONTRACT.get(chosenImplementer.getImplementerModel().getCategoriesModel()));
            chosenImplementersItemObsList.add(chosenImplementer);
            refreshCategoriesTable();
        }
    }


    @FXML
    void acceptButtonOnAction(ActionEvent event) {
        if (!chosenImplementersItemObsList.isEmpty()) {
            List<Integer> implementersIDs = new ArrayList<>();
            chosenImplementersItemObsList.forEach(implementersTableItem -> implementersIDs.add(implementersTableItem.getImplementerModel().getIdImplementers()));
            orderHelper.acceptOrderById(orderModel.getIdOrder(), implementersIDs, this);
        }
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        getDialogStage().close();
    }

    @Override
    public void onOrderImplementersResponseSuccess(List<ImplementerModel> implementerModelList) {
        implementerModelList.forEach(implementerModel -> availableImplementersObsList.add(new ImplementersTableItem(implementerModel)));
    }

    @Override
    public void onOrderImplementersResponseFailed(ResponseModel responseModel) {
    }


    @Override
    public void onAcceptOrderSuccessResponse(ResponseModel responseModel) {
        getDialogStage().close();
    }

    @Override
    public void onAcceptOrderFailedResponse(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder();
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));

        WarningDialog.showWarningDialog(responseModel.getMessage(), builder.toString());
    }
}
