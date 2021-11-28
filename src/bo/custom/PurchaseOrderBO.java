package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public interface PurchaseOrderBO extends SuperBO {
    boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    String generateNewOrderId()throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers()throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems()throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String code)throws SQLException, ClassNotFoundException;

    boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String s)throws SQLException, ClassNotFoundException;
}
