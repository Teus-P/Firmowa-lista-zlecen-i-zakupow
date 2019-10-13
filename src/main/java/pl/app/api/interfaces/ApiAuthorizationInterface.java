package pl.app.api.interfaces;

import okhttp3.RequestBody;
import pl.app.api.model.TokenModel;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiAuthorizationInterface {

    @Multipart
    @POST("/oauth/token")
    Call<TokenModel> getAccessToken(
            @Part("grant_type") RequestBody grantType,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );


    @Multipart
    @POST("/oauth/token")
    Call<TokenModel> refreshAccessToken(
            @Part("grant_type") RequestBody grantType,
            @Part("refresh_token") RequestBody refreshToken
    );

}
