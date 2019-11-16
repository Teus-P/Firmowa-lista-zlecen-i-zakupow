package pl.app.core.dialog;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.app.core.utils.ResourceLoader;
import pl.app.core.property.DialogProperty;
import pl.app.launch.LaunchApp;

import java.io.IOException;

public class DialogStage extends Stage {

    private ResourceLoader resourceLoader = ResourceLoader.getInstance();
    private FXMLLoader fxmlLoader;

    public DialogStage(DialogProperty dialogProperty) {

        fxmlLoader = resourceLoader.fxmlLoader(dialogProperty.getDialogFxmlPath());

        Parent parent = null;
        DialogBody dialogBody;
        try {
            parent = fxmlLoader.load();
            dialogBody = fxmlLoader.getController();
            dialogBody.dialogStage(this);
            initModality(Modality.APPLICATION_MODAL);
            initStyle(StageStyle.UTILITY);
            setMinWidth(1280);
            setMinHeight(720);
            setTitle(dialogProperty.getDialogTitle());
            Scene scene = new Scene(parent);
            setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setOnCloseRequest(e -> close());

    }



    @Override
    public void close() {
        super.close();
        LaunchApp.getPrimaryStage().getScene().getRoot().setEffect(null);
    }

    @Override
    public void showAndWait() {
        setPrimaryStageBlurEffect();
        super.showAndWait();
    }

    private void setPrimaryStageBlurEffect() {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        LaunchApp.getPrimaryStage().getScene().getRoot().setEffect(blur);
    }


    public <T> T getController(){
        return fxmlLoader.getController();
    }

}
