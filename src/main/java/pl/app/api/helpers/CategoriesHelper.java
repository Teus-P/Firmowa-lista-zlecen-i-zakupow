package pl.app.api.helpers;

import com.google.gson.Gson;
import pl.app.api.helpers.common.CallExecutor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.responseInterfaces.NewCategoryResponseListener;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class CategoriesHelper {

    private ApiResourceInterface apiResourceInterface;

    public CategoriesHelper(ApiResourceInterface apiResourceInterface) {
        this.apiResourceInterface = apiResourceInterface;
    }

    public List<CategoriesModel> getAllCategories() {
        Call<List<CategoriesModel>> call = apiResourceInterface.getAllCategories();

        return CallExecutor.execute(call);

    }

    public void postNewCategory(CategoriesModel categoriesModel, NewCategoryResponseListener listener) {
        Call<ResponseModel> call = apiResourceInterface.createNewCategory(categoriesModel);
        Response<ResponseModel> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                listener.onNewCategoryResponseSuccess(response.body());
            } else {
                Gson gson = new Gson();
                listener.onNewCategoryResponseFailed(gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseModel deleteCategoryById(int categoryId) {
        Call<ResponseModel> call = apiResourceInterface.deleteCategoryById(categoryId);

        return CallExecutor.execute(call);
    }

    public ResponseModel editCategoryById(int categoryId, CategoriesModel categoriesModel) {
        Call<ResponseModel> call = apiResourceInterface.editCategoryById(categoryId, categoriesModel);

        return CallExecutor.execute(call);
    }

}
