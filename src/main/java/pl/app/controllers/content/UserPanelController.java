package pl.app.controllers.content;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;

import javafx.scene.image.ImageView;
import pl.app.api.TokenKeeper;
import pl.app.core.baseComponent.BaseScreen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserPanelController extends BaseScreen {

    @FXML
    private ImageView qrImageView;

    public UserPanelController() {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initQrCode();
    }


    private void initQrCode() {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        int width = (int) qrImageView.getFitWidth();
        int height = (int) qrImageView.getFitHeight();

        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(TokenKeeper.getAccessToken(), BarcodeFormat.QR_CODE, width, height);
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

}