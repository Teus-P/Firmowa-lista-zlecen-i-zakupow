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

    @SerializedName("storekeeper")
    @Expose
    private UserAccountModel storekeeper;

    @SerializedName("recipient")
    @Expose
    private UserAccountModel recipient;

    @SerializedName("rejectedMessage")
    @Expose
    private String rejectedMessage;

    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    @SerializedName("orderProducts")
    @Expose
    private List<OrderProductModel> orderProductModels;

    @SerializedName("accepted")
    @Expose
    private boolean accepted;

    @SerializedName("ended")
    @Expose
    private boolean ended;

    @SerializedName("userOrderInfos")
    @Expose
    private List<UserOrderInfoModel> userOrderInfoModels;

}
