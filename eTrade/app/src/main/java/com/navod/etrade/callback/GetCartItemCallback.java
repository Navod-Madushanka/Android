package com.navod.etrade.callback;

import com.navod.etrade.entity.CartItem;

import java.util.List;

public interface GetCartItemCallback {
    void onSuccess(List<CartItem> cartItems);
    void onFailure(String error);
}
