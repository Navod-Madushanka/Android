package com.navod.etrade.callback;

public interface AddToCartCallback {
    void onSuccess(String successMassage);
    void onFailure(String failMassage);
}
