package pl.app.controllers.content.adminPanel.tabs;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.UnitHelper;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UnitModel;
import pl.app.api.responseInterfaces.NewUnitResponseListener;
import pl.app.controllers.common.popupDialogs.DeleteDialogInformation;
import pl.app.controllers.common.popupDialogs.WarningDialog;
import pl.app.controllers.common.listItems.UnitTableItem;

import java.net.URL;
import java.util.ResourceBundle;

public class UnitsTabPageController implements Initializable, NewUnitResponseListener {

    private UnitHelper unitHelper;
    private ObservableList<UnitTableItem> unitTableItemObservableList;

    @FXML
    private JFXTextField unitSearchField;

    @FXML
    private JFXTreeTableView<UnitTableItem> unitTable;

    @FXML
    private JFXTextField newUnitTextField;

    @FXML
    private Label unitResponseLabel;

    public UnitsTabPageController() {
        unitHelper = new UnitHelper(ApiResourcesClient.getApi());
        unitTableItemObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUnitTreeTableView();

    }

    private void initUnitTreeTableView() {

        unitHelper.getAllUnits().forEach(unitModel -> unitTableItemObservableList.add(new UnitTableItem(unitModel)));

        JFXTreeTableColumn<UnitTableItem, String> unitNameColumn = new JFXTreeTableColumn<>("Nazwa jednostki");
        unitNameColumn.setCellValueFactory(param -> param.getValue().getValue().getUnit());
        unitNameColumn.setCellFactory((TreeTableColumn<UnitTableItem, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));

        unitNameColumn.setOnEditCommit(event -> {

            UnitTableItem unitTableItem = event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue();

            unitTableItem.setUnit(event.getNewValue());

            ResponseModel responseModel = unitHelper.editUnitById(unitTableItem.getUnitModel().getIdUnit(), unitTableItem.getUnitModel());

            //rollback
            if (responseModel.getCode() != 200) {
                StringBuilder builder = new StringBuilder();
                responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
                unitTableItem.setUnit(event.getOldValue());
                unitResponseLabel.setText(builder.toString());
                int position = event.getTreeTablePosition().getRow();
                unitTableItemObservableList.clear();
                unitHelper.getAllUnits().forEach(item -> unitTableItemObservableList.add(new UnitTableItem(item)));
                unitTable.getSelectionModel().select(position);
            }

        });

        final TreeItem<UnitTableItem> root = new RecursiveTreeItem<>(unitTableItemObservableList, RecursiveTreeObject::getChildren);
        unitTable.getColumns().setAll(unitNameColumn);
        unitTable.setRoot(root);
        unitTable.setShowRoot(false);
        unitTable.setPlaceholder(new Label("Brak jednostek w systemie"));

        unitSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            unitTable.setPredicate(table -> table.getValue().getUnit().getValue().toLowerCase().contains(newValue.toLowerCase()));
        });
    }


    @FXML
    void addNewUnitOnAction(ActionEvent event) {
        if (newUnitTextField.getText().length() > 0) {
            unitHelper.addNewUnit(new UnitModel(newUnitTextField.getText()), this);
            unitTableItemObservableList.clear();
            unitHelper.getAllUnits().forEach(unitModel -> unitTableItemObservableList.add(new UnitTableItem(unitModel)));
        } else {
            unitResponseLabel.setText("Nazwa nowej jednostki nie może być pusta.");
        }
    }

    @FXML
    void deleteUnitOnAction(ActionEvent event) {
        if (unitTable.getSelectionModel().getSelectedItem() != null) {
            var unitModel = unitTable.getSelectionModel().getSelectedItem().getValue().getUnitModel();
            String title = "Uwaga!";
            String header = "Czy na pewno chcesz usunąć jednostkę?";
            String content = "Jednostka o nazwie " + unitModel.getUnit() + " zostanie usunięta";
            boolean dialogResult = DeleteDialogInformation.showDeleteDialogInformation(title, header, content);
            if (dialogResult) {
                unitTable.getSelectionModel().getSelectedItem().getParent().getChildren().remove(unitTable.getSelectionModel().getSelectedItem());
                unitHelper.deleteUnitById(unitModel.getIdUnit());
            }
        } else {
            String header = "Uwaga!";
            String contentText = "Proszę wybrać jednostke do usunięcia";
            WarningDialog.showWarningDialog(header, contentText);
        }
    }

    @Override
    public void onNewUnitResponseSuccess(ResponseModel responseModel) {
        unitResponseLabel.setText(responseModel.getMessage());
    }

    @Override
    public void onNewUnitResponseFailed(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder(responseModel.getMessage() + "\n");
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        unitResponseLabel.setText(builder.toString());
    }


}
