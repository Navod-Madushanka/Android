package com.navod.etradeadmin.CallBack;

import com.navod.etradeadmin.entity.Employee;

import java.util.List;

public interface EmployeeListCallback {
    void onSuccess(List<Employee> employeeList);
    void onFailure(String error);
}
