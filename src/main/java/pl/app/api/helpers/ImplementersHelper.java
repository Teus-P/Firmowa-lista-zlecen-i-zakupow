package pl.app.api.helpers;

import lombok.AllArgsConstructor;
import pl.app.api.helpers.common.CallExecutor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.ImplementerModel;
import pl.app.api.model.ResponseModel;
import retrofit2.Call;

import java.util.List;

@AllArgsConstructor
public class ImplementersHelper {

    private ApiResourceInterface apiResourceInterface;


    public ResponseModel addNewImplementer(int userId, int categoryId) {
        Call<ResponseModel> call = apiResourceInterface.addNewImplementers(userId, categoryId);

        return CallExecutor.execute(call);

    }

    public List<ImplementerModel> getAllImplementers() {

        Call<List<ImplementerModel>> call = apiResourceInterface.getAllImplementers();

        return CallExecutor.execute(call);
    }

}
