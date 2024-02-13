package com.navod.etrade.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {
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
    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
