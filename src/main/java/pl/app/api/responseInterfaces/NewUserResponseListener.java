package pl.app.api.responseInterfaces;

import pl.app.api.model.ResponseModel;

public interface NewUserResponseListener {
    void onNewUserResponseSuccess(ResponseModel responseModel);

    void onNewUserResponseFailed(ResponseModel responseModel);
}
