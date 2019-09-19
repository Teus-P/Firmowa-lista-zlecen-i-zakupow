package pl.app.api.helpers;

import pl.app.api.RestInterface;
import pl.app.api.model.TokenModel;
import retrofit2.Call;

import java.io.IOException;

public class TokenHelper {

    private RestInterface api;

    public TokenHelper(RestInterface api) {
        this.api = api;
    }

    public TokenModel getToken(String username, String password) throws IOException {
        Call<TokenModel> call = api.getToken(username, password);
        return call.execute().body();
    }


}
