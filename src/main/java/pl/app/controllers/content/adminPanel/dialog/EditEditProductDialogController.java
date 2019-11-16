package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.helpers.ProductHelper;
import pl.app.api.helpers.UnitHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ProductModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UnitModel;
import pl.app.api.responseInterfaces.product.EditProductResponseListener;
import pl.app.core.baseComponent.BaseDialog;
import pl.app.core.dialog.OnDialogCloseListener;

import java.util.List;

public class EditEditProductDialogController extends BaseDialog implements EditProductResponseListener {

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
        unitComboBox.setItems(unitModelObservableList);
        // TextFields.bindAutoCompletion(unitComboBox.getEditor(), unitComboBox.getItems());

        unitComboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<UnitModel> call(ListView<UnitModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(UnitModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getUnit());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

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

        categoriesModelObservableList = FXCollections.observableList(categoriesModelsList);
        categoriesComboBox.setItems(categoriesModelObservableList);
        //TextFields.bindAutoCompletion(categoriesComboBox.getEditor(), categoriesComboBox.getItems());

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
    void saveProductOnAction(ActionEvent event) {

        if (unitComboBox.getSelectionModel().getSelectedIndex() > 0) {
            productModel.setUnit(unitComboBox.getItems().get(unitComboBox.getSelectionModel().getSelectedIndex()));
        }

        if (categoriesComboBox.getSelectionModel().getSelectedIndex() > 0) {
            productModel.setCategories(categoriesComboBox.getItems().get(categoriesComboBox.getSelectionModel().getSelectedIndex()));
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
        onDialogCloseListener.onClose();
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
