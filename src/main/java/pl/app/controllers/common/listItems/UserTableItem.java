package pl.app.controllers.common.listItems;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
import pl.app.api.model.UserAccountModel;

@Getter
@Setter
public class UserTableItem extends RecursiveTreeObject<UserTableItem> {

    private int userId;
    private UserAccountModel userAccountModel;

    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty userLogin;
    private StringProperty email;
    private StringProperty phoneNumber;
    private StringProperty role;

    public UserTableItem(UserAccountModel userAccountModel) {
        this.userAccountModel = userAccountModel;
        this.firstName = new SimpleStringProperty(userAccountModel.getFirstName());
        this.lastName = new SimpleStringProperty(userAccountModel.getLastName());
        this.userLogin = new SimpleStringProperty(userAccountModel.getUsername());
        this.email = new SimpleStringProperty(userAccountModel.getEmail());
        this.phoneNumber = new SimpleStringProperty(userAccountModel.getPhoneNumber());
        setRole();
        this.userId = userAccountModel.getIdUserAccount();
    }

    private void setRole() {
        StringBuilder builder = new StringBuilder();
        userAccountModel.getUserAccountTypeModels().forEach(role -> builder.append(role.getName()).append("\n"));
        this.role = new SimpleStringProperty(builder.toString());
    }
}
