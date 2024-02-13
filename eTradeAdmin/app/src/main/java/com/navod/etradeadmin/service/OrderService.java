package com.navod.etradeadmin.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.navod.etradeadmin.CallBack.GetOrderCallBack;
import com.navod.etradeadmin.CallBack.UpdateOrderDetailsCallback;
import com.navod.etradeadmin.entity.Order;
import com.navod.etradeadmin.models.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {
    private static final String TAG = OrderService.class.getName();
    private FirebaseFirestore db;
    private Context context;
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
    public void updateOrder(String documentId, Map<String, Object> fields, UpdateOrderDetailsCallback callback){
        collectionReference.document(documentId)
                .update(fields)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.update(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "Failed to update order: "+e.getMessage());
                        callback.update(false);
                    }
                });
    }
    public void getOrders(Map<String, List<OrderStatus>> conditions, GetOrderCallBack callBack){
        Query query = collectionReference;
        for(Map.Entry<String, List<OrderStatus>> entry : conditions.entrySet()){
            String key = entry.getKey();;
            Object value = entry.getValue();

            if (value instanceof List) {
                query = query.whereIn(key, (List<?>) value);
            } else {
                query = query.whereEqualTo(key, value);
            }

            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                List<Order> orderList = new ArrayList<>();
                                for(QueryDocumentSnapshot snapshot:task.getResult()){
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
    }
}
