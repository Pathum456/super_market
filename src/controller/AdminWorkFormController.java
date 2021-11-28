package controller;

import bo.BoFactory;
import bo.custom.ItemBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDTO;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import view.tdm.ItemTM;

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
public class AdminWorkFormController {
    private final ItemBO itemBO = ( ItemBO ) BoFactory.getBOFactory ( ).getBO (BoFactory.BoTypes.ITEM);
    public AnchorPane root;
    public JFXButton btnAddNewItem;
    public JFXTextField txtCode;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtPackSize;
    public JFXTextField txtDiscount;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<ItemTM> tblItems;
    public ImageView imgHome;
    public Label lblHome;
    public ImageView imgItem;
    public ImageView ImgReport;
    public Label lblSelect;
    public Label lblDescription;
    public Label lblDate_Time;
    public ImageView imgBusiness;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<> ( );

    Pattern descriptionPattern = Pattern.compile ("^[A-z.[]]]{2,}$");
    Pattern packSizePattern = Pattern.compile ("^[1-9][0-9]*$");
    Pattern qtyPattern = Pattern.compile ("^[0-9][0-9]*$");
    Pattern discountPattern = Pattern.compile ("^[0-9][0-9]*$");
    Pattern unitPricePattern = Pattern.compile ("^[0-9][0-9]*$");

    private double price;
    private double value;
    private double unitPrice;
    private double discount;
    private int qty;

    public void initialize() {
        initUI ( );
        lblSelect.setText ("Welcome");
        lblDescription.setText ("Select from above ");
        tblItems.getColumns ( ).get (0).setCellValueFactory (new PropertyValueFactory<> ("code"));
        tblItems.getColumns ( ).get (1).setCellValueFactory (new PropertyValueFactory<> ("description"));
        tblItems.getColumns ( ).get (2).setCellValueFactory (new PropertyValueFactory<> ("packSize"));
        tblItems.getColumns ( ).get (3).setCellValueFactory (new PropertyValueFactory<> ("unitPrice"));
        tblItems.getColumns ( ).get (4).setCellValueFactory (new PropertyValueFactory<> ("qtyOnHand"));
        tblItems.getColumns ( ).get (5).setCellValueFactory (new PropertyValueFactory<> ("discount"));
        loadToTable ( );
    }

    public void loadToTable() {
        tblItems.getSelectionModel ( ).selectedItemProperty ( ).addListener ((observable, oldValue, newValue) -> {
            btnDelete.setDisable (newValue == null);
            btnSave.setText (newValue != null ? "Update" : "Save");
            btnSave.setDisable (newValue == null);
            if (newValue != null) {
                txtCode.setText (newValue.getCode ( ));
                txtDescription.setText (newValue.getDescription ( ));
                txtPackSize.setText (newValue.getPackSize ( ));
                txtUnitPrice.setText (String.valueOf (newValue.getUnitPrice ( )));
                txtQtyOnHand.setText (String.valueOf (newValue.getQtyOnHand ( )));
                txtDiscount.setText (String.valueOf (newValue.getDiscount ( )));
                txtCode.setDisable (true);
                txtDescription.setDisable (false);
                txtPackSize.setDisable (false);
                txtUnitPrice.setDisable (false);
                txtQtyOnHand.setDisable (false);
                txtDiscount.setDisable (false);
            }

        });
        txtDiscount.setOnAction (event -> btnSave.fire ( ));
        loadAllItems ( );

    }

    private void loadAllItems() {
        tblItems.getItems ( ).clear ( );
        /** Get all customers **/
        try {
            ArrayList<ItemDTO> allItem = itemBO.getAllItems ( );
            for (ItemDTO item : allItem) {
                tblItems.getItems ( ).add (new ItemTM (item.getItemCode ( ), item.getDescription ( ), item.getPackSize ( ), item.getUnitPrice ( ), item.getQtyOnHand ( ), item.getDiscount ( )));
            }
        } catch (SQLException e) {
            new Alert (Alert.AlertType.ERROR, e.getMessage ( )).show ( );
        } catch (ClassNotFoundException e) {
            new Alert (Alert.AlertType.ERROR, e.getMessage ( )).show ( );
        }


    }

