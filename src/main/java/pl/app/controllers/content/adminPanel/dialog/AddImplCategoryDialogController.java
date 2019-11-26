package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.helpers.ImplementersHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.UserAccountModel;
import pl.app.controllers.common.listViewItems.CategoryCell;
import pl.app.core.baseComponent.BaseDialog;

import java.util.ArrayList;

public class AddImplCategoryDialogController extends BaseDialog {

    private CategoriesHelper categoriesHelper;
    private ImplementersHelper implementersHelper;
    private ObservableList<CategoriesModel> allCategoriesModelObservableList;
    private ObservableList<CategoriesModel> chosenCategoriesModelObservableList;
    private UserAccountModel userAccountModel;

    @FXML
    private JFXListView<CategoriesModel> chosenCategoriesListView;

    @FXML
    private JFXListView<CategoriesModel> allCategoriesListView;

    @FXML
    private Label responseLabel;


    public void initData(UserAccountModel userAccountModel) {
        this.userAccountModel = userAccountModel;
        initHelpers();
        initObjects();
        initAllCategoriesListView();
        initChosenCategoriesListView();
    }

    private void initObjects() {
        allCategoriesModelObservableList = FXCollections.observableArrayList();
        chosenCategoriesModelObservableList = FXCollections.observableArrayList();
    }

    private void initHelpers() {

        categoriesHelper = new CategoriesHelper(ApiResourcesClient.getApi());
        implementersHelper = new ImplementersHelper(ApiResourcesClient.getApi());

    }

    private void initAllCategoriesListView() {

        allCategoriesModelObservableList.addAll(categoriesHelper.getAllCategories());
        allCategoriesListView.setCellFactory(param -> new CategoryCell());
        allCategoriesListView.getItems().addAll(allCategoriesModelObservableList);
        allCategoriesListView.setItems(allCategoriesModelObservableList);

    }

    private void initChosenCategoriesListView() {

        chosenCategoriesListView.setItems(chosenCategoriesModelObservableList);
        chosenCategoriesListView.setCellFactory(param -> new CategoryCell());


    }

    @FXML
    void choseCategoryOnAction(ActionEvent event) {

        CategoriesModel chosenCategory = allCategoriesListView.getSelectionModel().getSelectedItem();

        if (chosenCategory != null) {
            allCategoriesListView.getSelectionModel().clearSelection();
            allCategoriesModelObservableList.remove(chosenCategory);
            chosenCategoriesModelObservableList.add(chosenCategory);
        }

    }

    @FXML
    void deleteChosenCategoryOnAction(ActionEvent event) {
        CategoriesModel deletedCategory = chosenCategoriesListView.getSelectionModel().getSelectedItem();
        if (deletedCategory != null) {
            chosenCategoriesListView.getSelectionModel().clearSelection();
            chosenCategoriesModelObservableList.remove(deletedCategory);
            allCategoriesModelObservableList.addAll(deletedCategory);
        }
    }


    @FXML
    void saveButtonOnAction(ActionEvent event) {


        if (!chosenCategoriesModelObservableList.isEmpty()) {
            implementersHelper.assignCategoriesToImplementer(userAccountModel.getIdUserAccount(), new ArrayList<>(chosenCategoriesModelObservableList));
            getDialogStage().close();
        } else {
            responseLabel.setText("Proszę wybrać minimum jedną kategorię");
        }

    }


}
