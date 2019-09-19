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

    public List<UserAccountModel> getAllUsers() throws IOException {
        Call<List<UserAccountModel>> call = api.getUsers();

        return call.execute().body();
    }


}


/*public class UserAccountHelper implements UserAccountInterfaceAsyncExample

    //example async call execute

    @Override
    public Call<List<UserAccountModel>> getAllUsers(OnResponse onResponse) {
        Call<List<UserAccountModel>> call = api.getUsers();
        call.enqueue(new Callback<List<UserAccountModel>>() {
            @Override
            public void onResponse(Call<List<UserAccountModel>> call, Response<List<UserAccountModel>> response) {
                if (response.isSuccessful()) {
                    onResponse.userAccountListRetrived(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<UserAccountModel>> call, Throwable throwable) {
                    throwable.printStackTrace();
            }
        });

        return call;

    }*/
