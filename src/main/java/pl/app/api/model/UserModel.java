package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("firstName")
    private String firstName;

    @Expose
    @SerializedName("lastName")
    private String lastName;

    @Expose
    @SerializedName("pesel")
    private long pesel;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("phoneNumber")
    private String phoneNumber;


}
