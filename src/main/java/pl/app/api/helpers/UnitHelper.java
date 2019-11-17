package pl.app.api.helpers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UnitModel;
import pl.app.api.responseInterfaces.NewUnitResponseListener;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class UnitHelper {

    private ApiResourceInterface apiResourceInterface;


    public List<UnitModel> getAllUnits() {
        Call<List<UnitModel>> call = apiResourceInterface.getAllUnits();

        Response<List<UnitModel>> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                return response.body();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addNewUnit(UnitModel unitModel, NewUnitResponseListener listener) {
        Call<ResponseModel> call = apiResourceInterface.createNewUnit(unitModel);

        Response<ResponseModel> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                listener.onNewUnitResponseSuccess(response.body());
            } else {
                Gson gson = new Gson();
                listener.onNewUnitResponseFailed(gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ResponseModel deleteUnitById(int unitId) {
        Call<ResponseModel> call = apiResourceInterface.deleteUnitById(unitId);

        Response<ResponseModel> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                return response.body();
            } else {
                Gson gson = new Gson();
                return (gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
