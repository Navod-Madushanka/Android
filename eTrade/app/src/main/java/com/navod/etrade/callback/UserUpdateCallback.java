package com.navod.etrade.callback;

public interface UserUpdateCallback {
    void onSuccess(String successMessage);
    void onFailure(String errorMessage);
}
