package pl.app.api.helpers;

import com.google.gson.Gson;
import pl.app.api.helpers.common.CallExecutor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.OrderModel;
import pl.app.api.model.OrderProductModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.responseInterfaces.AcceptOrderResponseListener;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class OrderHelper {

    private ApiResourceInterface apiResourceInterface;

    public OrderHelper(ApiResourceInterface apiResourceInterface) {
        this.apiResourceInterface = apiResourceInterface;
    }


    public List<OrderModel> getAllUserOrders() {
        Call<List<OrderModel>> call = apiResourceInterface.getMyAllOrders();

        return CallExecutor.execute(call);

    }


    public List<OrderModel> getAllOrders() {
        Call<List<OrderModel>> call = apiResourceInterface.getAllOrders();

        return CallExecutor.execute(call);
    }

    public ResponseModel createNewOrder(List<OrderProductModel> orderProductList) {

        Call<ResponseModel> call = apiResourceInterface.createNewOrder(orderProductList);

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


    public void acceptOrderById(int orderId, List<Integer> implementersId, AcceptOrderResponseListener listener) {
        Call<ResponseModel> call = apiResourceInterface.acceptOrderById(orderId, implementersId);

        Response<ResponseModel> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                listener.onAcceptOrderSuccessResponse(response.body());
            } else {
                Gson gson = new Gson();
                listener.onAcceptOrderFailedResponse(gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
