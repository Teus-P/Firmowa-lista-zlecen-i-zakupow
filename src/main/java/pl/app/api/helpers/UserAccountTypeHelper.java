package pl.app.api.helpers;

import lombok.AllArgsConstructor;
import pl.app.api.helpers.common.CallExecutor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.UserAccountTypeModel;
import retrofit2.Call;

import java.util.List;

@AllArgsConstructor
public class UserAccountTypeHelper {

    private ApiResourceInterface api;

    public List<UserAccountTypeModel> getExtraAccountTypes() {
        Call<List<UserAccountTypeModel>> call = api.getExtraAccountType();

        return CallExecutor.execute(call);

    }


}
