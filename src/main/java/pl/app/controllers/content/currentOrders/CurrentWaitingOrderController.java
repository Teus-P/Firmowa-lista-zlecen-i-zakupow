package pl.app.controllers.content.currentOrders;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import pl.app.core.baseComponent.BaseScreen;
import pl.app.core.property.ContentProperty;

public class CurrentWaitingOrderController extends BaseScreen {

    @FXML
    private AnchorPane pageContainer;

    @FXML
    void backButtonOnAction(ActionEvent event) {
        getContentManager().buildContext(ContentProperty.CURRENT_ORDERS).attachTo(pageContainer).build();
    }
}
