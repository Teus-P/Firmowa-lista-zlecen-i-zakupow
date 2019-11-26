package pl.app.api.responseInterfaces;

import pl.app.api.model.ResponseModel;
import pl.app.api.model.UserAccountModel;

public interface NewUserResponseListener {
    void onNewUserResponseSuccess(UserAccountModel userAccountModel);

    void onNewUserResponseFailed(ResponseModel responseModel);
}
