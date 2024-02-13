package com.navod.etrade.callback;

import com.navod.etrade.entity.IndividualMessage;

import java.util.List;

public interface GetIndividualMessageListCallback {
    void onSuccess(List<IndividualMessage> individualMessageList);
    void onFailure(String error);
}
