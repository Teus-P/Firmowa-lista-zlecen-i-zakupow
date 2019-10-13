package pl.app.api.interceptors;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class DynamicHostInterceptor implements Interceptor {

    private static final Logger LOGGER = Logger.getLogger(DynamicHostInterceptor.class.getName());

    private String host;

    public DynamicHostInterceptor(String host) {
        this.host = host;
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (null != host) {
            URL url = new URL(host);

            HttpUrl httpUrl = request.url().newBuilder()
                    .host(url.getHost())
                    .port(getPort(url))
                    .build();

            request = request.newBuilder()
                    .url(httpUrl)
                    .build();
        }

        LOGGER.info("HOST : " + host);
        LOGGER.info("Request : " + request);

        return chain.proceed(request);
    }

    public void setHost(String host) {
        this.host = host;
    }

    private int getPort(URL url) {
        if (url.getPort() == (-1)) {
            return 8088;
        } else return url.getPort();
    }

    private String getSegments(URL url, HttpUrl httpUrl) {

        String oldSegments = String.join("/", httpUrl.pathSegments());
        String newSegments = url.getPath();

        return newSegments + oldSegments;
    }
}
