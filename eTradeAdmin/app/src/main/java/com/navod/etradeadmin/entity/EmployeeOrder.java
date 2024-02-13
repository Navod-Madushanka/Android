package com.navod.etradeadmin.entity;

import java.io.Serializable;

public class EmployeeOrder implements Serializable {
    private String orderId;
    private String userId;

    public EmployeeOrder() {
    }

    public EmployeeOrder(String orderId, String userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "EmployeeOrder{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
