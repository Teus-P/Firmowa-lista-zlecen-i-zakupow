package pl.app.api.clients;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.app.api.interceptors.DynamicHostInterceptor;
import retrofit2.Retrofit;

abstract class ApiConfiguration {

    static final String HOST_BASE_URL = "http://127.0.0.1:8088";
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    DynamicHostInterceptor dynamicHostInterceptor = new DynamicHostInterceptor(HOST_BASE_URL);
    OkHttpClient okHttpClient = null;
    Retrofit retrofit = null;


    //TODO - basic authorization api credicals
    // - do zmiany na hashe !
    // - przenieść dane to zewnetrznego plku ?
    static final String CLIENT_LOGIN = "desktop";
    static final String CLIENT_SECRET = "password";

}
