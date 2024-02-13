package com.navod.etradedelivery.callback;

import com.navod.etradedelivery.entity.Employee;

import java.util.List;

public interface EmployeeListCallback {
    void onSuccess(List<Employee> employeeList);
    void onFailure(String error);
}
