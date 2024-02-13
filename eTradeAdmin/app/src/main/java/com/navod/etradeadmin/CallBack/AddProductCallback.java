package com.navod.etradeadmin.CallBack;

public interface AddProductCallback {
    void onSuccess(String productId);
    void onFailure(String error);
}
