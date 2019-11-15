package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

//TODO change the class name to CategoryModel

@Getter
@Setter
public class CategoriesModel {

    @SerializedName("idCategories")
    @Expose
    private Integer idCategories;

    @SerializedName("name")
    @Expose
    private String name;


    public CategoriesModel() {
    }

    public CategoriesModel(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
