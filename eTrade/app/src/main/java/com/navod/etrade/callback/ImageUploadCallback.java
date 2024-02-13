package com.navod.etrade.callback;

public interface ImageUploadCallback {
    void onSuccess(String downloadUrl);
    void onFailure(String error);
}
