package com.navod.etradedelivery.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.navod.etradedelivery.callback.EmployeeAddedCallback;
import com.navod.etradedelivery.callback.EmployeeListCallback;
import com.navod.etradedelivery.callback.EmployeeUpdateCallback;
import com.navod.etradedelivery.entity.Employee;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {
    private static final String TAG = EmployeeService.class.getName();
    private FirebaseFirestore db;
    private Context context;
    private CollectionReference collectionReference;
    public EmployeeService(){
        this.db = FirebaseFirestore.getInstance();
        this.collectionReference = db.collection("employee");
    }
    public EmployeeService(Context context){
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
        this.collectionReference = db.collection("employee");
    }
    public void getEmployees(Map<String, Object> conditions, EmployeeListCallback callback){
        Query query = collectionReference;
        for(Map.Entry<String, Object> entry:conditions.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof List) {
                query = query.whereIn(key, (List<?>) value);
            } else {
                query = query.whereEqualTo(key, value);
            }
        }

            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                List<Employee> employeeList = new ArrayList<>();
                                for (QueryDocumentSnapshot snapshot : task.getResult()){
                                    Employee employee = snapshot.toObject(Employee.class);
                                    employeeList.add(employee);
                                }
                                callback.onSuccess(employeeList);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onFailure("Error getting employees: "+e.getMessage());
                        }
                    });
    }
    public void updateEmployee(String documentId, Map<String, Object> fields, EmployeeUpdateCallback callback){
        collectionReference.document(documentId)
                .update(fields)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onSuccess(false);
                    }
                });
    }
}
