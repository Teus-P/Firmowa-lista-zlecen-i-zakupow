package pl.app.controllers.common.checkComboBoxItem;

import lombok.Getter;
import pl.app.api.model.UserAccountTypeModel;

public class UserTypeCheckBoxItem {

    @Getter
    private UserAccountTypeModel userAccountTypeModel;

    public UserTypeCheckBoxItem(UserAccountTypeModel userAccountTypeModel) {
        this.userAccountTypeModel = userAccountTypeModel;
    }

    @Override
    public String toString() {
        return userAccountTypeModel.getName();
    }
}
