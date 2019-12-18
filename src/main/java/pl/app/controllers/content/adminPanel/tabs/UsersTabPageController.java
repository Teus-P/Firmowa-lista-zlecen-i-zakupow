package pl.app.controllers.content.adminPanel.tabs;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.UserAccountTypeModel;
import pl.app.controllers.common.listItems.UserTableItem;
import pl.app.controllers.content.adminPanel.dialog.EditUserDialogController;
import pl.app.controllers.content.adminPanel.dialog.NewUserDialogController;
import pl.app.core.dialog.DialogStage;
import pl.app.core.property.StageProperty;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UsersTabPageController implements Initializable {

    private UserAccountHelper userAccountHelper;
    private ObservableList<UserTableItem> userTableItemObservableList;

    private static final String ADMIN_ROLE = "Role_ADMIN";
    private static final String USER_ROLE = "Role_USER";
    private static final String RECIPIENT_ROLE = "Role_RECIPIENT";
    private static final String IMPLEMENTERS_ROLE = "Role_IMPLEMENTERS";


    @FXML
    private JFXTextField userSearchField;

    @FXML
    private JFXTreeTableView<UserTableItem> userTable;


    public UsersTabPageController() {
        userAccountHelper = new UserAccountHelper(ApiResourcesClient.getApi());
        userTableItemObservableList = FXCollections.observableArrayList();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUserTreeTableView();
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


        JFXTreeTableColumn<UserTableItem, List<UserAccountTypeModel>> userTypeColumn = new JFXTreeTableColumn<>("Typ użytkownika");
        userTypeColumn.setCellFactory(new Callback<>() {
            @Override
            public TreeTableCell<UserTableItem, List<UserAccountTypeModel>> call(TreeTableColumn<UserTableItem, List<UserAccountTypeModel>> param) {
                final TreeTableCell<UserTableItem, List<UserAccountTypeModel>> cell = new TreeTableCell<>() {
                    @Override
                    protected void updateItem(List<UserAccountTypeModel> item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            VBox vBox = new VBox();
                            item.forEach(type -> {
                                Label label = new Label();

                                if (type.getName().equals(ADMIN_ROLE))
                                    label.setText("Administrator");

                                if (type.getName().equals(USER_ROLE))
                                    label.setText("Użytkownik");

                                if (type.getName().equals(RECIPIENT_ROLE))
                                    label.setText("Przyjmujący");

                                if (type.getName().equals(IMPLEMENTERS_ROLE))
                                    label.setText("Realizator");

                                label.setTextFill(Color.BLACK);
                                vBox.getChildren().add(label);
                            });
                            setGraphic(vBox);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        userTypeColumn.setCellValueFactory(param -> param.getValue().getValue().getUserAccountTypeModeObservable());


        JFXTreeTableColumn<UserTableItem, CategoriesModel> implementersCategoriesColumn = new JFXTreeTableColumn<>("Kategorie relizatora");
        implementersCategoriesColumn.setCellFactory(new Callback<>() {
            @Override
            public TreeTableCell<UserTableItem, CategoriesModel> call(TreeTableColumn<UserTableItem, CategoriesModel> param) {
                final TreeTableCell<UserTableItem, CategoriesModel> cell = new TreeTableCell<>() {

                    @Override
                    protected void updateItem(CategoriesModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            VBox vBox = new VBox();

                            Label label = new Label(item.getName());
                            label.setTextFill(Color.BLACK);
                            if (item.isDeleted()) {
                                label.getStylesheets().addAll(getClass().getResource("/styles/StrikethroughLabel.css").toExternalForm());
                            }
                            vBox.getChildren().add(label);


                            setGraphic(vBox);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        implementersCategoriesColumn.setCellValueFactory(param -> param.getValue().getValue().getImplementersCategoriesObservable());


        final TreeItem<UserTableItem> root = new RecursiveTreeItem<>(userTableItemObservableList, RecursiveTreeObject::getChildren);
        userTable.getColumns().setAll(usernameColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn, userTypeColumn, implementersCategoriesColumn);
        userTable.setRoot(root);
        userTable.setShowRoot(false);
        userTable.setPlaceholder(new Label("Brak użytkowników"));

        userSearchField.textProperty().addListener((observable, oldValue, newValue) ->
                userTable.setPredicate(table
                        -> table.getValue().getLastName().getValue().toLowerCase().contains(newValue.toLowerCase())
                        || table.getValue().getFirstName().getValue().toLowerCase().contains(newValue.toLowerCase())
                        || table.getValue().getRole().getValue().toLowerCase().contains(newValue.toLowerCase())
                        || table.getValue().getUserLogin().getValue().toLowerCase().contains(newValue.toLowerCase())
                        || table.getValue().getEmail().getValue().toLowerCase().contains(newValue.toLowerCase())));

        userTable.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                showEditUserDialog();
            }
        });

    }

    private void showNewUserDialog() {
        DialogStage newUserDialog = new DialogStage(StageProperty.NEW_USER);
        NewUserDialogController controller = newUserDialog.getController();
        controller.setOnDialogCloseListener(() -> {
            userTableItemObservableList.clear();
            userAccountHelper.getAllUsers().forEach(model -> userTableItemObservableList.add(new UserTableItem(model)));
        });
        newUserDialog.showAndWait();
    }

    private void showEditUserDialog() {
        DialogStage editUserDialog = new DialogStage(StageProperty.EDIT_USER);
        EditUserDialogController controller = editUserDialog.getController();
        controller.initData(userTable.getSelectionModel().getSelectedItem().getValue().getUserAccountModel());
        controller.setOnDialogCloseListener(() -> {
            userTableItemObservableList.clear();
            userAccountHelper.getAllUsers().forEach(model -> userTableItemObservableList.add(new UserTableItem(model)));
        });
        editUserDialog.showAndWait();
    }


    @FXML
    void addNewUserOnAction(ActionEvent event) {
        showNewUserDialog();
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


}
