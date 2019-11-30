package pl.app.api.authenticator;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.app.api.UserSession;
import pl.app.api.clients.ApiAuthorizationClient;
import pl.app.api.model.TokenModel;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.logging.Logger;

public class TokenAuthenticator implements Authenticator {

    private static final Logger LOGGER = Logger.getLogger(TokenAuthenticator.class.getName());

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NotNull okhttp3.Response response) {

        if (response.code() == 401) {

            RequestBody requestBodyGrantType = RequestBody.create(MediaType.parse("multipart/form-data"), "refresh_token");
            RequestBody requestBodyRefreshToken = RequestBody.create(MediaType.parse("multipart/form-data"), UserSession.getRefreshToken());

            LOGGER.info("REFRESH TOKEN: " + UserSession.getRefreshToken());

            Call<TokenModel> call = ApiAuthorizationClient.getApi().refreshAccessToken(requestBodyGrantType, requestBodyRefreshToken);
            Response<TokenModel> refreshTokenResponse = null;

            try {
                refreshTokenResponse = call.execute();
                if (refreshTokenResponse.isSuccessful()) {
                    //save new token
                    String accessToken = refreshTokenResponse.body().getAccessToken();

                    LOGGER.info("NEW TOKEN AFTER REFRESH " + accessToken);

                    UserSession.setAccessToken(accessToken);

                    return response.request()
                            .newBuilder()
                            .header("Authorization", "Bearer" + accessToken)
                            .build();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }

    }

}
