package com.navod.etrade.entity;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String id;
    private String productId;
    private int quantity;
    private double price;

    public CartItem() {
    }

    public CartItem(String id,String productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
