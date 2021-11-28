package entity;

import java.math.BigDecimal;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class OrderDetails {
    private String OId;
    private String itemCode;
    private int qty;
    private double discount;
    private double Price;

    public OrderDetails() {
    }

    public OrderDetails(String OId, String itemCode, int qty, double discount, double price) {
        this.OId = OId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.discount = discount;
        Price = price;
    }

    public String getOId() {
        return OId;
    }

    public void setOId(String OId) {
        this.OId = OId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
