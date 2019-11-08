package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderModel {


    @SerializedName("idOrder")
    @Expose
    private Integer idOrder;

    @SerializedName("userAccount")
    @Expose
    private UserModel userAccount;

    @SerializedName("orderStatus")
    @Expose
    private OrderStatusModel orderStatus;

    @SerializedName("storekeeper")
    @Expose
    private UserModel storekeeper;

    @SerializedName("recipient")
    @Expose
    private UserModel recipient;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("userInfo")
    @Expose
    private Boolean userInfo;

    @SerializedName("orderProducts")
    @Expose
    private List<OrderProductModel> orderProductModels;

}
