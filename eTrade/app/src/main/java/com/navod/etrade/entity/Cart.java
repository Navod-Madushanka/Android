package com.navod.etrade.entity;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> items;

    public Cart() {
    }

    public Cart(List<CartItem> items) {
        this.items = items;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
