package pl.app.controllers.common.listItems;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import pl.app.api.model.ImplementerModel;

@Getter
public class ImplementersTableItem extends RecursiveTreeObject<ImplementersTableItem> {

    private ImplementerModel implementerModel;
    private StringProperty categoriesDisplayValue;
    private StringProperty userFullNameDisplayValue;

    public ImplementersTableItem(ImplementerModel implementerModel) {
        this.implementerModel = implementerModel;
        this.categoriesDisplayValue = new SimpleStringProperty(implementerModel.getCategoriesModel().getName());
        this.userFullNameDisplayValue = new SimpleStringProperty(implementerModel.getUserAccount().getFirstName()
                + " " + implementerModel.getUserAccount().getLastName());

    }
}
