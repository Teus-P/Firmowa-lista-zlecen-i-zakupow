package pl.app.api.responseInterfaces;

import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ResponseModel;

public interface ImplementerCategoriesResponseListener {

    void onImplementerCategoriesSuccessResponse(CategoriesModel categoriesModelList);

    void onImplementerCategoriesFailedResponse(ResponseModel responseModel);

}
