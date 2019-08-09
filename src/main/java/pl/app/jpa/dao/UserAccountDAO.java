package pl.app.jpa.dao;

import pl.app.jpa.model.UserAccount;

public class UserAccountDAO {

    public Integer addUserAccount(UserAccount userAccount) {
        return ObjectCrudDAO.addObject(userAccount);
    }
}
