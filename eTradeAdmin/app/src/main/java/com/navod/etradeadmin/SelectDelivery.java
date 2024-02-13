package com.navod.etradeadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.navod.etradeadmin.CallBack.EmployeeListCallback;
import com.navod.etradeadmin.adapter.EmployeeSelectAdapter;
import com.navod.etradeadmin.entity.Employee;
import com.navod.etradeadmin.entity.Order;
import com.navod.etradeadmin.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectDelivery extends AppCompatActivity {
    private static final String TAG = SelectDelivery.class.getName();
    private EmployeeService employeeService;
    private RecyclerView recyclerView;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_delivery);
        getOrderFromIntent();
        recyclerView = findViewById(R.id.employeeSelectRecyclerView);
        getAllEmployees();
    }
    private void getOrderFromIntent(){
        order =(Order) getIntent().getSerializableExtra("order");
        if(order == null){
            Log.i(TAG, "Order is null");
        }
    }
    private void getAllEmployees(){
        HashMap<String, Object> conditions = new HashMap<>();
        conditions = new HashMap<>();
        conditions.put("status", true);
        getEmployeeList(conditions, new EmployeeListCallback() {
            @Override
            public void onSuccess(List<Employee> employeeList) {
                setRecyclerView(employeeList);
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, error);
            }
        });
    }
    private void setRecyclerView(List<Employee> employeeList){
        recyclerView.removeAllViews();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectDelivery.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        EmployeeSelectAdapter employeeSelectAdapter = new EmployeeSelectAdapter(SelectDelivery.this, employeeList, order);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(employeeSelectAdapter);
    }
    private void getEmployeeList(HashMap<String, Object> conditions, EmployeeListCallback callback){
        employeeService = new EmployeeService();
        employeeService.getEmployees(conditions, new EmployeeListCallback() {
            @Override
            public void onSuccess(List<Employee> employeeList) {
                callback.onSuccess(employeeList);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });

    }
}