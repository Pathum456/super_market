package entity;


import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/
public class Orders {
    private String OId;
    private LocalDate date;
    private LocalTime time;
    private String cId;
    private double discount;
    private double total;

    public Orders() {
    }

    public Orders(String OId, LocalDate date, LocalTime time, String cId, double discount, double total) {
        this.OId = OId;
        this.date = date;
        this.time = time;
        this.cId = cId;
        this.discount = discount;
        this.total = total;
    }

    public String getOId() {
        return OId;
    }

    public void setOId(String OId) {
        this.OId = OId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
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

}
