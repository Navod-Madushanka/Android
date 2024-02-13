package com.navod.etrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.navod.etrade.util.CurrentDate;

public class Order extends AppCompatActivity {
    private TextView date;
    private Button completeOrderBtn, currentOrderBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initViews();
        setDate();
        completeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceCompleteOrderFragment();
            }
        });
        currentOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceOrderFragment();
            }
        });
    }
    private void replaceCompleteOrderFragment(){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        DeliveredOrders deliveredOrders = new DeliveredOrders();
        fragmentTransaction.replace(R.id.fragmentContainerView2, deliveredOrders);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void replaceOrderFragment(){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        CurrentOrdersFragment currentOrdersFragment = new CurrentOrdersFragment();
        fragmentTransaction.replace(R.id.fragmentContainerView2, currentOrdersFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void setDate(){
        date.setText(CurrentDate.getCurrentDate().toString());
    }
    private void initViews(){
        date = findViewById(R.id.textView34);
        completeOrderBtn = findViewById(R.id.button11);
        currentOrderBtn = findViewById(R.id.button10);
    }
}