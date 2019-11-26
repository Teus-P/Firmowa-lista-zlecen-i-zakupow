package pl.app.controllers.common.checkComboBoxItem;

import lombok.Getter;
import pl.app.api.model.CategoriesModel;

public class CategoriesCheckBoxItem {

    @Getter
    private CategoriesModel categoriesModel;

    public CategoriesCheckBoxItem(CategoriesModel categoriesModel) {
        this.categoriesModel = categoriesModel;
    }


    @Override
    public String toString() {
        return categoriesModel.getName();
    }
}
