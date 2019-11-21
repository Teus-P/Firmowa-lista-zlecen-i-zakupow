package pl.app.controllers.content.adminPanel.tabs;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.controllers.common.listItems.UserTableItem;
import pl.app.controllers.content.adminPanel.dialog.EditUserDialogController;
import pl.app.controllers.content.adminPanel.dialog.NewUserDialogController;
import pl.app.core.dialog.DialogStage;
import pl.app.core.property.DialogProperty;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersTabPageController implements Initializable {

    private UserAccountHelper userAccountHelper;
    private ObservableList<UserTableItem> userTableItemObservableList;


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

    private void showNewUserDialog() {
        DialogStage newUserDialog = new DialogStage(DialogProperty.NEW_USER);
        NewUserDialogController controller = newUserDialog.getController();
        controller.setOnDialogCloseListener(() -> {
            userTableItemObservableList.clear();
            userAccountHelper.getAllUsers().forEach(model -> userTableItemObservableList.add(new UserTableItem(model)));
        });
        newUserDialog.showAndWait();
    }

    private void showEditUserDialog() {
        DialogStage editUserDialog = new DialogStage(DialogProperty.EDIT_USER);
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
