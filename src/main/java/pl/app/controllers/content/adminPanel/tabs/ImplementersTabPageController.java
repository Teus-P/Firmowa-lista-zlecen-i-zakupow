package pl.app.controllers.content.adminPanel.tabs;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.ImplementersHelper;
import pl.app.controllers.common.listItems.CategoryTableItem;
import pl.app.controllers.common.listItems.ImplementersTableItem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Skeleton for 'ImplementersTabPage.fxml' Controller Class
 */
public class ImplementersTabPageController implements Initializable {

    private ImplementersHelper implementersHelper;
    private ObservableList<ImplementersTableItem> implementersTableItemObservableList;

    @FXML
    private JFXTreeTableView implementersTable;

    public ImplementersTabPageController() {
        implementersHelper = new ImplementersHelper(ApiResourcesClient.getApi());
        implementersTableItemObservableList = FXCollections.observableArrayList();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initImplementersTreeTable();
    }

    private void initData() {
        implementersHelper.getAllImplementers().forEach(implementerModel -> implementersTableItemObservableList.add(new ImplementersTableItem(implementerModel)));
    }

    private void initImplementersTreeTable() {
        initData();

        JFXTreeTableColumn<ImplementersTableItem, String> fullNameImplementerColumn = new JFXTreeTableColumn<>("Imię i nazwisko");
        fullNameImplementerColumn.setCellValueFactory(param -> param.getValue().getValue().getUserFullNameDisplayValue());
        fullNameImplementerColumn.setCellFactory((TreeTableColumn<ImplementersTableItem, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));

        JFXTreeTableColumn<ImplementersTableItem, String> categoryColumn = new JFXTreeTableColumn<>("Nazwa kategorii");
        categoryColumn.setCellValueFactory(param -> param.getValue().getValue().getCategoriesDisplayValue());
        categoryColumn.setCellFactory((TreeTableColumn<ImplementersTableItem, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));

        final TreeItem<ImplementersTableItem> root = new RecursiveTreeItem<>(implementersTableItemObservableList, RecursiveTreeObject::getChildren);
        implementersTable.getColumns().setAll(fullNameImplementerColumn, categoryColumn);
        implementersTable.setRoot(root);
        implementersTable.setShowRoot(false);

    }
}
