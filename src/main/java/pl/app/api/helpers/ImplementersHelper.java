package pl.app.api.helpers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import pl.app.api.helpers.common.CallExecutor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ImplementerModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.responseInterfaces.ImplementerCategoriesResponseListener;
import pl.app.api.responseInterfaces.OrderImplementersResponseListener;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
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


    public void getImplementerCategory(int userId, ImplementerCategoriesResponseListener listener) {

        Call<List<CategoriesModel>> call = apiResourceInterface.getImplementerCategories(userId);

        Response<List<CategoriesModel>> response = null;

        try {
            response = call.execute();

            if (response.isSuccessful() && response.code() == 200) {
                listener.onImplementerCategoriesSuccessResponse(response.body());
            } else {
                Gson gson = new Gson();
                listener.onImplementerCategoriesFailedResponse(gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseModel assignCategoriesToImplementer(int userId, List<CategoriesModel> categoriesId) {
        Call<ResponseModel> call = apiResourceInterface.assignManyCategoriesToImplementer(userId, categoriesId);

        return CallExecutor.execute(call);
    }

    public void getImplementersForOrder(int orderId, OrderImplementersResponseListener listener) {
        Call<List<ImplementerModel>> call = apiResourceInterface.getImplementersForOrder(orderId);
        Response<List<ImplementerModel>> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                listener.onOrderImplementersResponseSuccess(response.body());
            } else {
                Gson gson = new Gson();
                listener.onOrderImplementersResponseFailed(gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
