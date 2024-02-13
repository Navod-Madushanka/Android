package com.navod.etradeadmin.CallBack;

import com.navod.etradeadmin.entity.Product;

import java.util.List;

public interface ProductFetchedCallback {
    void onProductsFetched(List<Product> productList);
}
