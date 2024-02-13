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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.navod.etrade.callback.AddToCartCallback;
import com.navod.etrade.callback.DeleteCartItemCallback;
import com.navod.etrade.callback.GetCartItemCallback;
import com.navod.etrade.entity.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    public static final String TAG = CartItem.class.getName();
    private FirebaseFirestore db;
    private Context context;
    public CartService(){
        db = FirebaseFirestore.getInstance();
    }
    public CartService(Context context){
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }
    public void DeleteCartItem(String userDocumentId, List<CartItem> cartItems, DeleteCartItemCallback callback){
        List<String> cartItemIds = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            cartItemIds.add(cartItem.getId());
        }

        db.collection("user")
                .document(userDocumentId)
                .collection("cart")
                .whereIn("id", cartItemIds)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot result = task.getResult();
                            if (result != null && !result.isEmpty()) {
                                for (QueryDocumentSnapshot snapshot : result) {
                                    snapshot.getReference().delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    callback.onSuccess("CartItems deleted successfully");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e(TAG, "Error deleting cart item: " + e.getMessage());
                                                    callback.onFailure("Error deleting cart item: " + e.getMessage());
                                                }
                                            });
                                }
                            } else {
                                callback.onFailure("No cart items found with the given IDs");
                            }
                        } else {
                            Log.e(TAG, "Error getting cart items: " + task.getException());
                            callback.onFailure("Error getting cart items: " + task.getException().getMessage());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.getMessage());
                    }
                });
    }
    public void getCartByUserDocumentId(String documentId, GetCartItemCallback callback){
        db.collection("user").document(documentId).collection("cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<CartItem> cartItems = new ArrayList<>();
                            QuerySnapshot querySnapshot = task.getResult();

                            if (querySnapshot != null) {
                                for (QueryDocumentSnapshot snapshot : querySnapshot) {
                                    CartItem cartItem = snapshot.toObject(CartItem.class);
                                    cartItems.add(cartItem);
                                }
                                callback.onSuccess(cartItems);
                            } else {
                                Log.e(TAG, "Task result is null while retrieving cart items.");
                                callback.onFailure("Task result is null while retrieving cart items.");
                            }
                        } else {
                            Log.e(TAG, "Error getting cart items: " + task.getException());
                            callback.onFailure("Error getting cart items: " + task.getException().getMessage());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e.getMessage());
                    }
                });
    }

    public void addCartListToCart(String userDocumentId, CartItem cartItem, AddToCartCallback callback){
        Log.i(TAG, userDocumentId);
        CollectionReference reference;
        reference = db.collection("user").document(userDocumentId).collection("cart");
        reference.add(cartItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        callback.onSuccess("Item added to the cart successfully");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure("Error Adding to cart: "+e.getMessage());
                    }
                });
    }
}
