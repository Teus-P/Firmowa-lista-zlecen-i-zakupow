package pl.app.controllers.content;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXPasswordField;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import pl.app.api.UserSession;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.UserAccountHelper;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UserAccountModel;
import pl.app.api.responseInterfaces.ChangePasswordResponseListener;
import pl.app.controllers.common.FieldValidator;
import pl.app.controllers.common.popupDialogs.InformationDialog;
import pl.app.controllers.common.popupDialogs.WarningDialog;
import pl.app.core.baseComponent.BaseScreen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserPanelController extends BaseScreen implements ChangePasswordResponseListener {

    private UserAccountHelper userAccountHelper;
    private UserAccountModel userAccountModel;


    @FXML
    private Label userNameLabel;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label peselLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label userTypesLabel;

    @FXML
    private JFXPasswordField oldPasswordTxField;

    @FXML
    private JFXPasswordField newPasswordTxField;

    @FXML
    private JFXPasswordField repNewPasswordTxField;

    @FXML
    private ImageView qrImageView;

    @FXML
    private VBox qrContainer;


    public UserPanelController() {
        userAccountHelper = new UserAccountHelper(ApiResourcesClient.getApi());
        userAccountModel = userAccountHelper.getMyAccountDetails();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (UserSession.getLoggedUser().getUserAccountTypeModels().stream().anyMatch(item -> item.getName().equals("Role_IMPLEMENTERS"))) {
            qrContainer.setVisible(true);
        } else {
            qrContainer.setVisible(false);
        }

        initQrCode();


        initUi();
        setValidators();

    }

    private void setValidators() {
        FieldValidator.setRequiredValidator("Proszę wpisać stare hasło", oldPasswordTxField);
        FieldValidator.setRequiredValidator("Proszę wpisać nowe hasło", newPasswordTxField);
        FieldValidator.setRequiredValidator("Proszę powtórzyć nowe hasło", repNewPasswordTxField);
    }


    private void initQrCode() {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        int width = (int) qrImageView.getFitWidth();
        int height = (int) qrImageView.getFitHeight();

        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(UserSession.getAccessToken() + "\n" + UserSession.getRefreshToken(), BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();

            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

        } catch (WriterException ex) {
            Logger.getLogger(UserPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (bufferedImage != null) {
            qrImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        }
    }

    private void initUi() {
        userNameLabel.setText(userAccountModel.getUsername());
        fullNameLabel.setText(userAccountModel.getFirstName() + " " + userAccountModel.getLastName());
        peselLabel.setText(userAccountModel.getPesel());
        emailLabel.setText(userAccountModel.getEmail());
        phoneNumberLabel.setText(userAccountModel.getPhoneNumber());

        StringBuilder builder = new StringBuilder();
        userAccountModel.getUserAccountTypeModels().forEach(types ->
        {

            builder.append(types.getDescription()).append("\n");

        });
        userTypesLabel.setText(builder.toString());


    }

    @FXML
    void saveNewPasswordOnAction(ActionEvent event) {
        if (oldPasswordTxField.validate() && newPasswordTxField.validate() && repNewPasswordTxField.validate()) {
            if (newPasswordTxField.getText().equals(repNewPasswordTxField.getText())) {
                userAccountHelper.changeUserAccountPassword(oldPasswordTxField.getText(), newPasswordTxField.getText(), this);
            } else {
                WarningDialog.showWarningDialog("Błąd", "Nowe hasła nie są identyczne");
            }

        }
    }

    @Override
    public void onChangePasswordSuccessResponse(ResponseModel responseModel) {
        StringBuilder builder = new StringBuilder();
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        InformationDialog.showInformationDialog(responseModel.getMessage(), builder.toString());
    }

    @Override
    public void onChangePasswordFailedResponse(ResponseModel responseModel) {

        StringBuilder builder = new StringBuilder();
        responseModel.getDetails().forEach(message -> builder.append(message).append("\n"));
        WarningDialog.showWarningDialog(responseModel.getMessage(), builder.toString());
    }

}