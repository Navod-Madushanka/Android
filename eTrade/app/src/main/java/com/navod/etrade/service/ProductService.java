package com.navod.etrade.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.navod.etrade.callback.ProductCallback;
import com.navod.etrade.callback.ProductFetchedCallback;
import com.navod.etrade.entity.Product;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductService {
    public static final String TAG = ProductService.class.getName();
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private Context context;
    public ProductService(){
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }
    public ProductService(Context context){
        this.context = context;
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }
    public void getProductByDocumentId(String documentId, ProductCallback callback){
        db.collection("product").document(documentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot result = task.getResult();
                            Product product = result.toObject(Product.class);
                            callback.onGetProduct(product);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "Error getting product");
                        callback.onError(e.getMessage());
                    }
                });
    }
    public void getAllProduct(List<Pair<String, Object>> conditions, ProductFetchedCallback callback){
        List<Product> productList = new ArrayList<>();
        Query query = db.collection("product");

        for (Pair<String, Object> condition : conditions) {
            String key = condition.getKey();
            Object value = condition.getValue();

            if (value instanceof String) {
                query = query.whereEqualTo(key, (String) value);
            } else if (value instanceof Boolean) {
                query = query.whereEqualTo(key, (Boolean) value);
            } else if(value instanceof Date){
                query = query.whereEqualTo(key, (Date) value);
            }
        }
        query.orderBy("addedAt", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e(TAG, "Error getting product updates: " + error.getMessage());
                            return;
                        }

                        if (snapshot != null) {
                            productList.clear(); // Clear existing list to avoid duplicates

                            for (QueryDocumentSnapshot doc : snapshot) {
                                String documentId = doc.getId();
                                Product product = doc.toObject(Product.class);
                                product.setId(documentId);
                                productList.add(product);
                            }

                            callback.onProductFetched(productList);
                        }
                    }
                });
    }
}
