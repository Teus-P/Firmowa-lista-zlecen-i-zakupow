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
    private UserAccountModel userAccount;

    @SerializedName("orderStatus")
    @Expose
    private OrderStatusModel orderStatus;

    @SerializedName("storekeeper")
    @Expose
    private UserAccountModel storekeeper;

    @SerializedName("recipient")
    @Expose
    private UserAccountModel recipient;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("userInfo")
    @Expose
    private Boolean userInfo;

    @SerializedName("orderProducts")
    @Expose
    private List<OrderProductModel> orderProductModels;

    @SerializedName("accepted")
    @Expose
    private boolean accepted;

}
