package pl.app.api;

import pl.app.api.model.TokenModel;
import pl.app.api.model.UserAccountModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface RestInterface {

    @GET("/users")
    Call<List<UserAccountModel>> getUsers();

    @FormUrlEncoded
    @POST("/auth")
    Call<TokenModel> getToken(
            @Field("username") String username,
            @Field("password") String password);
}
