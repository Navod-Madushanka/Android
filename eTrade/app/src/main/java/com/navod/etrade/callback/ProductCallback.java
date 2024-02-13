package com.navod.etrade.callback;

import com.navod.etrade.entity.Product;

public interface ProductCallback {
    void onGetProduct(Product product);
    void onError(String errorMessage);
}
