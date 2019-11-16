package pl.app.api.responseInterfaces.product;

import pl.app.api.model.ResponseModel;

public interface EditProductResponseListener {

    void onSuccessResponse(ResponseModel responseModel);

    void onFailedResponse(ResponseModel responseModel);

}
