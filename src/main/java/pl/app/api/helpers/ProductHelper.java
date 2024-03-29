package pl.app.api.helpers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import pl.app.api.helpers.common.CallExecutor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.ProductModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.responseInterfaces.EditProductResponseListener;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class ProductHelper {

    private ApiResourceInterface apiResourceInterface;


    public ResponseModel createNewProduct(ProductModel productModel) {

        Call<ResponseModel> call = apiResourceInterface.createNewProduct(productModel);

        return CallExecutor.execute(call);
    }

    public List<ProductModel> getAllProducts() {
        Call<List<ProductModel>> call = apiResourceInterface.getAllProducts();

        return CallExecutor.execute(call);
    }

    public ResponseModel deleteProductById(int productId) {
        Call<ResponseModel> call = apiResourceInterface.deleteProductById(productId);

        return CallExecutor.execute(call);
    }


    public void editProductById(int productId, ProductModel productModel, EditProductResponseListener responseListener) {
        Call<ResponseModel> call = apiResourceInterface.editProductById(productId, productModel);

        Response<ResponseModel> response = null;
        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                responseListener.onSuccessResponse(response.body());
            } else {
                Gson gson = new Gson();
                responseListener.onFailedResponse(gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
