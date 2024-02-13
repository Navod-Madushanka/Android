package com.navod.etrade.service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.navod.etrade.callback.CheckImageExistenceCallback;
import com.navod.etrade.callback.ImageDeleteCallback;
import com.navod.etrade.callback.ImageUrlUpdateCallback;
import com.navod.etrade.callback.UserCallback;
import com.navod.etrade.callback.UserExistenceCallback;
import com.navod.etrade.callback.UserSignInCallback;
import com.navod.etrade.callback.UserUpdateCallback;
import com.navod.etrade.entity.User;
import com.navod.etrade.util.CurrentDate;
import com.navod.etrade.util.SecureApp;
import com.navod.etrade.util.SharedPreferencesManager;

import java.util.UUID;

public class UserService {
    private static final String TAG = UserService.class.getName();
    private final FirebaseFirestore db;
    private Context context;
    public UserService(){
        this.db = FirebaseFirestore.getInstance();
    }

    public UserService(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
    }
    public void updateImageTemplate(String documentId, String image){
        ImageService imageService = new ImageService(context);
        imageService.checkImageExistence(documentId, new CheckImageExistenceCallback() {
            @Override
            public void onImageExistence(boolean exist) {
                if(exist){
                    imageService.deleteImage(documentId, new ImageDeleteCallback() {
                        @Override
                        public void onDelete(boolean success, String documentId) {
                            if(success){
                                imageService.uploadImage(documentId, image);
                            }else{
                                Log.e(TAG, "Error deleting image");
                            }
                        }
                    });
                }else{
                    imageService.uploadImage(documentId, image);
                }
            }
        });
    }
    public void updateUser(String documentId, User updatedUser, UserUpdateCallback callback){
        db.collection("user").document(documentId)
                .update(
                        "userId", documentId,
                        "username", updatedUser.getUsername(),
                        "mobileNumber", updatedUser.getMobileNumber(),
                        "address", updatedUser.getAddress()
                )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            callback.onSuccess("User Updated successfully");
                        }else{
                            callback.onFailure("Error updating user");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error in UpdateUser: "+e.getMessage());
                    }
                });
    }
    public void updateImageUrl(String documentId , String downloadUrl, ImageUrlUpdateCallback callback){
        db.collection("user").document(documentId)
                .update(
                        "profilePictureUrl", downloadUrl
                )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            callback.updateUrl(true);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error updating image URL", e);
                        callback.updateUrl(false);
                    }
                });
    }
    public void logInUser(User user, UserExistenceCallback callback) {
        if (validateUser(user, false)) {
            getUserByEmailAndPassword(user.getEmail(), user.getPassword(), (userExists, userId) -> {
                if (userExists) {
                    Log.i(TAG, "User can Log in");
                    getUserById(userId, new UserCallback() {
                        @Override
                        public void onUserResult(User user) {
                            Log.i(TAG, "User id: "+userId);
                            SharedPreferencesManager.saveUserId(context, userId);
                        }
                    });
                    callback.onResult(true, userId);
                }else{
                    Log.i(TAG, "Log In Failed");
                    callback.onResult(false, userId);
                }
            });
        }
    }

    public void saveUser(User user, UserSignInCallback callback) {
        if (validateUser(user, true)) {
            getUserByEmailAndPassword(user.getEmail(), user.getPassword(), (userExists, userId) -> {
                if (!userExists && userId == null) {
                    User newUser = setMissingData(user);
                    db.collection("user").add(newUser)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.i(TAG, "User is saved");
                                    callback.success(true);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, "Error saving user: " + e.getMessage());
                                    callback.success(false);
                                }
                            });
                }else{
                    Log.i(TAG, "User already Exist");
                }
            });
        }
    }

    public void getUserById(String userId, final UserCallback callback){
        db.collection("user").document(userId)
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

    private User setMissingData(User user){
        UUID userID = UUID.randomUUID();
        user.setUserId(userID.toString());
        user.setCreatedAt(CurrentDate.getCurrentDate());
        user.setStatus(true);
        return user;
    }

    private void getUserByEmailAndPassword(String email, String password, UserExistenceCallback callback) {
        db.collection("user").whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        if(result != null && !result.isEmpty()){
                            String userID = result.getDocuments().get(0).getId();
                            callback.onResult(true, userID);
                        }else{
                            callback.onResult(false, null);
                        }
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Error checking user existence: " + e.getMessage()));
    }

    private boolean validateUser(User user, boolean isSignUp) {
        if (isSignUp) {
            if (user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
                return false;
            }
        }

        if (user.getEmail().isEmpty() || user.getPassword().isEmpty() || !SecureApp.isValidEmail(user.getEmail())) {
            return false;
        }

        return true;
    }
}
