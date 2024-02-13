package com.navod.etradeadmin.CallBack;

public interface ImageUploadCallback {
    void onSuccess(String downloadUrl);
    void onFailure(String error);
}
