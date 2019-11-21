package pl.app.api.helpers;

import pl.app.api.helpers.common.CallExecutor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.OrderModel;
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

}
