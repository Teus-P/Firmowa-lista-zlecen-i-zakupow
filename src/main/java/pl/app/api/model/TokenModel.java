package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenModel {

    @Expose
    @SerializedName("login")
    private String login;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("token")
    private String token;


    public TokenModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
