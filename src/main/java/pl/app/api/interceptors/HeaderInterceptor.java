package pl.app.api.interceptors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HeaderInterceptor implements Interceptor {

    private String token;

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        System.out.println("INTER IN INTERCEPT METHOD");
        if (token != null) {
            request = request.newBuilder().header("Accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .build();
            System.out.println("TOKEN ISN'T NULL");
            System.out.println("TOKEN " + token);
        }else{
            System.out.println("TOKEN IS NULL");
        }
        return chain.proceed(request);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void clearToken() {
        this.token = null;
    }
}
