package com.navod.etradeadmin.service;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.navod.etradeadmin.CallBack.EmployeeOrderAddedCallback;
import com.navod.etradeadmin.entity.EmployeeOrder;
import com.navod.etradeadmin.entity.Order;

public class EmployeeOrderService {
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    public EmployeeOrderService(String employeeId){
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("employee").document(employeeId).collection("orders");
    }
    public void addEmployeeOrder(EmployeeOrder employeeOrder, EmployeeOrderAddedCallback callback){
        collectionReference
                .add(employeeOrder)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
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
