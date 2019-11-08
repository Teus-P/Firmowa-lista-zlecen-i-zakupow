package pl.app.api.clients;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.app.api.authenticator.TokenAuthenticator;
import pl.app.api.interceptors.DynamicHostInterceptor;
import pl.app.api.interceptors.TokenAuthorizationInterceptor;
import pl.app.api.interfaces.ApiResourceInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.logging.Logger;

public final class ApiResourcesClient extends ApiConfiguration {

    private static final Logger LOGGER = Logger.getLogger(ApiAuthorizationClient.class.getName());
    private static final ApiResourcesClient REST_INSTANCE = new ApiResourcesClient();
    private static ApiResourceInterface apiResourceInterface;
    private TokenAuthorizationInterceptor tokenAuthorizationInterceptor;
    private TokenAuthenticator tokenAuthenticator;

    {
        httpLoggingInterceptor = new HttpLoggingInterceptor();
        dynamicHostInterceptor = new DynamicHostInterceptor(HOST_BASE_URL);
        tokenAuthenticator = new TokenAuthenticator();
        tokenAuthorizationInterceptor = new TokenAuthorizationInterceptor();
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(dynamicHostInterceptor)
                .addInterceptor(tokenAuthorizationInterceptor)
                .authenticator(tokenAuthenticator)
                .build();
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HOST_BASE_URL)
                .client(okHttpClient)
                .build();
        apiResourceInterface = retrofit.create(ApiResourceInterface.class);
    }

    private ApiResourcesClient() {
    }

    public static ApiResourceInterface getApi() {
        return apiResourceInterface;
    }

    public static ApiResourcesClient getInstance() {
        return REST_INSTANCE;
    }

}
