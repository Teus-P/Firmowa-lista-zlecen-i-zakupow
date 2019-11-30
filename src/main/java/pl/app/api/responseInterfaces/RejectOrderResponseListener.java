package pl.app.api.responseInterfaces;

import pl.app.api.model.ResponseModel;

public interface RejectOrderResponseListener {

    void onRejectOrderSuccessResponse(ResponseModel responseModel);

    void onRejectOrderFailedResponse(ResponseModel responseModel);

}
