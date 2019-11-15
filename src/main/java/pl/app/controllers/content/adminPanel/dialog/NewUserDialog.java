package pl.app.controllers.content.adminPanel.dialog;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.app.api.model.UserAccountModel;
import pl.app.core.ResourceLoader;
import pl.app.launch.LaunchApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewUserDialog extends Stage implements Initializable {

    private ResourceLoader resourceLoader = ResourceLoader.getInstance();



    public NewUserDialog() {
        FXMLLoader fxmlLoader = resourceLoader.fxmlLoader("views/content/adminPanel/NewUserDialog.fxml");
        fxmlLoader.setController(this);

        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UTILITY);
        setMinWidth(1280);
        setMinHeight(720);
        setTitle("Dodawanie nowego użytkownika");
        try {
            setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setOnCloseRequest(e->{
            LaunchApp.getPrimaryStage().getScene().getRoot().setEffect(null);
            close();
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
