package controller;


import bo.BoFactory;
import bo.custom.PurchaseOrderBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import javafx.animation.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.tdm.OrderDetailTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/

public class PlaceOrderFormController {
    private final PurchaseOrderBO purchaseOrderBO = ( PurchaseOrderBO ) BoFactory.getBOFactory ( ).getBO (BoFactory.BoTypes.PURCHASE_ORDER);
    public ImageView imgHome;
    public Label lblHome;
    public ImageView imgCustomer;
    public ImageView imgOrder;
    public ImageView imgOrderDetails;
    public Label lblSelect;
    public Label lblDescription;
    public AnchorPane root;
    public JFXTextField txtCustomerName;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXButton btnSave;
    public TableView<OrderDetailTM> tblOrderDetails;
    public JFXButton btnPlaceOrder;
    public Label lblId;
    public Label lblDate;
    public Label lblTotal;
    public JFXComboBox<String> cmbCustomerId;
    String orderId = generateNewOrderId ( );

    public void initialize() {
        lblId.setText (generateNewOrderId ( ));
        loadDateAndTime ( );
        loadAllCustomerIds ( );
        loadAllItemCodes ( );

        tblOrderDetails.getColumns ( ).get (0).setCellValueFactory (new PropertyValueFactory<> ("code"));
        tblOrderDetails.getColumns ( ).get (1).setCellValueFactory (new PropertyValueFactory<> ("description"));
        tblOrderDetails.getColumns ( ).get (2).setCellValueFactory (new PropertyValueFactory<> ("qty"));
        tblOrderDetails.getColumns ( ).get (3).setCellValueFactory (new PropertyValueFactory<> ("unitPrice"));
        tblOrderDetails.getColumns ( ).get (4).setCellValueFactory (new PropertyValueFactory<> ("price"));
        TableColumn<OrderDetailTM, Button> lastCol = ( TableColumn<OrderDetailTM, Button> ) tblOrderDetails.getColumns ( ).get (5);

        lastCol.setCellValueFactory (param -> {
            Button btnDelete = new Button ("Delete");

            btnDelete.setOnAction (event -> {
                tblOrderDetails.getItems ( ).remove (param.getValue ( ));
                tblOrderDetails.getSelectionModel ( ).clearSelection ( );
                calculateTotal ( );
                enableOrDisablePlaceOrderButton ( );
            });

            return new ReadOnlyObjectWrapper<> (btnDelete);
        });

        cmbCustomerId.getSelectionModel ( ).selectedItemProperty ( ).addListener ((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton ( );

            if (newValue != null) {
                try {
                    try {
                        if (!existCustomer (newValue + "")) {

                            new Alert (Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show ( );
                        }
                        /**Search Customer**/
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
            btnSave.setDisable (newItemCode == null);

            if (newItemCode != null) {
                try {
                    if (!existItem (newItemCode + "")) {

                    }
                    /**Find Item**/
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
                btnSave.setText ("Update");
                txtQtyOnHand.setText (Integer.parseInt (txtQtyOnHand.getText ( )) + selectedOrderDetail.getQty ( ) + "");
                txtQty.setText (selectedOrderDetail.getQty ( ) + "");
            } else {
                btnSave.setText ("Add");
                cmbItemCode.setDisable (false);
                cmbItemCode.getSelectionModel ( ).clearSelection ( );
                txtQty.clear ( );
            }
        });

    }

    public String generateNewOrderId() {
        try {
            return purchaseOrderBO.generateNewOrderId ( );
        } catch (SQLException e) {
            new Alert (Alert.AlertType.ERROR, e.getMessage ( )).show ( );
        } catch (ClassNotFoundException e) {
            e.printStackTrace ( );
        }
        return null;
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal (0);
        for (OrderDetailTM detail : tblOrderDetails.getItems ( )) {
            total = total.add (BigDecimal.valueOf (detail.getPrice ( )));
        }
        lblTotal.setText (String.valueOf (total));
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.ifItemExist (code);
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.ifCustomerExist (id);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable (!(cmbCustomerId.getSelectionModel ( ).getSelectedItem ( ) != null && !tblOrderDetails.getItems ( ).isEmpty ( )));
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
                    root = FXMLLoader.load (this.getClass ( ).getResource ("/view/SearchOrderForm.fxml"));
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

    public void txtQty_OnAction(ActionEvent actionEvent) {
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        if (!txtQty.getText ( ).matches ("\\d+") || Integer.parseInt (txtQty.getText ( )) <= 0 ||
                Integer.parseInt (txtQty.getText ( )) > Integer.parseInt (txtQtyOnHand.getText ( ))) {
            new Alert (Alert.AlertType.ERROR, "Invalid qty").show ( );
            txtQty.requestFocus ( );
            txtQty.selectAll ( );
            return;
        }

        String itemCode = cmbItemCode.getSelectionModel ( ).getSelectedItem ( );
        String description = txtDescription.getText ( );
        Double unitPrice = Double.valueOf (txtUnitPrice.getText ( ));
        int qty = Integer.parseInt (txtQty.getText ( ));
        Double total = unitPrice * (qty);
        System.out.println (total);
        boolean exists = tblOrderDetails.getItems ( ).stream ( ).anyMatch (detail -> detail.getCode ( ).equals (itemCode));

        if (exists) {
            OrderDetailTM orderDetailTM = tblOrderDetails.getItems ( ).stream ( ).filter (detail -> detail.getCode ( ).equals (itemCode)).findFirst ( ).get ( );

            if (btnSave.getText ( ).equalsIgnoreCase ("Update")) {
                orderDetailTM.setQty (qty);
                orderDetailTM.setPrice (total);
                tblOrderDetails.getSelectionModel ( ).clearSelection ( );
            } else {
                orderDetailTM.setQty (orderDetailTM.getQty ( ) + qty);
                total = (new Double (orderDetailTM.getQty ( )));
                orderDetailTM.setPrice (total);
            }
            tblOrderDetails.refresh ( );
        } else {
            tblOrderDetails.getItems ( ).add (new OrderDetailTM (itemCode, description, qty, unitPrice, total));
        }
        cmbItemCode.getSelectionModel ( ).clearSelection ( );
        cmbItemCode.requestFocus ( );
        calculateTotal ( );
        enableOrDisablePlaceOrderButton ( );
    }


    public boolean saveOrder(String orderId, LocalDate orderDate, LocalTime orderTime, String customerId, Double discount, Double total, List<OrderDetailDTO> orderDetails) {
        try {
            OrderDTO orderDTO = new OrderDTO (orderId, orderDate, orderTime, customerId, discount, total, orderDetails);

            return purchaseOrderBO.purchaseOrder (orderDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace ( );
        } catch (ClassNotFoundException e) {
            e.printStackTrace ( );
        }
        return false;
    }

    public void btnPlaceOrder_OnAction(ActionEvent actionEvent) {
        String id = cmbCustomerId.getValue ( );

        double total = Double.parseDouble (lblTotal.getText ( ));
        if (!txtQty.getText ( ).equals ("") && !cmbCustomerId.getValue ( ).equals ("") && !cmbItemCode.equals ("")) {
            boolean b = saveOrder (orderId, LocalDate.now ( ), LocalTime.now ( ), cmbCustomerId.getValue ( ), 10.00, total, tblOrderDetails.getItems ( ).stream ( ).map (tm -> new OrderDetailDTO (orderId, id, txtCustomerName.getText ( ), tm.getCode ( ), tm.getDescription ( ), tm.getQty ( ), tm.getUnitPrice ( ), tm.getPrice ( ))).collect (Collectors.toList ( )));
            if (b) {
                new Alert (Alert.AlertType.INFORMATION, "Order has been placed successfully").show ( );
            } else {
                new Alert (Alert.AlertType.ERROR, "Order has not been placed successfully").show ( );
            }
            lblId.setText (generateNewOrderId ( ));
            cmbCustomerId.getSelectionModel ( ).clearSelection ( );
            cmbItemCode.getSelectionModel ( ).clearSelection ( );
            tblOrderDetails.getItems ( ).clear ( );
            txtQty.clear ( );
            calculateTotal ( );

        } else {
            new Alert (Alert.AlertType.WARNING, "please fill the all field....").showAndWait ( );
        }
    }


}
