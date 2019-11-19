package pl.app.controllers.common.listItems;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import pl.app.api.model.UnitModel;

@Getter
public class UnitTableItem extends RecursiveTreeObject<UnitTableItem> {

    private UnitModel unitModel;
    private StringProperty unit;

    public UnitTableItem(UnitModel unitModel) {
        this.unitModel = unitModel;
        this.unit = new SimpleStringProperty(unitModel.getUnit());
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
        this.unitModel.setUnit(unit);
    }

}
