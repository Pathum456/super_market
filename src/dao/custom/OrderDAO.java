package dao.custom;

import dao.CrudDAO;
import entity.Orders;

import java.sql.SQLException;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public interface OrderDAO extends CrudDAO<Orders, String> {
    boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException;
    String generateNewOrderId() throws SQLException, ClassNotFoundException;
}
