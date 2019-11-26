package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserAccountModel {

    @SerializedName("idUserAccount")
    @Expose
    private Integer idUserAccount;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("password")
    private String password;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("pesel")
    @Expose
    private String pesel;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("userAccountTypes")
    @Expose
    private List<UserAccountTypeModel> userAccountTypeModels;


    public UserAccountModel(String username, String firstName, String password, String lastName,
                            String pesel, String email, String phoneNumber, List<UserAccountTypeModel> userAccountTypeModels) {
        this.username = username;
        this.firstName = firstName;
        this.password = password;
        this.lastName = lastName;
        this.pesel = pesel;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userAccountTypeModels = userAccountTypeModels;
    }

    public UserAccountModel(String username, String firstName, String lastName,
                            String pesel, String email, String phoneNumber, List<UserAccountTypeModel> userAccountTypeModels) {

        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userAccountTypeModels = userAccountTypeModels;
    }
}
