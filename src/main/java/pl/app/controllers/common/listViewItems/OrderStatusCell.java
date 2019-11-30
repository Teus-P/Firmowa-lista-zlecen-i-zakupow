package pl.app.controllers.common.listViewItems;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import pl.app.api.model.UserOrderInfoModel;
import pl.app.controllers.content.realizedOrder.OrderStatusCellController;
import pl.app.core.utils.ResourceLoader;

import java.io.IOException;

public class OrderStatusCell extends ListCell<UserOrderInfoModel> {

    private ResourceLoader resourceLoader = ResourceLoader.getInstance();

    @Override
    protected void updateItem(UserOrderInfoModel item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setText(null);
            setGraphic(null);
        }

        if (item != null) {
            FXMLLoader fxmlLoader;
            try {
                fxmlLoader = resourceLoader.fxmlLoader("views/content/OrderStatusCell.fxml");
                Parent parent = fxmlLoader.load();
                OrderStatusCellController controller = fxmlLoader.getController();
                controller.initData(item);
                setText(null);
                setGraphic(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }
}
