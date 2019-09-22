package pl.app.api;

import com.google.gson.internal.GsonBuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import pl.app.api.interceptors.DynamicHostInterceptor;
import pl.app.api.interceptors.HeaderInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RestClient {

    private static HttpLoggingInterceptor httpLoggingInterceptor;
    private static DynamicHostInterceptor dynamicHostInterceptor;
    private static HeaderInterceptor headerInterceptor;
    private static RestInterface restInterface;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    private static final RestClient instance = new RestClient();

    private static final String HOST_BASE_URL = "http://127.0.0.1";

    private RestClient() {

    }

    static {
        httpLoggingInterceptor = new HttpLoggingInterceptor();
        dynamicHostInterceptor = new DynamicHostInterceptor();
        headerInterceptor = new HeaderInterceptor();
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(dynamicHostInterceptor)
                .addInterceptor(headerInterceptor)
                .build();
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HOST_BASE_URL)
                .client(okHttpClient)
                .build();
        restInterface = retrofit.create(RestInterface.class);
    }


    public static RestInterface getApi() {
        return restInterface;
    }

    public static RestClient getInstance() {
        return instance;
    }

    public static void setHost(String host) {
        dynamicHostInterceptor.setHost(host);
    }

    public static void setToken(String token) {
        headerInterceptor.setToken(token);
    }

    public static void clearHeaderToken() {
        headerInterceptor.clearToken();
    }

}