    private void setValidation() {
        map.put (txtDescription, descriptionPattern);
        map.put (txtPackSize, packSizePattern);
        map.put (txtUnitPrice, unitPricePattern);
        map.put (txtQtyOnHand, qtyPattern);
        map.put (txtDiscount, discountPattern);

    }

    public void removeStyle() {
        txtDescription.getParent ( ).setStyle (null);
        txtPackSize.getParent ( ).setStyle (null);
        txtUnitPrice.getParent ( ).setStyle (null);
        txtDiscount.getParent ( ).setStyle (null);
        txtQtyOnHand.getParent ( ).setStyle (null);
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
                setDiscountOnKeyRelease ( );
                removeStyle ( );

            }
        }
    }

    private void initUI() {
        btnSave.setDisable (true);
        btnDelete.setDisable (true);
        txtCode.setDisable (true);
        txtDescription.setDisable (true);
        txtPackSize.setDisable (true);
        txtUnitPrice.setDisable (true);
        txtQtyOnHand.setDisable (true);
        txtDiscount.setDisable (true);
    }

    public void clear() {
        txtDiscount.clear ( );
        txtCode.clear ( );
        txtQtyOnHand.clear ( );
        txtUnitPrice.clear ( );
        txtPackSize.clear ( );
        txtDescription.clear ( );
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

    public void setDiscountOnKeyRelease() {
        qty = Integer.parseInt (txtQtyOnHand.getText ( ));
        discount = Double.parseDouble (txtDiscount.getText ( ));
        unitPrice = Double.parseDouble (txtUnitPrice.getText ( ));
        price = unitPrice * discount / 100;
        value = unitPrice - price;
        txtUnitPrice.setText (String.valueOf (value));
        txtDiscount.setText (String.valueOf (discount));
        System.out.println (qty);
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        this.value = value;
        double unitPrice= Double.parseDouble (txtUnitPrice.getText ());
        this.discount = discount;
        String code = txtCode.getText();
        String description = txtDescription.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        /** Save Item **/
        if (btnSave.getText ( ).equalsIgnoreCase ("Save")) {
            ItemDTO dto = new ItemDTO (txtCode.getText ( ), txtDescription.getText ( ), txtPackSize.getText ( ), unitPrice, qty, discount);
            try {
                itemBO.addItem (dto);
                loadToTable ( );
                clear ( );
            } catch (SQLException throwables) {
                throwables.printStackTrace ( );
            } catch (ClassNotFoundException e) {
                e.printStackTrace ( );
            }

        }
        /** Update Item **/
        if (btnSave.getText ( ).equalsIgnoreCase ("Update")) {
            try {
                if (!existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
                }
                /*Update Item*/
                ItemDTO dto = new ItemDTO(txtCode.getText ( ), txtDescription.getText ( ), txtPackSize.getText ( ), value, qty, discount);
                boolean b=itemBO.updateItem(dto);

                ItemTM selectedItem = tblItems.getSelectionModel().getSelectedItem();
                selectedItem.setDescription(description);
                selectedItem.setQtyOnHand(qtyOnHand);
                selectedItem.setUnitPrice(unitPrice);
                selectedItem.setPackSize (txtPackSize.getText ());
                selectedItem.setDiscount (Double.parseDouble (txtDiscount.getText ()));
                loadToTable ( );
                clear ( );
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert (Alert.AlertType.CONFIRMATION,e.getMessage ()).showAndWait ();
            }
        }
        initUI ();
        clear ( );

    }
    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemBO.ifItemExist(code);
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        String code = tblItems.getSelectionModel().getSelectedItem().getCode();
        try {
            if (!existItem(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
            }

            itemBO.deleteItem(code);
            tblItems.getItems().remove(tblItems.getSelectionModel().getSelectedItem());
            tblItems.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        clear ( );
        txtCode.setText (generateNewId() );
        txtCode.setDisable (false);
        txtDescription.setDisable (false);
        txtPackSize.setDisable (false);
        txtUnitPrice.setDisable (false);
        txtQtyOnHand.setDisable (false);
        txtDiscount.setDisable (false);
    }

    public void SaveOnKeyRelease(KeyEvent keyEvent) {

    }
    private String generateNewId() {
        try {
            return itemBO.generateNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I-001";
    }

}
