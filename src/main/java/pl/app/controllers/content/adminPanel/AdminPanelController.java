package pl.app.controllers.content.adminPanel;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import kotlin.Unit;
import org.controlsfx.control.textfield.TextFields;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.helpers.ProductHelper;
import pl.app.api.helpers.UnitHelper;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.api.model.*;
import pl.app.controllers.content.adminPanel.dialog.EditUserDialog;
import pl.app.launch.LaunchApp;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {

    private ResourceBundle stringResources;
    private CategoriesHelper categoriesHelper;
    private UnitHelper unitHelper;
    private ProductHelper productHelper;
    private UserAccountHelper userAccountHelper;
    private List<CategoriesModel> categoriesModelsList;
    private List<UnitModel> unitModelList;
    private ObservableList<UserAccountModel> userAccountModelObservableList;

    @FXML
    private JFXComboBox<CategoriesModel> categoriesComboBox;

    @FXML
    private JFXComboBox<UnitModel> unitComboBox;

    @FXML
    private JFXTextField productTextField;

    @FXML
    private Text responseText;

    @FXML
    private JFXListView<UserAccountModel> usersListView;

    @FXML
    private JFXButton addNewUserButton;

    @FXML
    private JFXButton editUserButton;

    @FXML
    private AnchorPane container;

    public AdminPanelController() {
        categoriesHelper = new CategoriesHelper(ApiResourcesClient.getApi());
        unitHelper = new UnitHelper(ApiResourcesClient.getApi());
        productHelper = new ProductHelper(ApiResourcesClient.getApi());
        userAccountHelper = new UserAccountHelper(ApiResourcesClient.getApi());
        userAccountModelObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;

        categoriesModelsList = categoriesHelper.getAllCategories();
        unitModelList = unitHelper.getAllUnits();

        userAccountModelObservableList.addAll(userAccountHelper.getAllUsers());

        initCategoriesComboBox();
        initUnitComboBox();
        initUserAccountListView();

    }

    private void initUserAccountListView() {
        usersListView.setItems(userAccountModelObservableList);
        usersListView.setCellFactory(factory -> new UserAccountViewCell());


        usersListView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                EditUserDialog editUserDialog = new EditUserDialog(usersListView.getSelectionModel().getSelectedItem());
                editUserDialog.showAndWait();
            }
        });

    }

    private void initUnitComboBox() {

        ObservableList<UnitModel> unitModelObservableList = FXCollections.observableList(unitModelList);
        unitComboBox.setItems(unitModelObservableList);
        // TextFields.bindAutoCompletion(unitComboBox.getEditor(), unitComboBox.getItems());

        unitComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(UnitModel object) {
                if (object == null)
                    return null;
                else
                    return object.getUnit();
            }

            @Override
            public UnitModel fromString(String string) {
                return new UnitModel(string);
            }
        });

    }

    private void initCategoriesComboBox() {

        ObservableList<CategoriesModel> categoriesModelObservableList = FXCollections.observableList(categoriesModelsList);
        categoriesComboBox.setItems(categoriesModelObservableList);
        TextFields.bindAutoCompletion(categoriesComboBox.getEditor(), categoriesComboBox.getItems());

        categoriesComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(CategoriesModel object) {
                if (object == null)
                    return null;
                else
                    return object.getName();
            }

            @Override
            public CategoriesModel fromString(String string) {
                return new CategoriesModel(string);
            }
        });
    }

    @FXML
    void addProductOnAction(ActionEvent event) {

        ProductModel productModel = new ProductModel();
        productModel.setCategories(categoriesComboBox.getValue());
        productModel.setName(productTextField.getText());
        productModel.setUnit(unitComboBox.getValue());

        ResponseModel responseModel = productHelper.createNewProduct(productModel);

        StringBuilder responseMessage = new StringBuilder();

        responseModel.getDetails().forEach(message -> responseMessage.append(message).append("\n"));
        responseText.setText(responseMessage.toString());

        responseText.setVisible(true);
        unitComboBox.setValue(null);
        categoriesComboBox.setValue(null);
        productTextField.setText("");

    }


    @FXML
    void addNewUserOnAction(ActionEvent event) {

    }


    @FXML
    void editUserOnAction(ActionEvent event) {

        EditUserDialog editUserDialog = new EditUserDialog(usersListView.getSelectionModel().getSelectedItem());
        editUserDialog.showAndWait();

    }


}