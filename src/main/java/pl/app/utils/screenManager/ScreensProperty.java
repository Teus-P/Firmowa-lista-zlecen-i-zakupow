package pl.app.utils.screenManager;

/**
 * enum ze sciezka do fxmla , rozmiary okna , tytul okna
 */
public enum ScreensProperty {
    MAIN_PAGE("mainPage", "view/MainPanel.fxml", "Mainpage"),
    LOGIN_PAGE("loginPage", "view/AcceptedOrderField.fxml", "LOGOWANIE");

    private String screenId;
    private String screenPath;

    private String title;


    ScreensProperty(String screenId, String screenPath, String title) {
        this.screenId = screenId;
        this.screenPath = screenPath;
        this.title = title;
    }

    public String getScreenId() {
        return screenId;
    }

    public String getScreenPath() {
        return screenPath;
    }


    public String getTitle() {
        return title;
    }
}
