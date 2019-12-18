package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXButton;
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
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UserAccountModel;
import pl.app.api.responseInterfaces.AssignCategoryToImplementer;
import pl.app.controllers.common.listViewItems.CategoryCell;
import pl.app.core.baseComponent.BaseDialog;

public class AddImplCategoryDialogController extends BaseDialog implements AssignCategoryToImplementer {

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

    @FXML
    private JFXButton addImplCategoryButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;


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
            addImplCategoryButton.setDisable(true);
        }

    }

    @FXML
    void deleteChosenCategoryOnAction(ActionEvent event) {
        CategoriesModel deletedCategory = chosenCategoriesListView.getSelectionModel().getSelectedItem();
        if (deletedCategory != null) {
            chosenCategoriesListView.getSelectionModel().clearSelection();
            chosenCategoriesModelObservableList.remove(deletedCategory);
            allCategoriesModelObservableList.addAll(deletedCategory);
            addImplCategoryButton.setDisable(false);
        }
    }


    @FXML
    void saveButtonOnAction(ActionEvent event) {

        if (!chosenCategoriesModelObservableList.isEmpty()) {
            implementersHelper.assignCategoriesToImplementer(userAccountModel.getIdUserAccount(), chosenCategoriesModelObservableList.get(0), this);
        } else {
            responseLabel.setText("Proszę wybrać minimum jedną kategorię");
        }

    }

    @FXML
    void cancelButtonOnAction() {
        getDialogStage().close();
    }


    @Override
    public void onAssignCategoryToImplementerSuccessResponse(ResponseModel responseModel) {
        saveButton.setDisable(true);
        StringBuilder builder = new StringBuilder();
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        responseLabel.setText(builder.toString());
    }

    @Override
    public void onAssignCategoryToImplementerResponseFailed(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder();
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        responseLabel.setText(builder.toString());
    }
}
