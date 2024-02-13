package com.navod.etradeadmin.CallBack;

import com.navod.etradeadmin.entity.Product;

public interface GetProductCallback {
    void getProduct(Product product, String documentId);
}
