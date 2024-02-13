package com.navod.etradedelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.navod.etradedelivery.callback.EmployeeListCallback;
import com.navod.etradedelivery.entity.Employee;
import com.navod.etradedelivery.service.EmployeeService;
import com.navod.etradedelivery.util.SharedPreferencesManager;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private EditText username, password;
    private Button loginBtn;
    private EmployeeService employeeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser(new EmployeeListCallback() {
                    @Override
                    public void onSuccess(List<Employee> employeeList) {
                        SharedPreferencesManager.saveUserId(MainActivity.this, employeeList.get(0).getEmployeeId());
                        gotoHome();
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.i(TAG, error);
                    }
                });
            }
        });
    }
    private void gotoHome(){
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    private void getUser(EmployeeListCallback callback){
        HashMap<String, Object> conditions = new HashMap<>();
        conditions.put("username", username.getText().toString());
        conditions.put("password", password.getText().toString());

        employeeService.getEmployees(conditions, new EmployeeListCallback() {
            @Override
            public void onSuccess(List<Employee> employeeList) {
                if(employeeList.size() == 1){
                    Log.i(TAG, employeeList.toString());
                    callback.onSuccess(employeeList);
                }else{
                    Log.i(TAG, "Username or password wrong");
                }
            }
            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }
    private void initViews(){
        username = findViewById(R.id.editTextText);
        password = findViewById(R.id.editTextTextPassword);
        loginBtn = findViewById(R.id.button);
        employeeService = new EmployeeService();
    }
}