package pl.app.controllers.content.adminPanel.dialog;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.helpers.ProductHelper;
import pl.app.api.helpers.UnitHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ProductModel;
import pl.app.api.model.UnitModel;
import pl.app.core.ResourceLoader;
import pl.app.core.property.DialogProperty;
import pl.app.launch.LaunchApp;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditProductDialog extends Stage implements Initializable {

    private ResourceLoader resourceLoader = ResourceLoader.getInstance();
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initHelpers();
        initData();
        initCategoriesComboBox();
        initUnitComboBox();
        initGuiValue();

    }

    public EditProductDialog(ProductModel productModel) {
        this.productModel = productModel;
        initView();


    }

    private void initGuiValue() {
        productTextField.setText(productModel.getName());


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

    private void initView() {

        FXMLLoader fxmlLoader = resourceLoader.fxmlLoader(DialogProperty.EDIT_PRODUCT.getDialogFxmlPath());
        fxmlLoader.setController(this);

        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UTILITY);
        setMinWidth(1280);
        setMinHeight(720);
        setTitle(DialogProperty.EDIT_PRODUCT.getDialogTitle());
        try {
            setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setOnCloseRequest(e -> {
            LaunchApp.getPrimaryStage().getScene().getRoot().setEffect(null);
            close();
        });
    }

    private void initData() {
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
        productHelper.editProductById(productModel.getIdProduct(), productModel);
        //TODO - dodać odpowiedź że pomyślnie edytowano i przeładować ponownie listę + wrócić do widoku listy
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        LaunchApp.getPrimaryStage().getScene().getRoot().setEffect(null);
        close();
    }


}
