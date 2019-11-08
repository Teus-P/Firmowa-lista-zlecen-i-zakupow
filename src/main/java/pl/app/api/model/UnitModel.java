package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitModel {

    @SerializedName("idUnit")
    @Expose
    private Integer idUnit;

    @SerializedName("unit")
    @Expose
    private String unit;

    @Override
    public String toString() {
        return unit;
    }

    public UnitModel(String unit) {
        this.unit = unit;
    }
}
