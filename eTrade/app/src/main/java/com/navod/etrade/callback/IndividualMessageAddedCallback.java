package com.navod.etrade.callback;

import com.navod.etrade.entity.IndividualMessage;

public interface IndividualMessageAddedCallback {
    void onSuccess(IndividualMessage individualMessage);
    void onFailure(String error);
}
