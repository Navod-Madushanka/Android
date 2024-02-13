package com.navod.etrade.service;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.navod.etrade.callback.CheckImageExistenceCallback;
import com.navod.etrade.callback.ImageDeleteCallback;
import com.navod.etrade.callback.ImageUploadCallback;
import com.navod.etrade.callback.ImageUrlUpdateCallback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageService {
    private static final String TAG = ImageService.class.getName();
    private FirebaseStorage storage;
    private StorageReference reference;
    private Context context;
    private InputStream inputStream;
    public ImageService(Context context){
        this.context = context;
        this.storage = FirebaseStorage.getInstance();
        this.reference = storage.getReference().child("user");
    }
    public void uploadImage(String documentId, String image){
        String imageName = documentId+"/profile_image.jpg";
        StorageReference imageRef = reference.child(imageName);
        try{
            ContentResolver contentResolver = context.getContentResolver();
            inputStream = contentResolver.openInputStream(Uri.parse(image));

            if (inputStream != null){
                uploadImageProcess(inputStream, imageRef, documentId, new ImageUploadCallback() {
                    @Override
                    public void onSuccess(String downloadUrl) {
                        UserService userService = new UserService();
                        userService.updateImageUrl(documentId, downloadUrl, new ImageUrlUpdateCallback() {
                            @Override
                            public void updateUrl(boolean success) {
                                if(success){
                                    Log.i(TAG, "Image uploading process Complete");
                                }else {
                                    Log.i(TAG, "Image uploading process not complete");
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e(TAG, "Error Uploading the image " + imageName);
                    }
                });
            }else{
                Log.e(TAG, "Failed to open input stream for URI: " + imageName);
            }
        }catch (FileNotFoundException e){
            Log.e(TAG, "File not found", e);
        }
    }
    private void uploadImageProcess(InputStream inputStream, StorageReference reference, String documentId, ImageUploadCallback callback){
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/*")
                .build();
        reference.putStream(inputStream, metadata)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.i(TAG, "Image Upload Successful");
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl = uri.toString();
                                Log.i(TAG, "Image Download Url: "+downloadUrl);
                                callback.onSuccess(downloadUrl);
                                closeInputStream(inputStream);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Image Upload failed: "+e.getMessage());
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Upload Image Failed: "+e.getMessage());
                        closeInputStream(inputStream);
                    }
                });
    }

    public void checkImageExistence(String documentId, CheckImageExistenceCallback callback) {
        String imageName = documentId + "/profile_image.jpg";
        StorageReference imageRef = reference.child(imageName);

        imageRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {
                            callback.onImageExistence(true);
                            return;
                        }
                        callback.onImageExistence(false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error checking image existence in Firebase Storage: " + e.getMessage());
                        callback.onImageExistence(false);
                    }
                });
    }
    public void deleteImage(String documentId, ImageDeleteCallback callback){
        String imageName = documentId+"/profile_image.jpg";
        StorageReference imageRef = reference.child(imageName);
        imageRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i(TAG, "Image Deleted Successfully");
                        callback.onDelete(true, documentId);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error deleting image: " + e.getMessage());
                        callback.onDelete(false, null);
                    }
                });
    }
    private void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing InputStream", e);
            }
        }
    }

}
