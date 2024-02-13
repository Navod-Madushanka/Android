package com.navod.etradeadmin.CallBack;

public interface EmployeeAddedCallback {
    void onSuccess(String documentId);
    void onFailure(String error);
}
