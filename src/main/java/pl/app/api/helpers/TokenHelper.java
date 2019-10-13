package pl.app.api.helpers;

import okhttp3.RequestBody;
import pl.app.api.interfaces.ApiAuthorizationInterface;
import pl.app.api.model.TokenModel;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class TokenHelper {

    private ApiAuthorizationInterface api;

    public TokenHelper(ApiAuthorizationInterface api) {
        this.api = api;
    }

    public TokenModel getAccessToken(RequestBody grantType, RequestBody username, RequestBody password) {

        Call<TokenModel> call = api.getAccessToken(grantType, username, password);
        Response<TokenModel> response = null;

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
