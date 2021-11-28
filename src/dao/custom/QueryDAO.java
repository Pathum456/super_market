package dao.custom;

import dao.SuperDAO;
import dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public interface QueryDAO extends SuperDAO {
    ArrayList<CustomDTO> getOrderDetailsFromOrderID(String oid) throws SQLException, ClassNotFoundException;
}
