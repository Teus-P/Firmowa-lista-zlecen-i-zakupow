package pl.app.core.content;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import pl.app.core.ResourceLoader;
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

    public class Builder<T extends Pane> {

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

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
