package pl.app.api.helpers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import pl.app.api.helpers.common.CallExecutor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.UserAccountTypeModel;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class UserAccountTypeHelper {

    private ApiResourceInterface api;


    public List<UserAccountTypeModel> getAllAccountTypes() {
        Call<List<UserAccountTypeModel>> call = api.getAllAccountType();

        return CallExecutor.execute(call);
    }


    public List<UserAccountTypeModel> getExtraAccountTypes() {
        Call<List<UserAccountTypeModel>> call = api.getExtraAccountType();

        return CallExecutor.execute(call);

    }


}
