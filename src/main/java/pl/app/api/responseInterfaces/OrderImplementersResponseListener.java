package pl.app.api.responseInterfaces;

import pl.app.api.model.ImplementerModel;
import pl.app.api.model.ResponseModel;

import java.util.List;

public interface OrderImplementersResponseListener {

    void onOrderImplementersResponseSuccess(List<ImplementerModel> implementerModelList);

    void onOrderImplementersResponseFailed(ResponseModel responseModel);
}
