package com.navod.etrade.callback;

public interface OrderAddedCallback {
    void OnSuccess(String successMessage);
    void OnFailure(String failMessage);
}
