package pl.app.core.baseComponent;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import pl.app.core.dialog.DialogBody;
import pl.app.core.dialog.OnDialogCloseListener;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseDialog implements Initializable, DialogBody {

    @Getter
    private Stage dialogStage;

    @Setter
    protected OnDialogCloseListener onDialogCloseListener;

    public BaseDialog() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void dialogStage(Stage stage) {
        this.dialogStage = stage;
    }



}
