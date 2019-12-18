package pl.app.api.clients;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.app.api.interceptors.DynamicHostInterceptor;
import retrofit2.Retrofit;

abstract class ApiConfiguration {

    static final String HOST_BASE_URL = "http://192.168.8.143";
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    DynamicHostInterceptor dynamicHostInterceptor = new DynamicHostInterceptor(HOST_BASE_URL);
    OkHttpClient okHttpClient = null;
    Retrofit retrofit = null;


    static final String CLIENT_LOGIN = "restApi";
    static final String CLIENT_SECRET = "JDJhJDA4JGg0THZMdldtZHRJWm8uTkNRVERuTk9aeHZPbVQyL3RSY0dOT0dJRnpMTnl3L0tXcEliTG9X";

}
