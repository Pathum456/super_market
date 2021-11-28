package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class ItemBOImpl implements ItemBO {

    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item i : all) {
            allItems.add(new ItemDTO(i.getCode (),i.getDescription (),i.getPackSize (),i.getUnitPrice (),i.getQtyOnHand (),i.getDiscount ()));
        }
        return allItems;

    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(dto.getItemCode (), dto.getDescription(),dto.getPackSize (), dto.getUnitPrice() ,dto.getQtyOnHand(),dto.getDiscount ()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(
                dto.getItemCode(),
                dto.getDescription(),
                dto.getPackSize (),
                dto.getUnitPrice(),
                dto.getQtyOnHand (),
                dto.getDiscount ()));
    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.ifItemExist(code);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }
}
