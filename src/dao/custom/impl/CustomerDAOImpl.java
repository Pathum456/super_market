package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/


public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public boolean add(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer ( custID,custTitle, custName,custAddress,city,province,postalCode,nationalID) VALUES (?,?,?,?,?,?,?,?)", dto.getId(), dto.getTitle (), dto.getName (),dto.getAddress (),dto.getCity (),dto.getProvince (),dto.getPostalCode (),dto.getNic ());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE  custID=?", id);
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Customer SET custTitle=?,custName=?, custAddress=?,city=?,province=?,postalCode=? WHERE  custID=?",dto.getId (),dto.getTitle (), dto.getName (),dto.getAddress (),dto.getCity (),dto.getProvince (),dto.getPostalCode ());
    }

    @Override
    public Customer search(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE  custID=?", nic);
        rst.next();
        return new Customer(rst.getString(1), rst.getString(2),rst.getString(3),nic,rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8));
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            allCustomers.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8)));
        }
        return allCustomers;
    }


    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT  custID FROM Customer WHERE  custID=?", id).next();
    }


    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT  custID FROM Customer ORDER BY  custID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("custID");
            int newCustomerId = Integer.parseInt(id.replace("C-", "")) + 1;
            return String.format("C-%03d", newCustomerId);
        } else {
            return "C-001";
        }
    }
}
