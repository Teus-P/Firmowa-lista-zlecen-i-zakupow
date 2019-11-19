package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.helpers.ProductHelper;
import pl.app.api.helpers.UnitHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ProductModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UnitModel;
import pl.app.api.responseInterfaces.EditProductResponseListener;
import pl.app.controllers.common.CategoriesComboBoxInitializer;
import pl.app.controllers.common.UnitComboBoxInitializer;
import pl.app.core.baseComponent.BaseDialog;

import java.util.List;

public class EditProductDialogController extends BaseDialog implements EditProductResponseListener {

    private ProductModel productModel;
    private CategoriesHelper categoriesHelper;
    private UnitHelper unitHelper;
    private ProductHelper productHelper;
    private List<CategoriesModel> categoriesModelsList;
    private List<UnitModel> unitModelList;
    private ObservableList<UnitModel> unitModelObservableList;
    private ObservableList<CategoriesModel> categoriesModelObservableList;

    @FXML
    private JFXTextField productTextField;

    @FXML
    private JFXComboBox<CategoriesModel> categoriesComboBox;

    @FXML
    private JFXComboBox<UnitModel> unitComboBox;

    @FXML
    private Label responseMessage;


    public void initData(ProductModel productModel) {
        this.productModel = productModel;

        initHelpers();
        getData();
        initCategoriesComboBox();
        initUnitComboBox();
        initGuiValue();
    }

    private void initGuiValue() {
        productTextField.setText(productModel.getName());
        responseMessage.setText("");
        if (productModel.getCategories().isDeleted())
            categoriesComboBox.setPromptText("Kategoria produktu została usunięta");
        else
            categoriesComboBox.getSelectionModel().select(productModel.getCategories());

        if (productModel.getUnit().isDeleted())
            unitComboBox.setPromptText("Jednostka produktu została usunięta");
        else {
            unitComboBox.getSelectionModel().select(unitComboBox.getItems().indexOf(productModel.getUnit()));
        }


    }

    private void getData() {
        categoriesModelsList = categoriesHelper.getAllCategories();
        unitModelList = unitHelper.getAllUnits();
    }

    private void initHelpers() {
        categoriesHelper = new CategoriesHelper(ApiResourcesClient.getApi());
        unitHelper = new UnitHelper(ApiResourcesClient.getApi());
        productHelper = new ProductHelper(ApiResourcesClient.getApi());
    }


    private void initUnitComboBox() {

        unitModelObservableList = FXCollections.observableList(unitModelList);
        UnitComboBoxInitializer.init(unitComboBox, unitModelObservableList);

    }

    private void initCategoriesComboBox() {

        categoriesModelObservableList = FXCollections.observableList(categoriesModelsList);
        CategoriesComboBoxInitializer.init(categoriesComboBox, categoriesModelObservableList);

    }


    @FXML
    void saveProductOnAction(ActionEvent event) {

        if (unitComboBox.getSelectionModel().getSelectedIndex() >= 0) {
            productModel.setUnit(unitComboBox.getValue());
        }

        if (categoriesComboBox.getSelectionModel().getSelectedIndex() >= 0) {
            productModel.setCategories(categoriesComboBox.getValue());
        }

        if (productTextField.getText().length() > 0) {
            productTextField.getText();
            productModel.setName(productTextField.getText());
        }

        productHelper.editProductById(productModel.getIdProduct(), productModel, this);

    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        getDialogStage().close();
    }


    @Override
    public void onSuccessResponse(ResponseModel responseModel) {
        getDialogStage().close();
    }


    @Override
    public void onFailedResponse(ResponseModel responseModel) {
        buildResponseMessage(responseModel);
    }

    private void buildResponseMessage(ResponseModel responseModel) {
        var stringBuilder = new StringBuilder();
        responseModel.getDetails().forEach(message -> stringBuilder.append(message).append("\n"));

        responseMessage.setText(stringBuilder.toString());
    }


}
