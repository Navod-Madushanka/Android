package com.navod.etradeadmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.navod.etradeadmin.entity.Employee;
import com.navod.etradeadmin.listener.EmployeeDataListener;
import com.navod.etradeadmin.util.SecureApp;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeRegistrationFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeRegistrationFragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText username, email, mobile, password;
    private Employee employee;
    EmployeeRegistration activity;
    private EmployeeDataListener employeeDataListener;

    public EmployeeRegistrationFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployeeRegistrationFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployeeRegistrationFragment1 newInstance(String param1, String param2) {
        EmployeeRegistrationFragment1 fragment = new EmployeeRegistrationFragment1();
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
        return inflater.inflate(R.layout.fragment_employee_registration1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        initViews(fragment);
        getEmployee();
        employeeDataListener = (EmployeeDataListener) getActivity();
        activity.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDetails();
            }
        });
    }
    private void setDetails(){
        employee.setUsername(username.getText().toString());
        employee.setEmail(email.getText().toString());
        employee.setMobile(mobile.getText().toString());
        employee.setPassword(password.getText().toString());

        if(employeeDataListener != null){
            employeeDataListener.onEmployeeDataReceived(employee);
        }
    }
    private void getEmployee(){
        activity = (EmployeeRegistration) getActivity();
        employee = new Employee();
        password.setText(SecureApp.generateRandomPassword(5));
    }
    private void initViews(View fragment){
        username = fragment.findViewById(R.id.editTextText2);
        email = fragment.findViewById(R.id.editTextTextEmailAddress);
        mobile = fragment.findViewById(R.id.editTextPhone);
        password = fragment.findViewById(R.id.editTextText3);
    }
}