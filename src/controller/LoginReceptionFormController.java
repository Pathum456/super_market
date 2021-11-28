package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * @author : Pathum Kaleesha
 * @since : 0.1.0
 * @day :20.10.21
 **/
public class LoginReceptionFormController {
    public ImageView imgHome;
    public JFXPasswordField txtPassword;
    public JFXTextField txtName;
    public ImageView imgLogin;
    public ImageView imgSingUp;
    public Label lblHome;
    public AnchorPane root;

    public void navigate(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource ( ) instanceof ImageView) {
            ImageView icon = ( ImageView ) mouseEvent.getSource ( );

            Parent root = null;
            switch (icon.getId ( )) {
                case "imgHome":

                    root = FXMLLoader.load (this.getClass ( ).getResource ("/view/MainForm.fxml"));
                    Stage primaryStage = ( Stage ) this.root.getScene ( ).getWindow ( );
                    primaryStage.setTitle ("Super_Market 'Main Form'");
                    break;
                case "imgLogin" :
                    root = FXMLLoader.load (this.getClass ( ).getResource ("/view/ReceptionWorkForm.fxml"));
                    Stage primaryStage1 = ( Stage ) this.root.getScene ( ).getWindow ( );
                    primaryStage1.setTitle ("Super_Market 'Main Form'");
                    break;
                case "imgSingUp" :
                    root = FXMLLoader.load (this.getClass ( ).getResource ("/view/MainForm.fxml"));
                    Stage primaryStage0 = ( Stage ) this.root.getScene ( ).getWindow ( );
                    primaryStage0.setTitle ("Cashier SignUp");
                    break;
            }
            if (root != null) {
                Scene subScene = new Scene (root);
                Stage primaryStage = ( Stage ) this.root.getScene ( ).getWindow ( );
                primaryStage.setScene (subScene);
                primaryStage.centerOnScreen ( );

                TranslateTransition tt = new TranslateTransition (Duration.millis (350), subScene.getRoot ( ));
                //tt.setFromX (-subScene.getWidth ( ));
                tt.setFromY (-subScene.getWidth ( ));
                //tt.setFromZ (-subScene.getWidth ( ));
                tt.setToY (0);
                //tt.setToZ (0);
                //tt.setToX (0);
                tt.play ( );
                //tt.stop ();

            }
        }
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        ImageView icon = ( ImageView ) mouseEvent.getSource ( );
        if (mouseEvent.getSource ( ) instanceof ImageView) {
            ScaleTransition scaleT = new ScaleTransition (Duration.millis (200), icon);
            scaleT.setToX (1.2);
            scaleT.setToY (1.2);
            scaleT.play ( );
            DropShadow glow = new DropShadow ( );
            glow.setColor (Color.CORNFLOWERBLUE);
            glow.setWidth (20);
            glow.setHeight (20);
            glow.setRadius (20);
            icon.setEffect (glow);
            lblHome.setText ("Home");
        }
    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource ( ) instanceof ImageView) {
            ImageView icon = ( ImageView ) mouseEvent.getSource ( );
            ScaleTransition scaleT = new ScaleTransition (Duration.millis (200), icon);
            scaleT.setToX (1);
            scaleT.setToY (1);
            scaleT.play ( );
            icon.setEffect (null);
            lblHome.setText (null);
        }
    }
}
