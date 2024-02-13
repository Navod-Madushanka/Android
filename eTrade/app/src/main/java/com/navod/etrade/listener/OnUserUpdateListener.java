package com.navod.etrade.listener;

import com.navod.etrade.entity.User;

public interface OnUserUpdateListener {
    void onUserUpdated(User updatedUser);
    void handleUpdatedUser(User user);
}
