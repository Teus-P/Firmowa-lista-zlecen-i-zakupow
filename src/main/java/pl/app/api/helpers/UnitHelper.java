package pl.app.api.helpers;

import lombok.AllArgsConstructor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.UnitModel;
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

}
