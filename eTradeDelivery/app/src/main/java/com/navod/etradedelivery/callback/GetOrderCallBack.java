package com.navod.etradedelivery.callback;

import com.navod.etradedelivery.entity.Order;

import java.util.List;

public interface GetOrderCallBack {
    void onSuccess(List<Order> orderList);
    void onFailure(String failMessage);
}
