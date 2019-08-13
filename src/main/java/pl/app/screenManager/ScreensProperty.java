package pl.app.screenManager;

/**
 * enum ze sciezka do fxmla , rozmiary okna , tytul okna
 */
public enum ScreensProperty {
    HOME_SCREEN("homeScreen", "/fxml/main.fxml", 800, 600, "HOME");

    private String screenId;
    private String screenPath;
    private double width;
    private double height;
    private String title;


    ScreensProperty(String screenId, String screenPath, double width, double height, String title) {
        this.screenId = screenId;
        this.screenPath = screenPath;
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public String getScreenId() {
        return screenId;
    }

    public String getScreenPath() {
        return screenPath;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }
}
