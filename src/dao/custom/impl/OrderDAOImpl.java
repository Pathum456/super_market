package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean add(Orders dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` (orderID, orderDate, orderTime,cId,totalDiscount,totalPrice) VALUES (?,?,?,?,?,?)", dto.getOId (),dto.getDate (),dto.getTime (),dto.getcId (),dto.getDiscount (),dto.getTotal ());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean update(Orders orderDTO) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public Orders search(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `Order` WHERE orderID=?", oid);
        rst.next();
        return new Orders(rst.getString(1), LocalDate.parse(rst.getString(2)), LocalTime.parse ( rst.getString(3)),rst.getString (4),rst.getDouble (5),rst.getDouble (6));
    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderID FROM `Order` WHERE orderID=?", oid);
        return rst.next();
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT orderID FROM `Order` ORDER BY orderID DESC LIMIT 1;");
        return rst.next() ? String.format("OD-%03d", (Integer.parseInt(rst.getString("orderID").replace("OD-", "")) + 1)) : "OD-001";
    }
}
