package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImplementerModel {

    @SerializedName("idImplementers")
    @Expose
    private int idImplementers;

    @SerializedName("categories")
    @Expose
    private CategoriesModel categoriesModel;

    @SerializedName("userAccount")
    @Expose
    private UserAccountModel userAccount;




}
