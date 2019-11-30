package pl.app.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.api.model.UserAccountModel;

import java.io.Serializable;

@NoArgsConstructor
public final class UserSession implements Serializable {

    private static final long serialVersionUID = -6127338495549209874L;

    private static final UserSession instance = new UserSession();
    private static UserAccountHelper userAccountHelper = new UserAccountHelper(ApiResourcesClient.getApi());

    @Setter
    @Getter
    private static String accessToken = null;
    @Setter
    @Getter
    private static String refreshToken = null;

    @Getter
    private static UserAccountModel loggedUser;


    public static UserSession getInstance() {
        return instance;
    }

    public static void setSession(String accessToken, String refreshToken) {
        UserSession.refreshToken = refreshToken;
        UserSession.accessToken = accessToken;
        loggedUser = userAccountHelper.getMyAccountDetails();
    }

    public static void clearSession() {
        UserSession.refreshToken = null;
        UserSession.accessToken = null;
        loggedUser = null;
    }
}
