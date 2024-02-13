package com.navod.etradedelivery.entity;

import android.os.Parcel;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private String productId;
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "OrderItem{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
    public OrderItem(Parcel in) {
        productId = in.readString();
        quantity = in.readInt();
    }
}
