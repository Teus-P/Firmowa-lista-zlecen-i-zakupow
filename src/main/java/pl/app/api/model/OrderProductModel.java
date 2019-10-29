package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductModel {

    @SerializedName("idOrderProduct")
    @Expose
    private Integer idOrderProduct;

    @SerializedName("product")
    @Expose
    private ProductModel product;

    @SerializedName("quantity")
    @Expose
    private Integer quantity;


}
