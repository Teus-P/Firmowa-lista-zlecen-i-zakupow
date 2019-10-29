package pl.app.core.contextController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import pl.app.core.ResourceLoader;
import pl.app.core.property.ContextProperty;

import java.io.IOException;

public class ContextManager {

    private static final ContextManager instance = new ContextManager();
    private ResourceLoader resourceLoader = ResourceLoader.getInstance();

    private ContextManager() {
    }

    public static ContextManager getInstance() {
        return instance;
    }

    public Inject buildContext(ContextProperty contextProperty) {
        return new Inject(resourceLoader.fxmlLoader(contextProperty.getScreenPath()));
    }

    public class Inject<T extends Pane> {

        private T container;
        private FXMLLoader fxmlLoader;

        Inject(FXMLLoader fxmlLoader) {
            this.fxmlLoader = fxmlLoader;
        }

        public Inject injectIn(T container) {
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
