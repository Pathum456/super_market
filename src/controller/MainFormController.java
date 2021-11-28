package controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
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
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author : Pathum Kaleesha
 * @since : 0.1.0
 * @day :19.10.21
 **/

public class MainFormController {
    public ImageView img_Admin;
    public ImageView img_Reciption;
    public Label lbl_Select;
    public AnchorPane root;
    public Label lblDescription;
    public ImageView imgBackGround;


    public void initialize(URL url, ResourceBundle rb) {
       FadeTransition fadeIn = new FadeTransition (Duration.millis (2000), root);
        fadeIn.setFromValue (0.0);
        fadeIn.setToValue (1.0);
        fadeIn.play ( );
    }

    public void navigate(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource ( ) instanceof ImageView) {
            ImageView icon = ( ImageView ) mouseEvent.getSource ( );

            Parent root = null;
            switch (icon.getId ( )) {
                case "img_Admin":
                    root = FXMLLoader.load (this.getClass ( ).getResource ("/view/LoginAdminForm.fxml"));
                    Stage primaryStage = (Stage) this.root.getScene().getWindow();
                    primaryStage.setTitle ("Admin Login");
                    break;
                case "img_Reciption":
                    root=FXMLLoader.load (this.getClass ( ).getResource ("/view/LoginReceptionForm.fxml"));
                    Stage primaryStage1 = (Stage) this.root.getScene().getWindow();
                    primaryStage1.setTitle ("Cashier Login");
                    break;
            }
            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();


                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromY(-subScene.getWidth());
                tt.setToY(0);
                tt.play();

            }
        }
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        ImageView icon = ( ImageView ) mouseEvent.getSource ( );
        if (mouseEvent.getSource ( ) instanceof ImageView) {

            switch (icon.getId ( )) {
                case "img_Admin":
                    lbl_Select.setText ("Admin");
                    lblDescription.setText ("continue as Admin");
                    break;
                case "img_Reciption":
                    lbl_Select.setText ("Cashier");
                    lblDescription.setText ("continue as Cashier");
                    break;
                default:
                    lbl_Select.setText ("Select Login from Above ");
            }
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
            lbl_Select.setText ("Welcome");
            lblDescription.setText ("Please select one of above main operations to proceed");

        }
    }
}
