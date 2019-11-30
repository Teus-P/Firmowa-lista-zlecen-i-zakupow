package pl.app.api.responseInterfaces;

import pl.app.api.model.ResponseModel;

public interface ChangePasswordResponseListener {

    void onChangePasswordSuccessResponse(ResponseModel responseModel);

    void onChangePasswordFailedResponse(ResponseModel responseModel);
}
