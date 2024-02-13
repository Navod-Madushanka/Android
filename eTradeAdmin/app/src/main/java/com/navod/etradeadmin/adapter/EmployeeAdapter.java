package com.navod.etradeadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.navod.etradeadmin.R;
import com.navod.etradeadmin.entity.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private Context context;
    private List<Employee> employeeList;
    public EmployeeAdapter(Context context, List<Employee> employeeList){
        this.context = context;
        this.employeeList = employeeList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.employee_select_layout, parent, false);
        return new ViewHolder(inflate, context, employeeList);
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
        private TextView username, mobile, email;
        public ViewHolder(@NonNull View itemView, Context context, List<Employee> employeeList) {
            super(itemView);
            this.context = context;
            this.employeeList = employeeList;
            initViews(itemView);
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
