package pl.app.api.responseInterfaces;

import pl.app.api.model.ResponseModel;

public interface LoginResponseListener {
    void onFailedServerConnection(ResponseModel errorResponse);
}
