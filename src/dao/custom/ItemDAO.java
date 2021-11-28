package dao.custom;

import dao.CrudDAO;
import entity.Item;

import java.sql.SQLException;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public interface ItemDAO extends CrudDAO<Item, String> {
    boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;
}
