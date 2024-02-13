package com.navod.etradeadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.navod.etradeadmin.CallBack.UpdateOrderDetailsCallback;
import com.navod.etradeadmin.adapter.OrderItemRecyclerViewAdapter;
import com.navod.etradeadmin.entity.Order;
import com.navod.etradeadmin.models.OrderStatus;
import com.navod.etradeadmin.service.OrderService;

import java.util.HashMap;

public class OrderItemViewActivity extends AppCompatActivity {
    private static final String TAG = OrderItemViewActivity.class.getName();
    private OrderService orderService;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item_view);
        orderService = new OrderService();

        getOrderFromIntent();
        setupRecyclerView();

        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrder(OrderStatus.SHIPPING);
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrder(OrderStatus.CANCELED);
            }
        });
    }
    private void updateOrder(OrderStatus orderStatus){
        HashMap<String, Object> fields = new HashMap<>();
        fields.put("orderStatus", orderStatus);
        orderService.updateOrder(order.getOrderId(), fields, new UpdateOrderDetailsCallback() {
            @Override
            public void update(boolean success) {
                if(success){
                    gotoSelectEmployee();
                }
            }
        });
    }
    private void gotoSelectEmployee(){
        Intent intent = new Intent(OrderItemViewActivity.this, SelectDelivery.class);
        intent.putExtra("order", order);
        startActivity(intent);
    }
    private void getOrderFromIntent() {
        order = (Order) getIntent().getSerializableExtra("order");
        if (order == null) {
            Log.e(TAG, "Failed to retrieve the 'order' object from the Intent.");
        }
    }
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.orderItemRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (order != null) {
            OrderItemRecyclerViewAdapter adapter = new OrderItemRecyclerViewAdapter(this, order.getOrderItems());
            recyclerView.setAdapter(adapter);
        } else {
            Intent intent = new Intent(OrderItemViewActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

}