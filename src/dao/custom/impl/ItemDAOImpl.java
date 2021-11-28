package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO item (itemCode, description,packSize, unitPrice, qtyOnHand,discount) VALUES (?,?,?,?,?,?)", dto.getCode(), dto.getDescription(),dto.getPackSize (), dto.getUnitPrice(), dto.getQtyOnHand(),dto.getDiscount ());
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM item WHERE itemCode=?", code);
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?", dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand(), dto.getCode());
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM item WHERE itemCode=?", code);
        rst.next();
        return new Item(rst.getString (1), rst.getString(2), rst.getString (3), rst.getDouble (4), rst.getInt (5), rst.getDouble (6));
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM item");
        while (rst.next()) {
            allItems.add(new Item( rst.getString (1),rst.getString(2), rst.getString (3), rst.getDouble (4), rst.getInt (5), rst.getDouble (6)));
        }
        return allItems;
    }


    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT itemCode FROM item WHERE itemCode=?", code).next();
    }


    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString(1);
            int newItemId = Integer.parseInt(id.replace("I-", "")) + 1;
            return String.format("I-%03d", newItemId);
        } else {
            return "I-001";
        }
    }
}
