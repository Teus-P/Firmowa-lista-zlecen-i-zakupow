package pl.app.api.helpers.common;


import com.google.gson.Gson;
import pl.app.api.model.ResponseModel;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;


public class CallExecutor {

    /**
     * Execute synchronously call
     *
     * @param call call to execute
     * @param <T>  generic type
     * @return T
     */
    public static <T> T execute(Call<T> call) {

        Response<T> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                return response.body();
            } else {
                Gson gson = new Gson();
                return (T) gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
