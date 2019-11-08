package pl.app.api.clients;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.app.api.interceptors.BasicAuthorizationInterceptor;
import pl.app.api.interceptors.DynamicHostInterceptor;
import pl.app.api.interfaces.ApiAuthorizationInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.logging.Logger;

public final class ApiAuthorizationClient extends ApiConfiguration {

    private static final Logger LOGGER = Logger.getLogger(ApiAuthorizationClient.class.getName());

    private static final ApiAuthorizationClient REST_INSTANCE = new ApiAuthorizationClient();
    private static ApiAuthorizationInterface apiAuthorizationInterface;
    private BasicAuthorizationInterceptor basicAuthorizationInterceptor;

    {
        httpLoggingInterceptor = new HttpLoggingInterceptor();
        dynamicHostInterceptor = new DynamicHostInterceptor(HOST_BASE_URL);
        basicAuthorizationInterceptor = new BasicAuthorizationInterceptor(CLIENT_LOGIN, CLIENT_SECRET);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(dynamicHostInterceptor)
                .addInterceptor(basicAuthorizationInterceptor)
                .build();
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HOST_BASE_URL)
                .client(okHttpClient)
                .build();
        apiAuthorizationInterface = retrofit.create(ApiAuthorizationInterface.class);
    }

    private ApiAuthorizationClient() {
    }

    public static ApiAuthorizationInterface getApi() {
        return apiAuthorizationInterface;
    }

    public static ApiAuthorizationClient getInstance() {
        return REST_INSTANCE;
    }

}
