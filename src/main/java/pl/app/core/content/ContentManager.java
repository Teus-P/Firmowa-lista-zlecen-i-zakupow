package pl.app.core.content;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import pl.app.core.utils.ResourceLoader;
import pl.app.core.property.ContentProperty;

import java.io.IOException;

public class ContentManager {

    private static final ContentManager instance = new ContentManager();
    private ResourceLoader resourceLoader = ResourceLoader.getInstance();

    private ContentManager() {
    }

    public static ContentManager getInstance() {
        return instance;
    }

    public Builder buildContext(ContentProperty contentProperty) {
        return new Builder(resourceLoader.fxmlLoader(contentProperty.getScreenPath()));
    }

    public class Builder<T extends AnchorPane> {

        private T container;
        private FXMLLoader fxmlLoader;

        Builder(FXMLLoader fxmlLoader) {
            this.fxmlLoader = fxmlLoader;
        }

        public Builder attachTo(T container) {
            this.container = container;
            return this;
        }


        public void build() {
            try {
                container.getChildren().clear();
                Parent parent = fxmlLoader.load();
                container.getChildren().add(parent);

                AnchorPane.setBottomAnchor(parent, 0.0);
                AnchorPane.setTopAnchor(parent, 0.0);
                AnchorPane.setLeftAnchor(parent, 0.0);
                AnchorPane.setRightAnchor(parent, 0.0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
