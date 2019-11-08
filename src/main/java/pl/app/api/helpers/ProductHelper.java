package pl.app.api.helpers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.ProductModel;
import pl.app.api.model.ResponseModel;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDateTime;

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

}
