package com.navod.etradedelivery.callback;

import com.navod.etradedelivery.entity.EmployeeOrder;

import java.util.List;

public interface GetEmployeeOrdersCallback {
    void onSuccess(List<EmployeeOrder> employeeOrderList);
    void onFailure(String error);
}
