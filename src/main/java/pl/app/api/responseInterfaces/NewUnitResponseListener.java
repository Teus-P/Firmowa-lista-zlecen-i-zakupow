package pl.app.api.responseInterfaces;

import pl.app.api.model.ResponseModel;

public interface NewUnitResponseListener {

    void onNewUnitResponseSuccess(ResponseModel responseModel);

    void onNewUnitResponseFailed(ResponseModel responseModel);

}
