package com.navod.etrade.callback;

import com.navod.etrade.entity.Product;

import java.util.List;

public interface ProductFetchedCallback {
    void onProductFetched(List<Product> productList);
}
