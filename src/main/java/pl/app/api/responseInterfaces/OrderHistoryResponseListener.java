package pl.app.api.responseInterfaces;

import pl.app.api.model.OrderModel;
import pl.app.api.model.ResponseModel;

import java.util.List;

public interface OrderHistoryResponseListener {

    void onOrderHistorySuccessResponse(List<OrderModel> orderModelList);

    void onOrderHistoryFailedResponse(ResponseModel responseModel);
}
