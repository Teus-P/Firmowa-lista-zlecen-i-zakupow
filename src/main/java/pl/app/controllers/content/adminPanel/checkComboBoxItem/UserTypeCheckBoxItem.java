package pl.app.controllers.content.adminPanel.checkComboBoxItem;

import pl.app.api.model.UserAccountTypeModel;

public class UserTypeCheckBoxItem {

    private UserAccountTypeModel userAccountTypeModel;

    public UserTypeCheckBoxItem(UserAccountTypeModel userAccountTypeModel) {
        this.userAccountTypeModel = userAccountTypeModel;
    }

    @Override
    public String toString() {
        return userAccountTypeModel.getName();
    }
}
