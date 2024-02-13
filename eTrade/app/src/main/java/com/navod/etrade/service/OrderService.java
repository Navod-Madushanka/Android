package com.navod.etrade.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.navod.etrade.callback.GetOrderCallBack;
import com.navod.etrade.callback.OrderAddedCallback;
import com.navod.etrade.callback.UpdateOrderDetailsCallback;
import com.navod.etrade.entity.Order;
import com.navod.etrade.entity.OrderItem;
import com.navod.etrade.model.OrderStatus;
import com.navod.etrade.util.CurrentDate;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private static final String TAG = OrderService.class.getName();
    private Context context;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    public OrderService(){
        this.db = FirebaseFirestore.getInstance();
        this.collectionReference = db.collection("order");
    }
    public OrderService(Context context){
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
        this.collectionReference = db.collection("order");
    }
    public void getOrder(String userDocumentId, Map<String, Object> conditions, GetOrderCallBack callBack){
        Query query = collectionReference.whereEqualTo("customerId", userDocumentId);

        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
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
                            List<Order> orderList = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot:task.getResult()){
                                Order order = snapshot.toObject(Order.class);
                                orderList.add(order);
                            }
                            callBack.onSuccess(orderList);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting Orders: "+e.getMessage());
                        callBack.onFailure("Error getting Orders: "+e.getMessage());
                    }
                });
    }
    public void addOrder(Order order, OrderAddedCallback callback){
        collectionReference
                .add(order)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Map<String, Object> details = new HashMap<>();
                        details.put("orderId", documentReference.getId());
                        details.put("orderDate", CurrentDate.getCurrentDate());
                        details.put("orderStatus", OrderStatus.PENDING);

                        updateOrderDetails(documentReference.getId(), details, new UpdateOrderDetailsCallback() {
                            @Override
                            public void update(boolean success) {
                                if(success){
                                    Log.i(TAG, "Order Added Successfully");
                                }else{
                                    Log.i(TAG, "Updating failed");
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.OnFailure("Inserting failed");
                    }
                });
    }
    public void updateOrderDetails(String documentId, Map<String, Object> values, UpdateOrderDetailsCallback callback){
        collectionReference.document(documentId)
                .update(values)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.update(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.update(false);
                    }
                });
    }
}
