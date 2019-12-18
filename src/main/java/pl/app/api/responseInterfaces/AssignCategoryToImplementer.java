package pl.app.api.responseInterfaces;

import pl.app.api.model.ResponseModel;

public interface AssignCategoryToImplementer {

    void onAssignCategoryToImplementerSuccessResponse(ResponseModel responseModel);

    void onAssignCategoryToImplementerResponseFailed(ResponseModel responseModel);
}
