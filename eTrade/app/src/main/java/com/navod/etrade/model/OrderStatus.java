package com.navod.etrade.model;

public enum OrderStatus {
    PENDING("Pending"),
    PROCESSING("Processing"),
    SHIPPING("Shipping"),
    DELIVERED("Delivered"),
    CANCELED("Canceled");

    private final String status;

    OrderStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return status;
    }

}
