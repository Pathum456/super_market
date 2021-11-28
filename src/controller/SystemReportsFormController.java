package controller;

import javafx.animation.*;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class SystemReportsFormController {
    public AnchorPane root;
    public ImageView imgHome;
    public Label lblHome;
    public ImageView imgItem;
    public ImageView ImgReport;
    public Tab tabDaily;
    public TableView tblDailyIncome;
    public Tab tabMonthly;
    public TableView tblMonthlyIncome;
    public Tab tabAnnual;
    public TableView tblAnnualIncome;
    public Tab tabCustomer;
    public TableView tblCustomerIncome;
    public Label lbl_Date_Time;
    public Label lblMost;
    public Label lblLeast;
    public Label lblIncomeTxt;
    public Label lblSelect;
    public Label lblDescription;
    public ImageView imgBusiness;

    public void initialize(){
    lblIncomeTxt.setText ("Daily Income :");
    lblMost.setText ("not set yet");
    lblLeast.setText ("not set yet");
    loadDateAndTime();
}
    private void loadDateAndTime() {
        // load Date
        Date date = new Date ( );
        SimpleDateFormat f = new SimpleDateFormat ("yy-MMM-dd");

        // load Time
        Timeline time = new Timeline (new KeyFrame (Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now ( );
           lbl_Date_Time.setText (f.format (date ) + "\t"+
                    currentTime.getHour ( ) + " : " + currentTime.getMinute ( ) +
                            " : " + currentTime.getSecond ( )
            );
        }),
                new KeyFrame (Duration.seconds (1))
        );
        time.setCycleCount (Animation.INDEFINITE);
        time.play ( );


    }
    public void navigate(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource ( ) instanceof ImageView) {
            ImageView icon = ( ImageView ) mouseEvent.getSource ( );

            Parent root = null;
            switch (icon.getId ( )) {
                case "imgHome":

                    root = FXMLLoader.load (this.getClass ( ).getResource ("/view/MainForm.fxml"));


                    break;
                case "imgItem":
                    root = FXMLLoader.load (this.getClass ( ).getResource ("/view/AdminWorkForm.fxml"));
                    break;
                case "ImgReport":

                    root = FXMLLoader.load (this.getClass ( ).getResource ("/view/SystemReportsForm.fxml"));
                    break;
                case "imgBusiness":
                    root = FXMLLoader.load (this.getClass ( ).getResource ("../view/BusinessDetailsForm.fxml"));
                   break;


            }
            if (root != null) {
                Scene subScene = new Scene (root);
                Stage primaryStage = ( Stage ) this.root.getScene ( ).getWindow ( );
                primaryStage.setScene (subScene);
                primaryStage.centerOnScreen ( );
                primaryStage.setTitle ("Super_Market 'Main Form'");

                TranslateTransition tt = new TranslateTransition (Duration.millis (0), subScene.getRoot ( ));
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
            switch (icon.getId ( ) ) {
                case "imgItem":
                    lblSelect.setText ("Manage Items");
                    lblDescription.setText ("Add, Delete, Update, Items Details & View");
                    break;
                case "ImgReport":
                    lblSelect.setText ("Income Reports");
                    lblDescription.setText ("Click here to view a Reports");
                    break;
                case "imgBusiness":
                    lblSelect.setText ("Business Reports");
                    lblDescription.setText ("Click if you want view Business Reports");
                    break;
                case "imgHome":
                    lblHome.setText ("Home");
                    break;
            }
        }
    }

    public void textChangeOnSelection(Event event) {
    if (event.getSource () instanceof Tab ) {
        Tab tab= ( Tab ) event.getSource ();

        switch (tab.getId ()){
            case "tabDaily" :
                lblIncomeTxt.setText ("Daily Income : ");
                /*tabDaily.getTabPane ().setStyle ("-fx-border-color: green");*/
                break;
            case "tabMonthly" :
                lblIncomeTxt.setText ("Monthly Income : ");
                break;
            case "tabAnnual" :
                lblIncomeTxt.setText ("Annual Income : ");
                break;
            case "tabCustomer" :
                lblIncomeTxt.setText ("Customer Vise Income : ");
                break;
        }
        /*if (tabDaily.isSelected ( )) {
            lblIncomeTxt.setText ("Daily Income : ");
        }
        if (tabMonthly.isSelected ( )) {
            lblIncomeTxt.setText ("Monthly Income : ");
        }
        if (tabAnnual.isSelected ( )) {
            lblIncomeTxt.setText ("Annual Income : ");
        }
        if (tabCustomer.isSelected ( )) {
            lblIncomeTxt.setText ("Customer Vise Income : ");
        }*/
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
            lblSelect.setText ("Welcome");
            lblDescription.setText ("Select from above ");
        }

    }

}
