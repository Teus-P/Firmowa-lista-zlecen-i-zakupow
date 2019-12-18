package pl.app.core.baseComponent;

import lombok.Getter;
import pl.app.core.content.ContentManager;
import pl.app.core.screen.ScreenController;

public abstract class BaseScreen extends BasePage {

    @Getter
    private ContentManager contentManager = ContentManager.getInstance();

    protected void onCreateBuildContext() {
    }

    @Override
    public void onLoadNode(ScreenController screenPage) {
        super.onLoadNode(screenPage);
        onCreateBuildContext();
    }
}
