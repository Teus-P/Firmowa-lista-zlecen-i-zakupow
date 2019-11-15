package pl.app.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class UnitModel {

    @SerializedName("idUnit")
    @Expose
    private Integer idUnit;

    @SerializedName("unit")
    @Expose
    private String unit;

    //ostrożnie z tą flagą
    //flaga odpowiada za usunięcie w systemie
    @SerializedName("deleted")
    @Expose
    private boolean deleted = false;

    @Override
    public String toString() {
        return unit;
    }

    public UnitModel(String unit) {
        this.unit = unit;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitModel)) return false;
        UnitModel unitModel = (UnitModel) o;
        return Objects.equals(getIdUnit(), unitModel.getIdUnit()) &&
                Objects.equals(getUnit(), unitModel.getUnit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUnit(), getUnit());
    }
}
