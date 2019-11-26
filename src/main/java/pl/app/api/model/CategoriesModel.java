package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

//TODO change the class productName to CategoryModel

import java.util.Objects;

@Getter
@Setter
public class CategoriesModel {

    @SerializedName("idCategories")
    @Expose
    private Integer idCategories;

    @SerializedName("name")
    @Expose
    private String name;

    //ostrożnie z tą flagą
    //flaga odpowiada za usunięcie w systemie
    @SerializedName("deleted")
    @Expose
    private boolean deleted = false;

    public CategoriesModel() {
    }

    public CategoriesModel(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriesModel)) return false;
        CategoriesModel that = (CategoriesModel) o;
        return isDeleted() == that.isDeleted() &&
                Objects.equals(getIdCategories(), that.getIdCategories()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCategories(), getName(), isDeleted());
    }
}
