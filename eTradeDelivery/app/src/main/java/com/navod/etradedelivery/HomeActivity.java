package com.navod.etradedelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.navod.etradedelivery.adapter.DeliveryOrderAdapter;
import com.navod.etradedelivery.callback.GetEmployeeOrdersCallback;
import com.navod.etradedelivery.callback.GetOrderCallBack;
import com.navod.etradedelivery.callback.UserCallback;
import com.navod.etradedelivery.entity.EmployeeOrder;
import com.navod.etradedelivery.entity.Order;
import com.navod.etradedelivery.entity.User;
import com.navod.etradedelivery.service.EmployeeOrderService;
import com.navod.etradedelivery.service.OrderService;
import com.navod.etradedelivery.service.UserService;
import com.navod.etradedelivery.util.SharedPreferencesManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getName();
    private RecyclerView recyclerView;
    private EmployeeOrder employeeOrder;
    private User orderUser;
    private Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        initiateDataLoading();
    }
    private void loadData() {
        getUser(new UserCallback() {
            @Override
            public void onUserResult(User user) {
                orderUser = user;
                getOrder();
            }
        });
    }

    private void getUser(UserCallback callback) {
        UserService userService = new UserService();
        userService.getUserById(employeeOrder.getUserId(), new UserCallback() {
            @Override
            public void onUserResult(User user) {
                orderUser = user;
                callback.onUserResult(user);
            }
        });
    }

    private void getOrder() {
        OrderService orderService = new OrderService();
        HashMap<String, Object> conditions = new HashMap<>();
        conditions.put("orderId", employeeOrder.getOrderId());
        orderService.getOrder(orderUser.getUserId(), conditions, new GetOrderCallBack() {
            @Override
            public void onSuccess(List<Order> orderList) {
                if (!orderList.isEmpty()) {
                    order = orderList.get(0);
                    getEmployeeOrderList(SharedPreferencesManager.getUserId(HomeActivity.this));
                }
            }

            @Override
            public void onFailure(String failMessage) {
                Log.i(TAG, failMessage);
            }
        });
    }

    private void getEmployeeOrderList(String employeeId) {
        EmployeeOrderService employeeOrderService = new EmployeeOrderService(employeeId);
        employeeOrderService.getEmployeeOrders(Collections.emptyMap(), new GetEmployeeOrdersCallback() {
            @Override
            public void onSuccess(List<EmployeeOrder> employeeOrderList) {
                if (!employeeOrderList.isEmpty()) {
                    employeeOrder = employeeOrderList.get(0);
                    setRecyclerView(employeeOrderList);
                }
            }

            @Override
            public void onFailure(String error) {
                Log.i(TAG, error);
            }
        });
    }

    private void setRecyclerView(List<EmployeeOrder> employeeOrderList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        new DeliveryOrderAdapter(HomeActivity.this, employeeOrderList, order, orderUser);
    }
    private void initiateDataLoading() {
        loadData();
    }
    private void initViews(){
        recyclerView = findViewById(R.id.employeeOrdersRecyclerView);
    }
}