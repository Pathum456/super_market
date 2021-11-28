package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.tdm.CustomerTM;
import view.tdm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class BusinessDetailsFormController {
    private final CustomerBO customerBO = ( CustomerBO ) BoFactory.getBOFactory ( ).getBO (BoFactory.BoTypes.CUSTOMER);
    private final ItemBO itemBO = ( ItemBO ) BoFactory.getBOFactory ( ).getBO (BoFactory.BoTypes.ITEM);
    public AnchorPane root;
    public ImageView imgHome;
    public ImageView imgItem;
    public ImageView ImgReport;
    public ImageView imgBusiness;
    public Label lblDescription;
    public Label lblSelect;
    public TableView<CustomerTM> tblCustomers;
    public TableView<ItemTM> tblItems;
    public Label lblHome;

    public void initialize() {
        tblCustomers.getColumns ( ).get (0).setCellValueFactory (new PropertyValueFactory<> ("id"));
        tblCustomers.getColumns ( ).get (1).setCellValueFactory (new PropertyValueFactory<> ("name"));
        tblCustomers.getColumns ( ).get (2).setCellValueFactory (new PropertyValueFactory<> ("address"));
        tblCustomers.getColumns ( ).get (3).setCellValueFactory (new PropertyValueFactory<> ("nic"));
        tblCustomers.getColumns ( ).get (4).setCellValueFactory (new PropertyValueFactory<> ("title"));
        tblCustomers.getColumns ( ).get (5).setCellValueFactory (new PropertyValueFactory<> ("city"));
        tblCustomers.getColumns ( ).get (6).setCellValueFactory (new PropertyValueFactory<> ("province"));
        tblCustomers.getColumns ( ).get (7).setCellValueFactory (new PropertyValueFactory<> ("postalCode"));
        loadAllCustomers ( );
        tblItems.getColumns ( ).get (0).setCellValueFactory (new PropertyValueFactory<> ("code"));
        tblItems.getColumns ( ).get (1).setCellValueFactory (new PropertyValueFactory<> ("description"));
        tblItems.getColumns ( ).get (2).setCellValueFactory (new PropertyValueFactory<> ("packSize"));
        tblItems.getColumns ( ).get (3).setCellValueFactory (new PropertyValueFactory<> ("unitPrice"));
        tblItems.getColumns ( ).get (4).setCellValueFactory (new PropertyValueFactory<> ("qtyOnHand"));
        tblItems.getColumns ( ).get (5).setCellValueFactory (new PropertyValueFactory<> ("discount"));
        loadAllItems ( );
    }

    private void loadAllItems() {
        tblItems.getItems ( ).clear ( );
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems ( );
            for (ItemDTO item : allItems) {
                tblItems.getItems ( ).add (new ItemTM (item.getItemCode ( ), item.getDescription ( ), item.getPackSize ( ), item.getUnitPrice ( ), item.getQtyOnHand ( ), item.getDiscount ( )));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace ( );
        } catch (ClassNotFoundException e) {
            e.printStackTrace ( );
        }
    }


    private void loadAllCustomers() {
        tblCustomers.getItems ( ).clear ( );
        /** Get all customers **/

        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomer ( );
            for (CustomerDTO customer : allCustomers) {
                tblCustomers.getItems ( ).add (new CustomerTM (customer.getId ( ), customer.getName ( ), customer.getAddress ( ), customer.getNic ( ), customer.getTitle ( ), customer.getCity ( ), customer.getProvince ( ), customer.getPostalCode ( )));
            }
        } catch (SQLException e) {
            new Alert (Alert.AlertType.ERROR, e.getMessage ( )).show ( );
        } catch (ClassNotFoundException e) {
            new Alert (Alert.AlertType.ERROR, e.getMessage ( )).show ( );
        }

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
            switch (icon.getId ( )) {
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
