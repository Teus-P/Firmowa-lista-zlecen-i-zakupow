package pl.app.utils.screenManager;

/**
 * enum with fxml paths and Stage title
 */
public enum ScreensProperty {
    MAIN_PAGE("view/MainPanel.fxml", "Ekran główny"),
    LOGIN_PAGE("view/LoginPage.fxml", "LOGOWANIE");

    private String screenPath;
    private String stageTitle;


    ScreensProperty(String screenPath, String stageTitle) {
        this.screenPath = screenPath;
        this.stageTitle = stageTitle;
    }


    public String getScreenPath() {
        return screenPath;
    }


    public String getStageTitle() {
        return stageTitle;
    }
}
