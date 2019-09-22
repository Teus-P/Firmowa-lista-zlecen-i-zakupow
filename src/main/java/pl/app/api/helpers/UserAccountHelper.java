package pl.app.api.helpers;


import pl.app.api.RestInterface;
import pl.app.api.model.UserAccountModel;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

public class UserAccountHelper {

    private RestInterface api;

    public UserAccountHelper(RestInterface api) {
        this.api = api;
    }




    public List<UserAccountModel> getAllUsers() {

        List<UserAccountModel> userAccountList = null;
        Call<List<UserAccountModel>> call = api.getUsers();

        try {
            userAccountList = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userAccountList;
    }

}
