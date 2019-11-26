package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductModel extends RecursiveTreeObject<OrderProductModel> {

    @SerializedName("userOrder")
    @Expose
    private Integer userOrder;

    @SerializedName("idOrderProduct")
    @Expose
    private Integer idOrderProduct;

    @SerializedName("product")
    @Expose
    private ProductModel product;

    @SerializedName("shoppingList")
    @Expose
    private Object shoppingList;

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    @SerializedName("quantityBought")
    @Expose
    private Integer quantityBought;


}
