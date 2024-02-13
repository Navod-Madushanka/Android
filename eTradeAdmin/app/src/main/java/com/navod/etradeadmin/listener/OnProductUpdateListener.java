package com.navod.etradeadmin.listener;

import com.navod.etradeadmin.entity.Product;

public interface OnProductUpdateListener {
    void onProductUpdated(Product updatedProduct);
    void handleUpdatedProduct(Product product);
}
