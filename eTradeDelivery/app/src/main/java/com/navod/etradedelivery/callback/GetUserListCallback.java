package com.navod.etradedelivery.callback;

import com.navod.etradedelivery.entity.User;

import java.util.List;

public interface GetUserListCallback {
    void onSuccess(List<User> userList);
    void onFailure(String error);
}
