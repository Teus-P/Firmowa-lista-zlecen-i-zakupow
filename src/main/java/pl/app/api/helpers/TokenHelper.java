package pl.app.api.helpers;

import com.google.common.base.Throwables;
import okhttp3.RequestBody;
import pl.app.api.interfaces.ApiAuthorizationInterface;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.TokenModel;
import pl.app.api.responseInterfaces.LoginResponseListener;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TokenHelper {

    private ApiAuthorizationInterface api;

    public TokenHelper(ApiAuthorizationInterface api) {
        this.api = api;
    }

    public TokenModel getAccessToken(RequestBody grantType, RequestBody username, RequestBody password, LoginResponseListener loginResponseListener) {

        Call<TokenModel> call = api.getAccessToken(grantType, username, password);
        Response<TokenModel> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                return response.body();
            } else {
                return null;
            }
        } catch (IOException e) {

            e.printStackTrace();
            ResponseModel connectionFailedResponse = new ResponseModel();
            connectionFailedResponse.setCode(503);
            connectionFailedResponse.setTimestamp(LocalDateTime.now().toString());
            connectionFailedResponse.setStatus("BANDWIDTH_LIMIT_EXCEEDED");
            connectionFailedResponse.setMessage(e.getCause().getMessage());
            List<String> stackTrace = new ArrayList<>();
            stackTrace.add(Throwables.getStackTraceAsString(e));
            connectionFailedResponse.setDetails(stackTrace);

            loginResponseListener.onFailedServerConnection(connectionFailedResponse);

            return null;
        }
    }

}
