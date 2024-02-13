package com.navod.etrade.callback;

public interface DeleteCartItemCallback {
    void onSuccess(String successMessage);
    void onFailure(String errorMessage);
}
