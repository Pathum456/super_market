package dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public interface CrudDAO<T, ID> extends SuperDAO {

    boolean add(T t) throws SQLException, ClassNotFoundException;

    boolean delete(ID id) throws SQLException, ClassNotFoundException;

    boolean update(T t) throws SQLException, ClassNotFoundException;

    T search(ID id) throws SQLException, ClassNotFoundException;

    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

}
