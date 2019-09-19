package pl.app.api.interceptors;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;

public class DynamicHostInterceptor implements Interceptor {

    private String host;

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

        return chain.proceed(request);
    }

    public void setHost(String host) {
        this.host = host;
    }

    private int getPort(URL url) {
        return ((-1) == url.getPort()) ? 80 : url.getPort();
    }

    private String getSegments(URL url, HttpUrl httpUrl) {

        String oldSegments = String.join("/", httpUrl.pathSegments());
        String newSegments = url.getPath();

        return newSegments + oldSegments;
    }
}
