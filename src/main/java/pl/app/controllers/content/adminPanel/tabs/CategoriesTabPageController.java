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
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.responseInterfaces.NewCategoryResponseListener;
import pl.app.controllers.common.popupDialogs.DeleteDialogInformation;
import pl.app.controllers.common.popupDialogs.WarningDialog;
import pl.app.controllers.common.listItems.CategoryTableItem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CategoriesTabPageController implements Initializable, NewCategoryResponseListener {

    private CategoriesHelper categoriesHelper;
    private ObservableList<CategoryTableItem> categoryTableItemObservableList;


    @FXML
    private JFXTextField categorySearchField;

    @FXML
    private JFXTreeTableView<CategoryTableItem> categoryTable;

    @FXML
    private Label categoryResponseLabel;

    @FXML
    private JFXTextField newCategoryTextField;


    public CategoriesTabPageController() {
        categoriesHelper = new CategoriesHelper(ApiResourcesClient.getApi());
        categoryTableItemObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCategoriesTreeTableView();
    }

    private void initCategoriesTreeTableView() {

        List<CategoriesModel> categoriesModels = categoriesHelper.getAllCategories();
        if (!categoriesModels.isEmpty())
            categoriesHelper.getAllCategories().forEach(categoriesModel -> categoryTableItemObservableList.add(new CategoryTableItem(categoriesModel)));

        JFXTreeTableColumn<CategoryTableItem, String> categoryColumn = new JFXTreeTableColumn<>("Nazwa kategorii");
        categoryColumn.setCellValueFactory(param -> param.getValue().getValue().getCategoryName());
        categoryColumn.setCellFactory((TreeTableColumn<CategoryTableItem, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));

        categoryColumn.setOnEditCommit(event -> {

            CategoryTableItem categoryTableItem = event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue();
            categoryTableItem.setCategoryName(event.getNewValue());

            ResponseModel responseModel = categoriesHelper.editCategoryById(categoryTableItem.getCategoriesModel().getIdCategories(), categoryTableItem.getCategoriesModel());

            //rollback
            if (responseModel.getCode() != 200) {
                StringBuilder builder = new StringBuilder();
                responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
                categoryTableItem.setCategoryName(event.getOldValue());
                categoryResponseLabel.setText(builder.toString());
                int position = event.getTreeTablePosition().getRow();
                categoryTableItemObservableList.clear();
                categoriesHelper.getAllCategories().forEach(item -> categoryTableItemObservableList.add(new CategoryTableItem(item)));
                categoryTable.getSelectionModel().select(position);
            }

        });

        final TreeItem<CategoryTableItem> root = new RecursiveTreeItem<>(categoryTableItemObservableList, RecursiveTreeObject::getChildren);
        categoryTable.getColumns().setAll(categoryColumn);
        categoryTable.setRoot(root);
        categoryTable.setShowRoot(false);

        categorySearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            categoryTable.setPredicate(table -> table.getValue().getCategoryName().getValue().contains(newValue));
        });
    }


    @FXML
    void addCategoryOnAction(ActionEvent event) {
        if (newCategoryTextField.getText().length() > 0) {
            categoriesHelper.postNewCategory(new CategoriesModel(newCategoryTextField.getText()), this);
            categoryTableItemObservableList.clear();
            categoriesHelper.getAllCategories().forEach(categoriesModel -> categoryTableItemObservableList.add(new CategoryTableItem(categoriesModel)));
            newCategoryTextField.clear();

        } else {
            categoryResponseLabel.setText("Nazwa kategorii nie może być pusta");
        }
    }


    @FXML
    void deleteCategoryOnAction(ActionEvent event) {
        if (categoryTable.getSelectionModel().getSelectedItem() != null) {
            var categoryModel = categoryTable.getSelectionModel().getSelectedItem().getValue().getCategoriesModel();
            String title = "Uwaga!";
            String header = "Czy na pewno chcesz usunąć jednostkę?";
            String content = "Kategoria o nazwie " + categoryModel.getName() + " zostanie usunięta";
            boolean dialogResult = DeleteDialogInformation.showDeleteDialogInformation(title, header, content);
            if (dialogResult) {
                categoryTable.getSelectionModel().getSelectedItem().getParent().getChildren().remove(categoryTable.getSelectionModel().getSelectedItem());
                categoriesHelper.deleteCategoryById(categoryModel.getIdCategories());
            }
        } else {
            String header = "Uwaga!";
            String contentText = "Proszę wybrać kategorię do usunięcia";
            WarningDialog.showWarningDialog(header, contentText);
        }
    }

    @Override
    public void onNewCategoryResponseSuccess(ResponseModel responseModel) {
        categoryResponseLabel.setText(responseModel.getMessage());
    }

    @Override
    public void onNewCategoryResponseFailed(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder(responseModel.getMessage() + "\n");
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        categoryResponseLabel.setText(builder.toString());
    }

}
