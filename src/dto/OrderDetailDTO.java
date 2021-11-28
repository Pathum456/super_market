package dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/

public class OrderDetailDTO implements Serializable {
   private String OId;
   private String id;
   private String name;
   private String itemCode;
   private String description;
   private int qty;
   private double discount;
   private double total;


    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String OId, String id, String name, String itemCode, String description, int qty, double discount, double total) {
        this.OId = OId;
        this.id = id;
        this.name = name;
        this.itemCode = itemCode;
        this.description = description;
        this.qty = qty;
        this.discount = discount;
        this.total = total;
    }

    public String getOId() {
        return OId;
    }

    public void setOId(String OId) {
        this.OId = OId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "OId='" + OId + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", discount=" + discount +
                ", total=" + total +
                '}';
    }
}
