package pl.app.core.screen;

public interface ControlledScreen {

    //Parent injection
    void onLoadNode(ScreenController screenPage);

    void notifyDataSetChanged();
    //void setContainerContent();

}
