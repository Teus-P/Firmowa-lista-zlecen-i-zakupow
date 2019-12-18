package pl.app.api.helpers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import pl.app.api.helpers.common.CallExecutor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ImplementerModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.responseInterfaces.AssignCategoryToImplementer;
import pl.app.api.responseInterfaces.ImplementerCategoriesResponseListener;
import pl.app.api.responseInterfaces.OrderImplementersResponseListener;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class ImplementersHelper {

    private ApiResourceInterface apiResourceInterface;


    public void getImplementerCategory(int userId, ImplementerCategoriesResponseListener listener) {

        Call<CategoriesModel> call = apiResourceInterface.getImplementerCategories(userId);

        Response<CategoriesModel> response = null;

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

    public void assignCategoriesToImplementer(int userId, CategoriesModel categoriesModel, AssignCategoryToImplementer listener) {

        Call<ResponseModel> call = apiResourceInterface.assignManyCategoriesToImplementer(userId, categoriesModel);

        Response<ResponseModel> response = null;

        try {
            response = call.execute();

            if (response.isSuccessful() && response.code() == 200) {
                listener.onAssignCategoryToImplementerSuccessResponse(response.body());
            } else {
                Gson gson = new Gson();
                listener.onAssignCategoryToImplementerResponseFailed(gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public ResponseModel editImplementerCategory(int userId, int categoryId) {
        Call<ResponseModel> call = apiResourceInterface.editImplementerCategory(userId, categoryId);

        return CallExecutor.execute(call);
    }

}
