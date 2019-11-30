package pl.app.api.responseInterfaces;

import pl.app.api.model.OrderModel;
import pl.app.api.model.ResponseModel;

import java.util.List;

public interface CurrentUserOrdersResponseListener {

    void onCurrentUserOrdersSuccessResponse(List<OrderModel> orderModelList);

    void onCurrentUserOrdersFailedResponse(ResponseModel responseModel);

}
