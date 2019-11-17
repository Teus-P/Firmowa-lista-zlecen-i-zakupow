package pl.app.api.responseInterfaces;

import pl.app.api.model.ResponseModel;

public interface EditProductResponseListener {

    void onSuccessResponse(ResponseModel responseModel);

    void onFailedResponse(ResponseModel responseModel);

}
