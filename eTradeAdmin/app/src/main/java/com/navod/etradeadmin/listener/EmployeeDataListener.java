package com.navod.etradeadmin.listener;

import com.navod.etradeadmin.entity.Employee;

public interface EmployeeDataListener {
    void onEmployeeDataReceived(Employee employee);
}
