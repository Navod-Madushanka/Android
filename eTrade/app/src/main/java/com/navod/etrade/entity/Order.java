package com.navod.etrade.entity;

import com.navod.etrade.model.OrderStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private String customerId;
    private List<OrderItem> orderItems;
    private double totalPrice;
    private Date orderDate;
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(String orderId, String customerId, List<OrderItem> orderItems, double totalPrice, Date orderDate, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderItems=" + orderItems +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
