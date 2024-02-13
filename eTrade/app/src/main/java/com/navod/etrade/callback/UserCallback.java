package com.navod.etrade.callback;

import com.navod.etrade.entity.User;

public interface UserCallback {
    void onUserResult(User user);
}
