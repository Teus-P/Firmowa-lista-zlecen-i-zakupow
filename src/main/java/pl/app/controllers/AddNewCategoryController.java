package pl.app.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ResponseModel;
import pl.app.core.screenController.ControlledScreen;
import pl.app.core.screenController.ScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewCategoryController implements Initializable, ControlledScreen {

    private ScreenController screenController;
    private ResourceBundle stringResources;

    private CategoriesModel categoriesModel;
    private Alert loginAlert;

    private CategoriesHelper categoriesHelper;

    @FXML
    private JFXTextField categoryTextField;

    @FXML
    private JFXButton categoryButton;

    public AddNewCategoryController() {
        loginAlert = new Alert(Alert.AlertType.ERROR);
        categoriesModel = new CategoriesModel();
        categoriesHelper = new CategoriesHelper(ApiResourcesClient.getApi());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @FXML
    private void onClickCategoryButton() {


        if (categoryTextField.getText() != null && categoryTextField.getText().length() > 0) {

            categoriesModel.setName(categoryTextField.getText());
            ResponseModel responseModel = categoriesHelper.postNewCategory(categoriesModel);
            StringBuilder details = new StringBuilder();

            responseModel.getDetails().forEach(e -> details.append("\n").append(e));

            loginAlert.setContentText(details.toString());
            loginAlert.setHeaderText(responseModel.getMessage());
            loginAlert.setTitle(responseModel.getMessage());

            loginAlert.show();
        } else {
            loginAlert.setContentText("Brak nazwy");
            loginAlert.showAndWait();
        }

    }
}
