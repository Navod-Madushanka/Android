package com.navod.etradedelivery.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.navod.etradedelivery.callback.GetUserListCallback;
import com.navod.etradedelivery.callback.UserCallback;
import com.navod.etradedelivery.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserService {
    private static final String TAG = UserService.class.getName();
    private final FirebaseFirestore db;
    private Context context;
    private CollectionReference collectionReference;
    public UserService(){
        this.db = FirebaseFirestore.getInstance();
        this.collectionReference = db.collection("user");
    }
    public UserService(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
        this.collectionReference = db.collection("user");
    }
    public void getUserList(Map<String, Object> conditions, GetUserListCallback callback){
        Log.i(TAG, collectionReference.toString());
        Query query = collectionReference;
        for(Map.Entry<String, Object> entry : conditions.entrySet()) {
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
                                List<User> userList = new ArrayList<>();
                                for(QueryDocumentSnapshot snapshot : task.getResult()){
                                    User user = snapshot.toObject(User.class);
                                    userList.add(user);
                                }
                                Log.i(TAG, "if Task is success");
                                Log.i(TAG, userList.toString());
                                callback.onSuccess(userList);
                            }else{
                                Log.i(TAG, "if Task is notSuccess");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i(TAG, "Error getting User");
                            callback.onFailure("Error getting users: "+e.getMessage());
                        }
                    });
    }

    public void getUserById(String userId, final UserCallback callback){
        collectionReference.document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot result = task.getResult();
                            if(result.exists()){
                                User user = result.toObject(User.class);
                                callback.onUserResult(user);
                            }else{
                                callback.onUserResult(null);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting user by ID: " + e.getMessage());
                        callback.onUserResult(null);
                    }
                });
    }
}
