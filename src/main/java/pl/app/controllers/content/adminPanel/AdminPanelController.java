package pl.app.controllers.content.adminPanel;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.*;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UnitModel;
import pl.app.api.responseInterfaces.NewCategoryResponseListener;
import pl.app.api.responseInterfaces.NewUnitResponseListener;
import pl.app.controllers.content.adminPanel.dialog.*;
import pl.app.controllers.content.adminPanel.listItems.*;
import pl.app.core.dialog.DialogStage;
import pl.app.core.property.DialogProperty;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable, NewUnitResponseListener, NewCategoryResponseListener {

    private ResourceBundle stringResources;
    private ProductHelper productHelper;
    private UserAccountHelper userAccountHelper;
    private UnitHelper unitHelper;
    private CategoriesHelper categoriesHelper;
    private OrderHelper orderHelper;
    private ObservableList<UserTableItem> userTableItemObservableList;
    private ObservableList<ProductTableItem> productTableItemObservableList;
    private ObservableList<UnitTableItem> unitTableItemObservableList;
    private ObservableList<CategoryTableItem> categoryTableItemObservableList;
    private ObservableList<OrderTableItem> orderTableItemObservableList;
    private DialogStage newUserDialog;
    private DialogStage editProductDialog;
    private DialogStage editUserDialog;
    private DialogStage newProductDialog;

    //user tab

    @FXML
    private JFXTextField userSearchField;

    @FXML
    private JFXTreeTableView<UserTableItem> userTable;

    //end section

    //Product tab

    @FXML
    private JFXTextField productSearchField;

    @FXML
    private JFXTreeTableView<ProductTableItem> productTable;

    //end section

    //Unit tab

    @FXML
    private JFXTextField unitSearchField;

    @FXML
    private JFXTreeTableView<UnitTableItem> unitTable;

    @FXML
    private JFXTextField newUnitTextField;

    @FXML
    private Label unitResponseLabel;

    //end section

    //category tab

    @FXML
    private JFXTextField categorySearchField;

    @FXML
    private JFXTreeTableView<CategoryTableItem> categoryTable;

    @FXML
    private Label categoryResponseLabel;

    @FXML
    private JFXTextField newCategoryTextField;

    //end section

    //order tab

    @FXML
    private JFXTextField orderSearchField;

    @FXML
    private JFXTreeTableView<OrderTableItem> orderTable;

    //end section

    public AdminPanelController() {
        productHelper = new ProductHelper(ApiResourcesClient.getApi());
        userAccountHelper = new UserAccountHelper(ApiResourcesClient.getApi());
        unitHelper = new UnitHelper(ApiResourcesClient.getApi());
        categoriesHelper = new CategoriesHelper(ApiResourcesClient.getApi());
        orderHelper = new OrderHelper(ApiResourcesClient.getApi());
        userTableItemObservableList = FXCollections.observableArrayList();
        productTableItemObservableList = FXCollections.observableArrayList();
        unitTableItemObservableList = FXCollections.observableArrayList();
        categoryTableItemObservableList = FXCollections.observableArrayList();
        orderTableItemObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.stringResources = resources;

        initProductTreeTableView();
        initUserTreeTableView();
        initUnitTreeTableView();
        initCategoriesTreeTableView();
        initOrderTreeTableView();
        initDialogs();
    }

    @FXML
    void addProductOnAction(ActionEvent event) {
        newProductDialog.showAndWait();
    }

    @FXML
    void addNewUserOnAction(ActionEvent event) {
        newUserDialog.showAndWait();
    }

    @FXML
    void editUserOnAction(ActionEvent event) {

        if (userTable.getSelectionModel().getSelectedItem() != null) {

            showEditUserDialog();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Informacja");
            alert.setHeaderText("Proszę wybrać użytkownika do edycji");
            alert.showAndWait();
        }
    }

    @FXML
    void addNewUnitOnAction(ActionEvent event) {
        if (newUnitTextField.getText().length() > 0) {
            unitHelper.addNewUnit(new UnitModel(newUnitTextField.getText()), this);
            unitTableItemObservableList.clear();
            unitHelper.getAllUnits().forEach(unitModel -> unitTableItemObservableList.add(new UnitTableItem(unitModel)));
        } else {
            unitResponseLabel.setText("Nazwa nowej jednostki nie może być pusta.");
        }
    }

    @FXML
    void addCategoryOnAction(ActionEvent event) {
        if (newCategoryTextField.getText().length() > 0) {
            categoriesHelper.postNewCategory(new CategoriesModel(newCategoryTextField.getText()), this);
            categoryTableItemObservableList.clear();
            categoriesHelper.getAllCategories().forEach(categoriesModel -> categoryTableItemObservableList.add(new CategoryTableItem(categoriesModel)));

        } else {
            categoryResponseLabel.setText("Nazwa kategorii nie może być pusta");
        }
    }

    @FXML
    void deleteProductOnAction(ActionEvent event) {

        if (productTable.getSelectionModel().getSelectedItem() != null) {

            var productModel = productTable.getSelectionModel().getSelectedItem().getValue().getProductModel();
            String title = "Uwaga!";
            String header = "Czy na pewno chcesz usunąć produkt?";
            String content = "Produkt o następujących danych zostanie usunięty:\n\n" +
                    "Nazwa produktu: " + productModel.getName() + "\n" +
                    "Kategoria produktu: " + productModel.getCategories().getName() + "\n" +
                    "Jednostka produktu: " + productModel.getUnit().getUnit();

            boolean dialogResult = getDeleteDialogInformation(title, header, content);
            if (dialogResult) {
                productTable.getSelectionModel().getSelectedItem().getParent().getChildren().remove(productTable.getSelectionModel().getSelectedItem());
                productHelper.deleteProductById(productModel.getIdProduct());
            }

        } else {
            String header = "Uwaga";
            String contentText = "Proszę wybrać produkt do usunięcia";
            showWarningDialog(header, contentText);
        }
    }

    @FXML
    void editProductOnAction(ActionEvent event) {
        if (productTable.getSelectionModel().getSelectedItem() != null) {

            showEditProductDialog();

        } else {
            String header = "Uwaga";
            String contentText = "Proszę wybrać produkt do edycji";
            showWarningDialog(header, contentText);
        }

    }

    @FXML
    void deleteUnitOnAction(ActionEvent event) {
        if (unitTable.getSelectionModel().getSelectedItem() != null) {
            var unitModel = unitTable.getSelectionModel().getSelectedItem().getValue().getUnitModel();
            String title = "Uwaga!";
            String header = "Czy na pewno chcesz usunąć jednostkę?";
            String content = "Jednostka o nazwie " + unitModel.getUnit() + " zostanie usunięta";
            boolean dialogResult = getDeleteDialogInformation(title, header, content);
            if (dialogResult) {
                unitTable.getSelectionModel().getSelectedItem().getParent().getChildren().remove(unitTable.getSelectionModel().getSelectedItem());
                unitHelper.deleteUnitById(unitModel.getIdUnit());
            }
        } else {
            String header = "Uwaga!";
            String contentText = "Proszę wybrać jednostke do usunięcia";
            showWarningDialog(header, contentText);
        }
    }

    @FXML
    void deleteCategoryOnAction(ActionEvent event) {
        if (categoryTable.getSelectionModel().getSelectedItem() != null) {
            var categoryModel = categoryTable.getSelectionModel().getSelectedItem().getValue().getCategoriesModel();
            String title = "Uwaga!";
            String header = "Czy na pewno chcesz usunąć jednostkę?";
            String content = "Kategoria o nazwie " + categoryModel.getName() + " zostanie usunięta";
            boolean dialogResult = getDeleteDialogInformation(title, header, content);
            if (dialogResult) {
                categoryTable.getSelectionModel().getSelectedItem().getParent().getChildren().remove(categoryTable.getSelectionModel().getSelectedItem());
                categoriesHelper.deleteCategoryById(categoryModel.getIdCategories());
            }
        } else {
            String header = "Uwaga!";
            String contentText = "Proszę wybrać kategorię do usunięcia";
            showWarningDialog(header, contentText);
        }
    }

    private void initProductTreeTableView() {
        productHelper.getAllProducts().forEach(model -> productTableItemObservableList.add(new ProductTableItem(model)));

        JFXTreeTableColumn<ProductTableItem, String> productColumn = new JFXTreeTableColumn<>("Nazwa produktu");
        productColumn.setCellValueFactory(param -> param.getValue().getValue().getProduct());

        JFXTreeTableColumn<ProductTableItem, String> categoryColumn = new JFXTreeTableColumn<>("Kategoria");
        categoryColumn.setCellValueFactory(param -> param.getValue().getValue().getCategory());

        JFXTreeTableColumn<ProductTableItem, String> unitColumn = new JFXTreeTableColumn<>("Jednostka");
        unitColumn.setCellValueFactory(param -> param.getValue().getValue().getUnit());


        final TreeItem<ProductTableItem> root = new RecursiveTreeItem<>(productTableItemObservableList, RecursiveTreeObject::getChildren);
        productTable.getColumns().setAll(productColumn, categoryColumn, unitColumn);
        productTable.setRoot(root);
        productTable.setShowRoot(false);


        productSearchField.textProperty().addListener((observable, oldValue, newValue) ->
                productTable.setPredicate(productTableItemTreeItem
                        -> productTableItemTreeItem.getValue().getProduct().getValue().contains(newValue)
                        || productTableItemTreeItem.getValue().getCategory().getValue().contains(newValue)
                        || productTableItemTreeItem.getValue().getUnit().getValue().contains(newValue)));

        productTable.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                showEditProductDialog();
            }
        });

    }

    private void initUserTreeTableView() {
        userAccountHelper.getAllUsers().forEach(model -> userTableItemObservableList.add(new UserTableItem(model)));

        JFXTreeTableColumn<UserTableItem, String> firstNameColumn = new JFXTreeTableColumn<>("Imię");
        firstNameColumn.setCellValueFactory(param -> param.getValue().getValue().getFirstName());

        JFXTreeTableColumn<UserTableItem, String> lastNameColumn = new JFXTreeTableColumn<>("Nazwisko");
        lastNameColumn.setCellValueFactory(param -> param.getValue().getValue().getLastName());

        JFXTreeTableColumn<UserTableItem, String> usernameColumn = new JFXTreeTableColumn<>("Nazwa użytkownika");
        usernameColumn.setCellValueFactory(param -> param.getValue().getValue().getUserLogin());

        JFXTreeTableColumn<UserTableItem, String> emailColumn = new JFXTreeTableColumn<>("Email");
        emailColumn.setCellValueFactory(param -> param.getValue().getValue().getEmail());

        JFXTreeTableColumn<UserTableItem, String> phoneNumberColumn = new JFXTreeTableColumn<>("Numer telefonu");
        phoneNumberColumn.setCellValueFactory(param -> param.getValue().getValue().getPhoneNumber());

        JFXTreeTableColumn<UserTableItem, String> userTypeColumn = new JFXTreeTableColumn<>("Typ użytkownika");
        userTypeColumn.setCellValueFactory(param -> param.getValue().getValue().getRole());

        final TreeItem<UserTableItem> root = new RecursiveTreeItem<>(userTableItemObservableList, RecursiveTreeObject::getChildren);
        userTable.getColumns().setAll(usernameColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn, userTypeColumn);
        userTable.setRoot(root);
        userTable.setShowRoot(false);

        userSearchField.textProperty().addListener((observable, oldValue, newValue) ->
                userTable.setPredicate(table
                        -> table.getValue().getLastName().getValue().contains(newValue)
                        || table.getValue().getFirstName().getValue().contains(newValue)
                        || table.getValue().getRole().getValue().contains(newValue)
                        || table.getValue().getUserLogin().getValue().contains(newValue)
                        || table.getValue().getEmail().getValue().contains(newValue)));

        userTable.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                showEditUserDialog();
            }
        });

    }

    private void initUnitTreeTableView() {

        unitHelper.getAllUnits().forEach(unitModel -> unitTableItemObservableList.add(new UnitTableItem(unitModel)));

        JFXTreeTableColumn<UnitTableItem, String> unitNameColumn = new JFXTreeTableColumn<>("Nazwa jednostki");
        unitNameColumn.setCellValueFactory(param -> param.getValue().getValue().getUnit());
        unitNameColumn.setCellFactory((TreeTableColumn<UnitTableItem, String> param) -> new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder()));

        unitNameColumn.setOnEditCommit(event -> {

            UnitTableItem unitTableItem = event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue();

            unitTableItem.setUnit(event.getNewValue());

            ResponseModel responseModel = unitHelper.editUnitById(unitTableItem.getUnitModel().getIdUnit(), unitTableItem.getUnitModel());

            //rollback
            if (responseModel.getCode() != 200) {
                StringBuilder builder = new StringBuilder();
                responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
                unitTableItem.setUnit(event.getOldValue());
                unitResponseLabel.setText(builder.toString());
                int position = event.getTreeTablePosition().getRow();
                unitTableItemObservableList.clear();
                unitHelper.getAllUnits().forEach(item -> unitTableItemObservableList.add(new UnitTableItem(item)));
                unitTable.getSelectionModel().select(position);
            }

        });


        final TreeItem<UnitTableItem> root = new RecursiveTreeItem<>(unitTableItemObservableList, RecursiveTreeObject::getChildren);
        unitTable.getColumns().setAll(unitNameColumn);
        unitTable.setRoot(root);
        unitTable.setShowRoot(false);

        unitSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            unitTable.setPredicate(table -> table.getValue().getUnit().getValue().contains(newValue));
        });

    }

    private void initCategoriesTreeTableView() {

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

    //TODO - do dokończenia
    private void initOrderTreeTableView() {
        orderHelper.getAllOrders().forEach(orderModel -> orderTableItemObservableList.add(new OrderTableItem(orderModel)));

        JFXTreeTableColumn<OrderTableItem, String> orderNumberColumn = new JFXTreeTableColumn<>("Numer zamówienia");
        orderNumberColumn.setCellValueFactory(param -> param.getValue().getValue().getOrderNumber());

        JFXTreeTableColumn<OrderTableItem, String> userNameColumn = new JFXTreeTableColumn<>("Użytkownik");
        userNameColumn.setCellValueFactory(param -> param.getValue().getValue().getUserName());

        JFXTreeTableColumn<OrderTableItem, String> orderAcceptStatusColumn = new JFXTreeTableColumn<>("Status akceptacji");
        orderAcceptStatusColumn.setCellValueFactory(param -> param.getValue().getValue().getAcceptedStatus());

        final TreeItem<OrderTableItem> root = new RecursiveTreeItem<>(orderTableItemObservableList, RecursiveTreeObject::getChildren);
        orderTable.getColumns().setAll(orderNumberColumn, userNameColumn, orderAcceptStatusColumn);
        orderTable.setRoot(root);
        orderTable.setShowRoot(false);
    }


    private void showEditUserDialog() {
        EditUserDialogController controller = editUserDialog.getController();
        controller.initData(userTable.getSelectionModel().getSelectedItem().getValue().getUserAccountModel());
        editUserDialog.showAndWait();
    }

    private void showEditProductDialog() {
        EditProductDialogController controller = editProductDialog.getController();
        controller.initData(productTable.getSelectionModel().getSelectedItem().getValue().getProductModel());
        controller.setOnDialogCloseListener(() -> {
            productTableItemObservableList.clear();
            productHelper.getAllProducts().forEach(model -> productTableItemObservableList.add(new ProductTableItem(model)));
        });
        editProductDialog.showAndWait();
    }

    private void showWarningDialog(String headerText, String contentText) {
        var warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setHeaderText(headerText);
        warningAlert.setContentText(contentText);
        warningAlert.showAndWait();
    }

    private boolean getDeleteDialogInformation(String title, String header, String content) {

        var wrapper = new Object() {
            boolean buttonClickFlag;
        };
        var delete = new ButtonType("Usuń");
        var cancel = new ButtonType("Anuluj");
        var alert = new Alert(Alert.AlertType.NONE, "", delete, cancel);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait().ifPresent(response -> {
            if (response == delete) {
                wrapper.buttonClickFlag = true;
            }
        });
        return wrapper.buttonClickFlag;
    }

    private void initDialogs() {
        newUserDialog = new DialogStage(DialogProperty.NEW_USER);
        editProductDialog = new DialogStage(DialogProperty.EDIT_PRODUCT);
        editUserDialog = new DialogStage(DialogProperty.EDIT_USER);
        newProductDialog = new DialogStage(DialogProperty.NEW_PRODUCT);
    }

    @Override
    public void onNewUnitResponseSuccess(ResponseModel responseModel) {
        unitResponseLabel.setText(responseModel.getMessage());
    }

    @Override
    public void onNewUnitResponseFailed(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder(responseModel.getMessage() + "\n");
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        unitResponseLabel.setText(builder.toString());
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