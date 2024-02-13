package com.navod.etrade.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.navod.etrade.callback.ProductFetchedCallback;
import com.navod.etrade.entity.Product;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchProductService {
    private FirebaseFirestore db;
    private Context context;
    private CollectionReference collectionReference;
    private static final String TAG = SearchProductService.class.getName();
    public SearchProductService(){
        this.db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("product");
    }
    public SearchProductService(Context context){
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("product");
    }

    public void getProductByModelOrBrand(String searchQuery, ProductFetchedCallback callback){
        List<Product> productList = new ArrayList<>();
        Query query = collectionReference.where(Filter.or(
                        Filter.equalTo("model", searchQuery),
                        Filter.equalTo("brand", searchQuery)
                ));
        query
                .orderBy("addedAt",Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            final int[] productsProcessed = {0};
                            int totalProducts = task.getResult().size();

                            for (QueryDocumentSnapshot snapshot:task.getResult()){
                                String documentId = snapshot.getId();
                                Product product = snapshot.toObject(Product.class);
                                product.setId(documentId);
                                productList.add(product);

                                productsProcessed[0]++;
                                if(productsProcessed[0] == totalProducts){
                                    callback.onProductFetched(productList);
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Getting product is unsuccessful " + e.getMessage());
                    }
                });
    }

    public void getProductByListConditions(List<Pair<String, Object>> conditions, ProductFetchedCallback callback){
        List<Product> productList = new ArrayList<>();
        Log.i(TAG, conditions.toString());
        Query query = collectionReference;

        for (Pair<String, Object> condition : conditions) {
            String key = condition.getKey();
            Object value = condition.getValue();

            if (value instanceof String) {
                query = query.whereEqualTo(key, (String) value);
            } else if (value instanceof Boolean) {
                query = query.whereEqualTo(key, (Boolean) value);
            } else if(value instanceof Date){
                query = query.whereEqualTo(key, (Date) value);
            }else if(value instanceof Double){
                query = query.whereEqualTo(key, (Double) value);
            }else if (value == null) {
                query = query.whereEqualTo(key, FieldValue.serverTimestamp());
            }
        }
        query
                .orderBy("addedAt", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            final int[] productsProcessed = {0};
                            int totalProducts = task.getResult().size();
                            Log.i(TAG, String.valueOf(totalProducts));

                            for (QueryDocumentSnapshot snapshot : task.getResult()){
                                String documentId = snapshot.getId();
                                Product product = snapshot.toObject(Product.class);
                                product.setId(documentId);
                                productList.add(product);

                                productsProcessed[0]++;
                                if(productsProcessed[0] == totalProducts){
                                    Log.i(TAG, productList.toString());
                                    callback.onProductFetched(productList);
                                }
                            }
                        }else{

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Getting product is unsuccessful " + e.getMessage());
                    }
                });
    }
}
