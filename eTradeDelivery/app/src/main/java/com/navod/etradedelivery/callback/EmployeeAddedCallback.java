package com.navod.etradedelivery.callback;

public interface EmployeeAddedCallback {
    void onSuccess(String documentId);
    void onFailure(String error);
}
