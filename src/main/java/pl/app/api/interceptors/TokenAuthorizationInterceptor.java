package pl.app.api.interceptors;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import pl.app.api.TokenKeeper;

import java.io.IOException;
import java.util.logging.Logger;

public class TokenAuthorizationInterceptor implements Interceptor {

    private static final Logger LOGGER = Logger.getLogger(TokenAuthorizationInterceptor.class.getName());

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request authenticatedRequest = chain.request();

        authenticatedRequest = authenticatedRequest.newBuilder()
                .header("Authorization", "Bearer" + TokenKeeper.getAccessToken())
                .build();

        LOGGER.info("Request " + authenticatedRequest);

        return chain.proceed(authenticatedRequest);
    }
}
