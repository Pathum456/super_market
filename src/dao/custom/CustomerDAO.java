package dao.custom;



import dao.CrudDAO;
import entity.Customer;

import java.sql.SQLException;
/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/

public interface CustomerDAO extends CrudDAO<Customer, String> {
    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;
    String generateNewID() throws SQLException, ClassNotFoundException;
}
