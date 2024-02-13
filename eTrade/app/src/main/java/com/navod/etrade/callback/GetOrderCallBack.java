package com.navod.etrade.callback;

import com.navod.etrade.entity.Order;

import java.util.List;

public interface GetOrderCallBack {
    void onSuccess(List<Order> orderList);
    void onFailure(String failMessage);
}
