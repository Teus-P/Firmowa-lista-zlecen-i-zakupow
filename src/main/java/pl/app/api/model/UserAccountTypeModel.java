package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class UserAccountTypeModel {

    @SerializedName("idUserAccountType")
    @Expose
    private Integer idUserAccountType;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccountTypeModel)) return false;
        UserAccountTypeModel that = (UserAccountTypeModel) o;
        return Objects.equals(getIdUserAccountType(), that.getIdUserAccountType()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUserAccountType(), getName(), getDescription());
    }
}
