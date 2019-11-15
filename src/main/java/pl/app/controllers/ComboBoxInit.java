package pl.app.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.CategoriesHelper;
import pl.app.api.helpers.ProductHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ProductModel;

import java.util.List;

public class ComboBoxInit {

    private List<CategoriesModel> categoriesModelsList;
    private List<ProductModel> productModelsList;

    private CategoriesHelper categoriesHelper;
    private ProductHelper productHelper;

    public ComboBoxInit(){
        categoriesHelper = new CategoriesHelper(ApiResourcesClient.getApi());
        categoriesModelsList = categoriesHelper.getAllCategories();

        productHelper = new ProductHelper(ApiResourcesClient.getApi());
        productModelsList = productHelper.getAllProducts();
    }

    public void initCategoriesComboBox(JFXComboBox<CategoriesModel> categoriesComboBox) {

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

    public void initProductComboBox(JFXComboBox<ProductModel> productComboBox) {

        ObservableList<ProductModel> productModelObservableList = FXCollections.observableList(productModelsList);
        productComboBox.setItems(productModelObservableList);
        TextFields.bindAutoCompletion(productComboBox.getEditor(), productComboBox.getItems());

        productComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(ProductModel object) {
                if (object == null)
                    return null;
                else
                    return object.getName();
            }

            @Override
            public ProductModel fromString(String string) {
                return new ProductModel(string);
            }
        });
    }
}
