package com.navod.etradeadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.navod.etradeadmin.CallBack.EmployeeAddedCallback;
import com.navod.etradeadmin.entity.Employee;
import com.navod.etradeadmin.listener.EmployeeDataListener;
import com.navod.etradeadmin.service.EmployeeService;

public class EmployeeRegistration extends AppCompatActivity implements EmployeeDataListener {
    private static final String TAG = EmployeeRegistration.class.getName();
    public Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_registration);
        initView();
    }
    private void initView(){
        saveBtn = findViewById(R.id.button9);
    }

    @Override
    public void onEmployeeDataReceived(Employee employee) {
        Log.i(TAG, employee.toString());
        EmployeeService employeeService = new EmployeeService();
        employeeService.addEmployee(employee, new EmployeeAddedCallback() {
            @Override
            public void onSuccess(String documentId) {
                Log.i(TAG, documentId);
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, error);
            }
        });
    }
}