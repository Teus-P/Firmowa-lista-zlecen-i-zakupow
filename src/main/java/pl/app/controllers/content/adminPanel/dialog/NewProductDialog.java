package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.helpers.ProductHelper;
import pl.app.api.helpers.UnitHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ProductModel;
import pl.app.api.model.UnitModel;
import pl.app.core.baseComponent.BaseDialog;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewProductDialog extends BaseDialog {

    private CategoriesHelper categoriesHelper;
    private UnitHelper unitHelper;
    private ProductHelper productHelper;
    private List<CategoriesModel> categoriesModelsList;
    private List<UnitModel> unitModelList;

    @FXML
    private JFXTextField productTextField;

    @FXML
    private JFXComboBox<CategoriesModel> categoriesComboBox;

    @FXML
    private JFXComboBox<UnitModel> unitComboBox;

    @FXML
    private Label responseLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initHelpers();
        initData();
        initCategoriesComboBox();
        initUnitComboBox();
    }


    private void initHelpers() {
        categoriesHelper = new CategoriesHelper(ApiResourcesClient.getApi());
        unitHelper = new UnitHelper(ApiResourcesClient.getApi());
        productHelper = new ProductHelper(ApiResourcesClient.getApi());
    }


    private void initData() {
        categoriesModelsList = categoriesHelper.getAllCategories();
        unitModelList = unitHelper.getAllUnits();
    }

    @FXML
    void addProductOnAction(ActionEvent event) {

        ProductModel productModel = new ProductModel();
        productModel.setCategories(categoriesComboBox.getValue());
        productModel.setName(productTextField.getText());
        productModel.setUnit(unitComboBox.getValue());

        var responseModel = productHelper.createNewProduct(productModel);

        var responseMessage = new StringBuilder();

        responseModel.getDetails().forEach(message -> responseMessage.append(message).append("\n"));
        responseLabel.setText(responseMessage.toString());

        responseLabel.setVisible(true);
        unitComboBox.setValue(null);
        categoriesComboBox.setValue(null);
        productTextField.setText("");
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        getDialogStage().close();
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
}
