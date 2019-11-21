package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductModel {

    @SerializedName("idProduct")
    @Expose
    private Integer idProduct;

    @SerializedName("categories")
    @Expose
    private CategoriesModel categories;

    @SerializedName("unit")
    @Expose
    private UnitModel unit;

    @SerializedName("name")
    @Expose
    private String name;

    public ProductModel(){}

    public ProductModel(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
