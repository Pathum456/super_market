package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailsDAO;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;
/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public boolean add(OrderDetails dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order Detail` (oID, iCode,orderQTY, discount, price) VALUES (?,?,?,?,?)", dto.getOId (),dto.getItemCode (),dto.getQty (),dto.getDiscount (),dto.getPrice ());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(OrderDetails orderDetailDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetails search(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }
}
