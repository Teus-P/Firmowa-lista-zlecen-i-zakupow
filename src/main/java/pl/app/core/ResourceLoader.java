package pl.app.core;

import javafx.fxml.FXMLLoader;
import pl.app.utils.Utf8ResourceBundleControl;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ResourceLoader {

    private static final Locale POLISH_LOCALE = new Locale("pl", "PL");
    private static final Logger LOGGER = Logger.getLogger(ResourceLoader.class.getName());

    private static final ResourceLoader instance = new ResourceLoader();

    private ResourceLoader(){}

    public static ResourceLoader getInstance() {
        return instance;
    }

    private URL getFxmlUrl(String fxmlPath) {

        URL url = null;

        try {
            url = getClass().getClassLoader().getResource(fxmlPath);
            LOGGER.info("FXML URL : " + url);
            if (url == null) {
                throw new NullPointerException("** File with " + fxmlPath + " path doesn't exist **");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return url;
    }

    public FXMLLoader fxmlLoader(String fxmlPath) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        ResourceBundle resourceBundle = getLanguageBundleResources(POLISH_LOCALE);

        fxmlLoader.setLocation(getFxmlUrl(fxmlPath));
        fxmlLoader.setResources(resourceBundle);

        return fxmlLoader;
    }

    private ResourceBundle getLanguageBundleResources(Locale locale) {

        Utf8ResourceBundleControl resourceBundleControl = new Utf8ResourceBundleControl();
        ResourceBundle resourceBundle = null;

        try {
            resourceBundle = resourceBundleControl.newBundle("bundles/language", locale, "properties", getClass().getClassLoader(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resourceBundle;
    }

}
