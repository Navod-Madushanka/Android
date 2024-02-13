package com.navod.etrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.navod.etrade.adapter.OrderItemRecyclerViewAdapter;
import com.navod.etrade.entity.OrderItem;

import java.util.List;

public class OrderItemView extends AppCompatActivity {
    private static final String TAG = OrderItemView.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item_view);

        List<OrderItem> orderItems = getIntent().getParcelableArrayListExtra("orderItems");
        Log.i(TAG, orderItems.toString());
        RecyclerView recyclerView = findViewById(R.id.OrderItemRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        OrderItemRecyclerViewAdapter adapter = new OrderItemRecyclerViewAdapter(this, orderItems);
        recyclerView.setAdapter(adapter);

    }
}