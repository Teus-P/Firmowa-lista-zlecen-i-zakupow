package pl.app.api.helpers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.UserAccountTypeModel;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class UserAccountTypeHelper {

    private ApiResourceInterface api;


    public List<UserAccountTypeModel> getAllAccountTypes(){
        Call<List<UserAccountTypeModel>> call = api.getAllAccountType();

        Response<List<UserAccountTypeModel>> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                return response.body();
            } else {
                Gson gson = new Gson();

                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
