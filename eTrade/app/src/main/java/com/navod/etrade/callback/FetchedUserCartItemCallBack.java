package com.navod.etrade.callback;

import com.navod.etrade.entity.CartItem;

import java.util.List;

public interface FetchedUserCartItemCallBack {
    void onSuccess(List<CartItem> cartItemList);
}
