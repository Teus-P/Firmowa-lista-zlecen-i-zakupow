package pl.app.api.responseInterfaces;

import pl.app.api.model.ResponseModel;

public interface NewCategoryResponseListener {

    void onNewCategoryResponseSuccess(ResponseModel responseModel);

    void onNewCategoryResponseFailed(ResponseModel responseModel);

}
