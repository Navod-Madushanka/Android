package com.navod.etradeadmin.CallBack;

import com.navod.etradeadmin.entity.Product;

public interface ProductCallback {
    void onGetProduct(Product product);
    void onError(String errorMessage);
}
