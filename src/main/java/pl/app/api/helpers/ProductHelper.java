package pl.app.api.helpers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.ProductModel;
import pl.app.api.model.ResponseModel;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class ProductHelper {

    private ApiResourceInterface apiResourceInterface;


    public ResponseModel createNewProduct(ProductModel productModel) {

        Call<ResponseModel> call = apiResourceInterface.createNewProduct(productModel);

        Response<ResponseModel> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                return response.body();
            } else {
                Gson gson = new Gson();

                return gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class);
            }
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public List<ProductModel> getAllProducts() {
        Call<List<ProductModel>> call = apiResourceInterface.getAllProducts();

        Response<List<ProductModel>> response = null;

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

    public ResponseModel deleteProductById(int productId) {
        Call<ResponseModel> call = apiResourceInterface.deleteProductById(productId);

        Response<ResponseModel> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                return response.body();
            } else {
                Gson gson = new Gson();

                return gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class);
            }
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }


    public ResponseModel editProductById(int productId, ProductModel productModel) {
        Call<ResponseModel> call = apiResourceInterface.editProductyById(productId, productModel);

        Response<ResponseModel> response = null;
        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                return response.body();
            } else {
                Gson gson = new Gson();

                return gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class);
            }
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

}
