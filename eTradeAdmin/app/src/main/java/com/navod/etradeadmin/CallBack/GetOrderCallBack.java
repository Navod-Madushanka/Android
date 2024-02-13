package com.navod.etradeadmin.CallBack;

import com.navod.etradeadmin.entity.Order;

import java.util.List;

public interface GetOrderCallBack {
    void onSuccess(List<Order> orderList);
    void onFailure(String failMessage);
}
