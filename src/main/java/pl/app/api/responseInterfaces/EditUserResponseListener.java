package pl.app.api.responseInterfaces;

import pl.app.api.model.ResponseModel;

public interface EditUserResponseListener {


    void onEditUserSuccessResponse(ResponseModel responseModel);

    void onEditUserFailedResponse(ResponseModel responseModel);

}
