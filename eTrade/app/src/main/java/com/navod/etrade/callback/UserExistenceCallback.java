package com.navod.etrade.callback;

public interface UserExistenceCallback {
    void onResult(boolean userExists,String userId);
}

