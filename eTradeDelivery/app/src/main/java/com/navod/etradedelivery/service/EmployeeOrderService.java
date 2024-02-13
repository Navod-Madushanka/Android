package com.navod.etradedelivery.service;

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
import com.navod.etradedelivery.callback.EmployeeOrderAddedCallback;
import com.navod.etradedelivery.callback.GetEmployeeOrdersCallback;
import com.navod.etradedelivery.entity.EmployeeOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeOrderService {
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    public EmployeeOrderService(String employeeId){
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("employee").document(employeeId).collection("orders");
    }
    public void getEmployeeOrders(Map<String, Object> conditions, GetEmployeeOrdersCallback callback){
        Query query = collectionReference;
        for(Map.Entry<String, Object> entry:conditions.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value instanceof List){
                query = query.whereIn(key, (List<?>) value);
            }else{
                query = query.whereEqualTo(key, value);
            }
        }
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<EmployeeOrder> employeeOrders = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot:task.getResult()){
                                EmployeeOrder employeeOrder = snapshot.toObject(EmployeeOrder.class);
                                employeeOrders.add(employeeOrder);
                            }
                            callback.onSuccess(employeeOrders);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure("Error getting EmployeeOrders: "+e.getMessage());
                    }
                });
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
