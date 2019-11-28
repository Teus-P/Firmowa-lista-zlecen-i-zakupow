package pl.app.api.responseInterfaces;

import pl.app.api.model.ResponseModel;

public interface AcceptOrderResponseListener {

    void onAcceptOrderSuccessResponse(ResponseModel responseModel);

    void onAcceptOrderFailedResponse(ResponseModel responseModel);
}
