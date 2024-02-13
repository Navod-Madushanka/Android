package com.navod.etradeadmin.CallBack;

import com.navod.etradeadmin.entity.User;

import java.util.List;

public interface GetUserListCallback {
    void onSuccess(List<User> userList);
    void onFailure(String error);
}
