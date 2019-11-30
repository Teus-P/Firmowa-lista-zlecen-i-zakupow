package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class UserOrderInfoModel implements Comparable<UserOrderInfoModel> {


    @SerializedName("idUserOrderInfo")
    @Expose
    private Integer idUserOrderInfo;

    @SerializedName("orderStatus")
    @Expose
    private OrderStatusModel orderStatus;

    @SerializedName("informationDate")
    @Expose
    private String informationDate;

    @Override
    public int compareTo(@NotNull UserOrderInfoModel o) {
        return this.informationDate.compareTo(o.getInformationDate());
    }
}
