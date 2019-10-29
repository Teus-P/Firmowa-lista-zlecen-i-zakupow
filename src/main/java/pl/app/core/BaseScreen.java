package pl.app.core;

import lombok.Getter;
import pl.app.core.contextController.ContextManager;
import pl.app.core.screenController.ScreenController;

public abstract class BaseScreen extends BasePage {

    @Getter
    private ContextManager contextManager = ContextManager.getInstance();

    protected void onCreateBuildContext(){
    }

    @Override
    public void onLoadNode(ScreenController screenPage) {
        super.onLoadNode(screenPage);
        onCreateBuildContext();
    }
}
