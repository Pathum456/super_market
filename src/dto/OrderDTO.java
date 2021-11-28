package dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Pathum Kaleesha
 * @day :19.10.21
 * @since : 0.1.0
 **/

public class OrderDTO implements Serializable {
    private String orderId;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private String customerId;
   private double discount;
   private double total;
    private List<OrderDetailDTO> orderDetail;

    public OrderDTO() {
    }


    public OrderDTO(String orderId, LocalDate orderDate, LocalTime orderTime, String customerId, double discount, double total, List<OrderDetailDTO> orderDetail) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.customerId = customerId;
        this.discount = discount;
        this.total = total;
        this.orderDetail = orderDetail;
    }

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public void setOrderDetail(ArrayList<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", orderTime=" + orderTime +
                ", customerId='" + customerId + '\'' +
                ", discount=" + discount +
                ", total=" + total +
                ", orderDetail=" + orderDetail +
                '}';
    }
}
