package com.navod.etradeadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.navod.etradeadmin.CallBack.UpdateOrderDetailsCallback;
import com.navod.etradeadmin.OrderItemViewActivity;
import com.navod.etradeadmin.R;
import com.navod.etradeadmin.entity.Order;
import com.navod.etradeadmin.entity.OrderItem;
import com.navod.etradeadmin.models.OrderStatus;
import com.navod.etradeadmin.service.OrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder> {
    private List<Order> orderList;
    private Context context;
    public OrderRecyclerViewAdapter(Context context, List<Order> orderList){
        this.context = context;
        this.orderList = orderList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(com.navod.etradeadmin.R.layout.order_layout, parent, false);
        return new ViewHolder(inflate, context, orderList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private List<Order> orderList;
        private TextView orderId, date, state;
        private OrderService orderService;
        public ViewHolder(@NonNull View itemView, Context context, List<Order> orderList) {
            super(itemView);
            this.context = context;
            this.orderList = orderList;
            initViews(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    Order order = orderList.get(adapterPosition);

                    Map<String, Object> fields = new HashMap<>();
                    fields.put("orderStatus", OrderStatus.PROCESSING);
                    orderService.updateOrder(order.getOrderId(), fields, new UpdateOrderDetailsCallback() {
                        @Override
                        public void update(boolean success) {
                            System.out.println("Order Updated");
                            Intent intent = new Intent(context, OrderItemViewActivity.class);
                            intent.putExtra("order", order);
                            itemView.getContext().startActivity(intent);
                        }
                    });
                }
            });
        }
        private void bind(Order order){
            if(order != null){
                orderId.setText(order.getOrderId());
                date.setText(order.getOrderDate().toString());
                state.setText(order.getOrderStatus().toString());
            }
        }
        private void initViews(View itemView){
            orderId = itemView.findViewById(R.id.textView30);
            date = itemView.findViewById(R.id.textView31);
            state = itemView.findViewById(R.id.textView32);
            orderService = new OrderService();
        }
    }
}
