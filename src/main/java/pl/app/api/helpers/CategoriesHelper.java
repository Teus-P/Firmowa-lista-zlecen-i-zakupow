package pl.app.api.helpers;

import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ResponseModel;
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

        Response<List<CategoriesModel>> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                return response.body();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseModel postNewCategory(CategoriesModel categoriesModel){
        Call<ResponseModel> call = apiResourceInterface.createNewCategory(categoriesModel);
        Response<ResponseModel> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                return response.body();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
