package com.navod.etradeadmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.navod.etradeadmin.CallBack.EmployeeListCallback;
import com.navod.etradeadmin.CallBack.GetUserListCallback;
import com.navod.etradeadmin.adapter.EmployeeAdapter;
import com.navod.etradeadmin.adapter.UserViewAdapter;
import com.navod.etradeadmin.entity.Employee;
import com.navod.etradeadmin.entity.User;
import com.navod.etradeadmin.service.EmployeeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EmployeeService employeeService;
    private RecyclerView recyclerView;
    private EditText searchTxt;
    private static final String TAG = DeliveryFragment.class.getName();

    public DeliveryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeliveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeliveryFragment newInstance(String param1, String param2) {
        DeliveryFragment fragment = new DeliveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        initViews(fragment);
        searchEmployee();
    }

    private void searchEmployee(){
        String string = searchTxt.getText().toString();
        if(string.isEmpty()){
            getEmployeeList(Collections.emptyMap(), new EmployeeListCallback() {
                @Override
                public void onSuccess(List<Employee> employeeList) {
                    setRecyclerView(employeeList);
                }

                @Override
                public void onFailure(String error) {
                    Log.i(TAG, error);
                }
            });
        }else{
            HashMap<String, Object> conditions = new HashMap<>();
            conditions.put("username", string);
            getEmployeeList(conditions, new EmployeeListCallback() {
                @Override
                public void onSuccess(List<Employee> employeeList) {
                    setRecyclerView(employeeList);
                }

                @Override
                public void onFailure(String error) {
                    Log.i(TAG, error);
                }
            });
        }
    }
    private void setRecyclerView(List<Employee> employeeList){
        recyclerView.removeAllViews();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(getContext(), employeeList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(employeeAdapter);
    }

    private void getEmployeeList(Map<String, Object> conditions, EmployeeListCallback callback){
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
    private void initViews(View fragment){
        employeeService = new EmployeeService();
        recyclerView = fragment.findViewById(R.id.employeeRecyclerView);
        searchTxt = fragment.findViewById(R.id.txtSearch);
    }
}