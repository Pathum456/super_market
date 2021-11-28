package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.tdm.CustomerTM;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class ReceptionWorkFormController {
    private final CustomerBO customerBO = ( CustomerBO ) BoFactory.getBOFactory ( ).getBO (BoFactory.BoTypes.CUSTOMER);
    public ImageView imgHome;
    public AnchorPane root;
    public ImageView imgCustomer;
    public ImageView imgOrder;
    public ImageView imgOrderDetails;
    public Label lblSelect;
    public Label lblDescription;
    public Label lblHome;
    public JFXButton btnAddNewCustomer;
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<CustomerTM> tblCustomers;
    public JFXTextField txtCustomerTitle;
    public JFXTextField txtCustomerCity;
    public JFXTextField txtCustomerProvince;
    public JFXTextField txtCustomerPostalCode;
    public JFXTextField txtCustomerNIC;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<> ( );

    Pattern namePattern = Pattern.compile ("^[A-z.[]]]{2,}$");
    Pattern titlePattern = Pattern.compile ("^[A-z.[]]]{2,}$");
    Pattern cityPattern = Pattern.compile ("^[A-z.[]]]{2,}$");
    Pattern provincePattern = Pattern.compile ("^[A-z.[]]]{2,}$");
    //Pattern contact = Pattern.compile ("^(07)[0-9][0-9]{7}$");
    Pattern nicPattern = Pattern.compile ("^[0-9]{12}|[0-9]{9}[V,v]$");
    Pattern postalCodePattern = Pattern.compile ("^[0-9]{3,}$");
    Pattern addressPattern = Pattern.compile ("^[#./0-9a-zA-z,-[]]]{5,}$");

    public void initialize() {
        tblCustomers.getColumns ().get (0).setCellValueFactory (new PropertyValueFactory<> ("id"));
        tblCustomers.getColumns ( ).get (1).setCellValueFactory (new PropertyValueFactory<> ("name"));
        tblCustomers.getColumns ( ).get (2).setCellValueFactory (new PropertyValueFactory<> ("address"));
        tblCustomers.getColumns ( ).get (3).setCellValueFactory (new PropertyValueFactory<> ("nic"));
        tblCustomers.getColumns ( ).get (4).setCellValueFactory (new PropertyValueFactory<> ("title"));
        tblCustomers.getColumns ( ).get (5).setCellValueFactory (new PropertyValueFactory<> ("city"));
        tblCustomers.getColumns ( ).get (6).setCellValueFactory (new PropertyValueFactory<> ("province"));
        tblCustomers.getColumns ( ).get (7).setCellValueFactory (new PropertyValueFactory<> ("postalCode"));
        initUI ( );
        lblSelect.setText ("Welcome");
        lblDescription.setText ("Select from above ");
        btnAddNewCustomer.requestFocus ( );
        loadToTable ( );
    }

    public void loadToTable() {
        tblCustomers.getSelectionModel ( ).selectedItemProperty ( ).addListener ((observable, oldValue, newValue) -> {
            btnDelete.setDisable (newValue == null);
            btnSave.setText (newValue != null ? "Update" : "Save");
            btnSave.setDisable (newValue == null);
            if (newValue != null) {
                txtCustomerId.setText (newValue.getId ( ));
                txtCustomerName.setText (newValue.getName ( ));
                txtCustomerAddress.setText (newValue.getAddress ( ));
                txtCustomerNIC.setText (newValue.getNic ( ));
                txtCustomerTitle.setText (newValue.getTitle ( ));
                txtCustomerCity.setText (newValue.getCity ( ));
                txtCustomerProvince.setText (newValue.getProvince ( ));
                txtCustomerPostalCode.setText (newValue.getPostalCode ( ));
                txtCustomerId.setDisable (true);
                txtCustomerName.setDisable (false);
                txtCustomerNIC.setDisable (true);
                txtCustomerAddress.setDisable (false);
                txtCustomerTitle.setDisable (false);
                txtCustomerCity.setDisable (false);
                txtCustomerProvince.setDisable (false);
                txtCustomerPostalCode.setDisable (false);
            }
        });
        txtCustomerAddress.setOnAction (event -> btnSave.fire ( ));
        loadAllCustomers ( );

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

    public void initUI() {

        btnSave.setDisable (true);
        btnDelete.setDisable (true);
        txtCustomerId.setDisable (true);
        txtCustomerName.setDisable (true);
        txtCustomerAddress.setDisable (true);
        txtCustomerTitle.setDisable (true);
        txtCustomerNIC.setDisable (true);
        txtCustomerCity.setDisable (true);
        txtCustomerProvince.setDisable (true);
        txtCustomerPostalCode.setDisable (true);

    }

    public void clear() {
        txtCustomerId.clear ( );
        txtCustomerName.clear ( );
        txtCustomerAddress.clear ( );
        txtCustomerTitle.clear ( );
        txtCustomerNIC.clear ( );
        txtCustomerCity.clear ( );
        txtCustomerProvince.clear ( );
        txtCustomerPostalCode.clear ( );
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


    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        clear ( );
        txtCustomerId.setText (generateNewId ( ));
        txtCustomerName.requestFocus ( );
        btnSave.setDisable (true);
        btnSave.setText ("Save");
        txtCustomerId.setDisable (false);
        btnDelete.setDisable (true);
        txtCustomerName.setDisable (false);
        txtCustomerAddress.setDisable (false);
        txtCustomerTitle.setDisable (false);
        txtCustomerNIC.setDisable (false);
        txtCustomerCity.setDisable (false);
        txtCustomerProvince.setDisable (false);
        txtCustomerPostalCode.setDisable (false);
        loadToTable ( );
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {

        String id = txtCustomerId.getText ( );
        String name = txtCustomerName.getText ( );
        String address = txtCustomerAddress.getText ( );
        String nic = txtCustomerNIC.getText ( );
        String title = txtCustomerTitle.getText ( );
        String city = txtCustomerCity.getText ( );
        String province = txtCustomerProvince.getText ( );
        String postalCode = txtCustomerPostalCode.getText ( );
        /** Save Customer **/
        if (btnSave.getText ( ).equalsIgnoreCase ("Save")) {
            CustomerDTO customerDTO = new CustomerDTO (id, name, address, nic, title, city, province, postalCode);
            try {
                customerBO.addCustomer (customerDTO);
            } catch (SQLException throwables) {
                throwables.printStackTrace ( );
            } catch (ClassNotFoundException e) {
                e.printStackTrace ( );
            }
        }
        /** Update Customer **/
        if (btnSave.getText ( ).equalsIgnoreCase ("Update")) {
            CustomerDTO customerDTO = new CustomerDTO (id, name, address, nic, title, city, province, postalCode);
            try {
                customerBO.updateCustomer (customerDTO);
            } catch (SQLException throwables) {
                throwables.printStackTrace ( );
            } catch (ClassNotFoundException e) {
                e.printStackTrace ( );
            }
            CustomerTM selectedCustomer = tblCustomers.getSelectionModel ( ).getSelectedItem ( );
            selectedCustomer.setName (name);
            selectedCustomer.setId (id);
            selectedCustomer.setAddress (address);
            selectedCustomer.setNic (nic);
            selectedCustomer.setTitle (title);
            selectedCustomer.setCity (city);
            selectedCustomer.setProvince (province);
            selectedCustomer.setPostalCode (postalCode);
            tblCustomers.refresh ( );
        }nullValidate ( );
        btnAddNewCustomer.fire ( );

        clear ( );
        initUI ( );

    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.ifCustomerExist (id);
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {

        String id = tblCustomers.getSelectionModel ( ).getSelectedItem ( ).getId ( );
        try {
            if (!existCustomer (id)) {
                new Alert (Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show ( );
            }
            customerBO.deleteCustomer (id);
            tblCustomers.getItems ( ).remove (tblCustomers.getSelectionModel ( ).getSelectedItem ( ));
            tblCustomers.getSelectionModel ( ).clearSelection ( );
            initUI ( );
        } catch (SQLException throwables) {
            throwables.printStackTrace ( );
        } catch (ClassNotFoundException e) {
            e.printStackTrace ( );
        }
        clear ( );

    }



    public Object validate() {
        setValidation ( );
        for (TextField textFieldKey : map.keySet ( )) {
            Pattern pattern = map.get (textFieldKey);
            if (!pattern.matcher (textFieldKey.getText ( )).matches ( )) {
                textFieldKey.getParent ( ).setStyle ("-fx-border-color: red");
                return textFieldKey;

            }
            //System.out.println (map.get (textFieldKey));
            textFieldKey.getParent ( ).setStyle ("-fx-border-color:green");
        }
        return true;
    }

    public Object nullValidate() {
        setValidation ( );
        for (TextField textFieldKey : map.keySet ( )) {
            Pattern pattern = map.get (textFieldKey);
            if (!pattern.matcher (textFieldKey.getText ( )).matches ( )) {
                textFieldKey.getParent ( ).setStyle ("-fx-border-color: null");
                return textFieldKey;

            }
            //System.out.println (map.get (textFieldKey));
            textFieldKey.getParent ( ).setStyle ("-fx-border-color: null");
        }
        return true;
    }

    private void setValidation() {
        map.put (txtCustomerName, namePattern);
        map.put (txtCustomerAddress, addressPattern);
        map.put (txtCustomerTitle, titlePattern);
        map.put (txtCustomerNIC, nicPattern);
        map.put (txtCustomerCity, cityPattern);
        map.put (txtCustomerProvince, provincePattern);
        map.put (txtCustomerPostalCode, postalCodePattern);
    }

    public void validationKeyReleased(KeyEvent keyEvent) {
        Object response = validate ( );
        if ((keyEvent.getCode ( ) == KeyCode.ENTER)) {
            if ((response instanceof TextField)) {
                TextField errorText = ( TextField ) response;
                errorText.requestFocus ( );
                btnSave.setDisable (true);
            } else {
                btnSave.requestFocus ( );
                btnSave.setDisable (false);
            }
        }
    }

    private String generateNewId() {
        try {
            return customerBO.generateNewID ( );
        } catch (SQLException e) {
            new Alert (Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage ( )).show ( );
        } catch (ClassNotFoundException e) {
            e.printStackTrace ( );
        }


        if (tblCustomers.getItems ( ).isEmpty ( )) {
            return "C-001";
        } else {
            String id = getLastCustomerId ( );
            int newCustomerId = Integer.parseInt (id.replace ("C-", "")) + 1;
            return String.format ("C-%03d", newCustomerId);
        }

    }

    private String getLastCustomerId() {
        List<CustomerTM> tempCustomersList = new ArrayList<> (tblCustomers.getItems ( ));
        Collections.sort (tempCustomersList);
        return tempCustomersList.get (tempCustomersList.size ( ) + 1).getId ( );
    }

    public void saveOnKeyRelease(KeyEvent keyEvent) {
        if ((keyEvent.getCode ( ) == KeyCode.ENTER)) {
            String id = txtCustomerId.getText ( );
            String name = txtCustomerName.getText ( );
            String address = txtCustomerAddress.getText ( );
            String nic = txtCustomerNIC.getText ( );
            String title = txtCustomerTitle.getText ( );
            String city = txtCustomerCity.getText ( );
            String province = txtCustomerProvince.getText ( );
            String postalCode = txtCustomerPostalCode.getText ( );
            /** Save Customer **/
            if (btnSave.getText ( ).equalsIgnoreCase ("Save")) {
                CustomerDTO customerDTO = new CustomerDTO (id, name, address, nic, title, city, province, postalCode);
                try {
                    customerBO.addCustomer (customerDTO);
                } catch (SQLException throwables) {
                    throwables.printStackTrace ( );
                } catch (ClassNotFoundException e) {
                    e.printStackTrace ( );
                }
            }
            /** Update Customer **/
            if (btnSave.getText ( ).equalsIgnoreCase ("Update")) {
                CustomerDTO customerDTO = new CustomerDTO (id, name, address, nic, title, city, province, postalCode);
                try {
                    customerBO.updateCustomer (customerDTO);
                } catch (SQLException throwables) {
                    throwables.printStackTrace ( );
                } catch (ClassNotFoundException e) {
                    e.printStackTrace ( );
                }
                CustomerTM selectedCustomer = tblCustomers.getSelectionModel ( ).getSelectedItem ( );
                selectedCustomer.setName (name);
                selectedCustomer.setId (id);
                selectedCustomer.setAddress (address);
                selectedCustomer.setNic (nic);
                selectedCustomer.setTitle (title);
                selectedCustomer.setCity (city);
                selectedCustomer.setProvince (province);
                selectedCustomer.setPostalCode (postalCode);
                tblCustomers.refresh ( );
            }nullValidate ( );
            btnAddNewCustomer.fire ( );

            clear ( );
            initUI ( );
        }
    }


}
