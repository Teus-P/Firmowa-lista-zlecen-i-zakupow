package pl.app.api;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public final class TokenKeeper implements Serializable {

    private static final long serialVersionUID = -6127338495549209874L;

    private static final TokenKeeper instance = new TokenKeeper();
    private static String accessToken = null;
    private static String refreshToken = null;


    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        TokenKeeper.accessToken = accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        TokenKeeper.refreshToken = refreshToken;
    }

    public static TokenKeeper getInstance() {
        return instance;
    }

}
