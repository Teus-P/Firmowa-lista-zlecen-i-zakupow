package pl.app.api.interceptors;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.logging.Logger;

public class BasicAuthorizationInterceptor implements Interceptor {

    private static final Logger LOGGER = Logger.getLogger(BasicAuthorizationInterceptor.class.getName());

    private String credentials;

    public BasicAuthorizationInterceptor(String user, String password) {
        this.credentials = Credentials.basic(user, password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request authenticatedRequest = chain.request();

        authenticatedRequest = authenticatedRequest.newBuilder()
                .header("Authorization", credentials)
                .build();

        LOGGER.info("Credentials : " + credentials);

        return chain.proceed(authenticatedRequest);
    }

}