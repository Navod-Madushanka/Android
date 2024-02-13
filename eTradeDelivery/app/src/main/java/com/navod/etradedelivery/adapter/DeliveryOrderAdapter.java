package com.navod.etradedelivery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.navod.etradedelivery.R;
import com.navod.etradedelivery.callback.GetOrderCallBack;
import com.navod.etradedelivery.callback.UserCallback;
import com.navod.etradedelivery.entity.EmployeeOrder;
import com.navod.etradedelivery.entity.Order;
import com.navod.etradedelivery.entity.User;
import com.navod.etradedelivery.service.OrderService;
import com.navod.etradedelivery.service.UserService;

import java.util.HashMap;
import java.util.List;

public class DeliveryOrderAdapter extends RecyclerView.Adapter<DeliveryOrderAdapter.ViewHolder> {
    private List<EmployeeOrder> employeeOrderList;
    private Order order;
    private User user;
    private Context context;
    public DeliveryOrderAdapter(Context context, List<EmployeeOrder> employeeOrderList, Order order, User user){
        this.context = context;
        this.employeeOrderList = employeeOrderList;
        this.order = order;
        this.user = user;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.delivery_order_layout, parent, false);
        return new ViewHolder(inflate,context, employeeOrderList, order, user);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmployeeOrder employeeOrder = employeeOrderList.get(position);
        holder.bind(employeeOrder);
    }

    @Override
    public int getItemCount() {
        return employeeOrderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private List<EmployeeOrder> employeeOrderList;
        private Order order;
        private User user;
        private EmployeeOrder employeeOrder;
        private UserService userService;
        private OrderService orderService;
        private TextView orderId, username, orderItemSize;
        private static final String TAG = ViewHolder.class.getName();
        public ViewHolder(@NonNull View itemView, Context context, List<EmployeeOrder> employeeOrderList, Order order, User user) {
            super(itemView);
            this.context = context;
            this.employeeOrderList = employeeOrderList;
            this.order = order;
            this.user = user;
            int adapterPosition = getAdapterPosition();
            employeeOrder = employeeOrderList.get(adapterPosition);
            initViews(itemView);
        }
        private void getOrder(String userId, GetOrderCallBack callBack){
            HashMap<String, Object> conditions = new HashMap<>();
            conditions.put("orderId", employeeOrder.getOrderId());
            orderService.getOrder(userId, conditions, new GetOrderCallBack() {
                @Override
                public void onSuccess(List<Order> orderList) {
                    Log.i(TAG, String.valueOf(orderList.size()));
                    callBack.onSuccess(orderList);
                }

                @Override
                public void onFailure(String failMessage) {
                    Log.i(TAG, failMessage);
                }
            });
        }
        private void getUsername(String userId, UserCallback callback){
            userService.getUserById(userId, new UserCallback() {
                @Override
                public void onUserResult(User user) {
                    callback.onUserResult(user);
                }
            });
        }
        private void bind(EmployeeOrder employeeOrder){
            if(employeeOrder != null){
                orderId.setText(employeeOrder.getOrderId());
                getUsername(employeeOrder.getUserId(), new UserCallback() {
                    @Override
                    public void onUserResult(User user) {
                        username.setText(user.getUsername());
                        getOrder(user.getUserId(), new GetOrderCallBack() {
                            @Override
                            public void onSuccess(List<Order> orderList) {
                                orderItemSize.setText(orderList.get(1).getOrderItems().size());
                            }

                            @Override
                            public void onFailure(String failMessage) {
                                Log.i(TAG, failMessage);
                            }
                        });
                    }
                });

            }
        }
        private void initViews(View itemView){
            userService = new UserService();
            orderService = new OrderService();
            orderId = itemView.findViewById(R.id.textView7);
            username = itemView.findViewById(R.id.textView8);
            orderItemSize = itemView.findViewById(R.id.textView9);
        }
    }
}
