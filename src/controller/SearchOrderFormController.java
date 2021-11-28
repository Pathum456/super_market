package controller;

import bo.BoFactory;
import bo.custom.PurchaseOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.tdm.OrderDetailTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/

public class SearchOrderFormController {
    private final PurchaseOrderBO purchaseOrderBO = ( PurchaseOrderBO ) BoFactory.getBOFactory ( ).getBO (BoFactory.BoTypes.PURCHASE_ORDER);
    public ImageView imgHome;
    public Label lblHome;
    public ImageView imgCustomer;
    public ImageView imgOrder;
    public ImageView imgOrderDetails;
    public Label lblSelect;
    public Label lblDescription;
    public AnchorPane root;
    public Label lblId;
    public Label lblDate;
    public JFXTextField txtCustomerNIC;
    public JFXTextField txtCustomerName;
    public JFXComboBox <String>cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXButton btnDelete;
    public TableView <OrderDetailTM>tblOrderDetails;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colNIC;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public JFXComboBox <String>cmbCustomerId;

    public void initialize() {
        loadDateAndTime ( );
        loadAllCustomerIds();
        loadAllItemCodes ();

        cmbCustomerId.getSelectionModel ( ).selectedItemProperty ( ).addListener ((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton ( );

            if (newValue != null) {
                try {
                    try {
                        if (!existCustomer (newValue + "")) {
                            //"There is no such customer associated with the id " + id
                            new Alert (Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show ( );
                        }
                        /*Search Customer*/
                        CustomerDTO customerDTO = purchaseOrderBO.searchCustomer (newValue + "");
                        txtCustomerName.setText (customerDTO.getName ( ));

                    } catch (SQLException e) {
                        new Alert (Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show ( );
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace ( );
                }
            } else {
                txtCustomerName.clear ( );
            }
        });
        cmbItemCode.getSelectionModel ( ).selectedItemProperty ( ).addListener ((observable, oldValue, newItemCode) -> {
            txtQty.setEditable (newItemCode != null);


            if (newItemCode != null) {
                try {
                    if (!existItem (newItemCode + "")) {
                        //throw new NotFoundException("There is no such item associated with the id " + code);
                    }
                    /*Find Item*/
                    ItemDTO item = purchaseOrderBO.searchItem (newItemCode + "");
                    txtDescription.setText (item.getDescription ( ));
                    txtUnitPrice.setText (String.valueOf (item.getUnitPrice ( )));
                    Optional<OrderDetailTM> optOrderDetail = tblOrderDetails.getItems ( ).stream ( ).filter (detail -> detail.getCode ( ).equals (newItemCode)).findFirst ( );
                    txtQtyOnHand.setText ((optOrderDetail.isPresent ( ) ? item.getQtyOnHand ( ) - optOrderDetail.get ( ).getQty ( ) : item.getQtyOnHand ( )) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace ( );
                } catch (ClassNotFoundException e) {
                    e.printStackTrace ( );
                }

            } else {
                txtDescription.clear ( );
                txtQty.clear ( );
                txtQtyOnHand.clear ( );
                txtUnitPrice.clear ( );
            }
        });
        tblOrderDetails.getSelectionModel ( ).selectedItemProperty ( ).addListener ((observable, oldValue, selectedOrderDetail) -> {
            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable (true);
                cmbItemCode.setValue (selectedOrderDetail.getCode ( ));

                txtQtyOnHand.setText (Integer.parseInt (txtQtyOnHand.getText ( )) + selectedOrderDetail.getQty ( ) + "");
                txtQty.setText (selectedOrderDetail.getQty ( ) + "");
            } else {

                cmbItemCode.setDisable (false);
                cmbItemCode.getSelectionModel ( ).clearSelection ( );
                txtQty.clear ( );
            }
        });
    }
    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.ifCustomerExist (id);
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.ifItemExist (code);
    }
    private void enableOrDisablePlaceOrderButton() {
        btnDelete.setDisable (!(cmbCustomerId.getSelectionModel ( ).getSelectedItem ( ) != null && !tblOrderDetails.getItems ( ).isEmpty ( )));
    }

    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> all = purchaseOrderBO.getAllCustomers ( );
            for (CustomerDTO customerDTO : all) {
                cmbCustomerId.getItems ( ).add (customerDTO.getId ( ));
            }
        } catch (SQLException e) {
            new Alert (Alert.AlertType.ERROR, "Failed to load customer ids").show ( );
        } catch (ClassNotFoundException e) {
            e.printStackTrace ( );
        }
    }

    private void loadAllItemCodes() {
        try {
            ArrayList<ItemDTO> all = purchaseOrderBO.getAllItems ( );
            for (ItemDTO dto : all) {
                cmbItemCode.getItems ( ).add (dto.getItemCode ( ));
            }
        } catch (SQLException e) {
            new Alert (Alert.AlertType.ERROR, e.getMessage ( )).show ( );
        } catch (ClassNotFoundException e) {
            e.printStackTrace ( );
        }
    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date ( );
        SimpleDateFormat f = new SimpleDateFormat ("yyyy-MM-dd");
        lblDate.setText (f.format (date));

        // load Time
        Timeline time = new Timeline (new KeyFrame (Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now ( );
            /*lblTime.setText (
                    currentTime.getHour ( ) + " : " + currentTime.getMinute ( ) +
                            " : " + currentTime.getSecond ( )
            );*/
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
                case "imgCustomer":
                    root = FXMLLoader.load (this.getClass ( ).getResource ("/view/ReceptionWorkForm.fxml"));
                    break;
                case "imgOrder":
                    root = FXMLLoader.load (this.getClass ( ).getResource ("/view/PlaceOrderForm.fxml"));
                    lblSelect.setText ("Place Order");
                    lblDescription.setText ("Click here to place a Order");
                    break;
                case "imgOrderDetails":
                    root = FXMLLoader.load (this.getClass ( ).getResource ("../view/SearchOrderForm.fxml"));
                    lblSelect.setText ("Search Orders");
                    lblDescription.setText ("Click if you want to search orders");
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

    public void txtQty_OnAction(ActionEvent actionEvent) {
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
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
                case "imgCustomer":
                    lblSelect.setText ("Manage Customer");
                    lblDescription.setText ("Add, Delete, Update, Customer Details & Search");
                    break;
                case "imgOrder":
                    lblSelect.setText ("Place Order");
                    lblDescription.setText ("Click here to place a Order");
                    break;
                case "imgOrderDetails":
                    lblSelect.setText ("Search Orders");
                    lblDescription.setText ("Click if you want to search orders");
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
