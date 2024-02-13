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

import com.navod.etradeadmin.CallBack.EmployeeOrderAddedCallback;
import com.navod.etradeadmin.HomeActivity;
import com.navod.etradeadmin.R;
import com.navod.etradeadmin.entity.Employee;
import com.navod.etradeadmin.entity.EmployeeOrder;
import com.navod.etradeadmin.entity.Order;
import com.navod.etradeadmin.service.EmployeeOrderService;

import java.util.List;

public class EmployeeSelectAdapter extends RecyclerView.Adapter<EmployeeSelectAdapter.ViewHolder>{
    private Context context;
    private List<Employee> employeeList;
    private Order order;
    public EmployeeSelectAdapter(Context context, List<Employee> employeeList, Order order){
        this.context = context;
        this.employeeList = employeeList;
        this.order = order;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.employee_select_layout, parent, false);
        return new ViewHolder(view, context, employeeList, order);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.bind(employee);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private List<Employee> employeeList;
        private Order order;
        private TextView username, mobile, email;
        private EmployeeOrderService employeeOrderService;
        private static final String TAG = ViewHolder.class.getName();
        public ViewHolder(@NonNull View itemView, Context context, List<Employee> employeeList, Order order) {
            super(itemView);
            this.context = context;
            this.employeeList = employeeList;
            this.order = order;
            initViews(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createEmployeeOrders();
                }
            });
        }
        private void goToHome(){
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        }
        private void createEmployeeOrders(){
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                EmployeeOrder employeeOrder = new EmployeeOrder();
                employeeOrder.setUserId(order.getCustomerId());
                employeeOrder.setOrderId(order.getOrderId());

                Employee employee = employeeList.get(getAdapterPosition());

                if (employee != null) {
                    employeeOrderService = new EmployeeOrderService(employee.getEmployeeId());
                    employeeOrderService.addEmployeeOrder(employeeOrder, new EmployeeOrderAddedCallback() {
                        @Override
                        public void onSuccess(Boolean success) {
                            if (success) {
                                goToHome();
                            }
                        }
                    });
                } else {
                    Log.e(TAG, "Employee is null at position: " + getAdapterPosition());
                }
            } else {
                Log.e(TAG, "Invalid adapter position");
            }
        }
        private void bind(Employee employee){
            if(employee != null){
                username.setText(employee.getUsername());
                mobile.setText(employee.getMobile());
                email.setText(employee.getEmail());
            }
        }
        private void initViews(View itemView){
            username = itemView.findViewById(R.id.textView42);
            mobile = itemView.findViewById(R.id.textView43);
            email = itemView.findViewById(R.id.textView44);
        }
    }
}
