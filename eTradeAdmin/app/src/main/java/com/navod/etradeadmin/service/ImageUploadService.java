package com.navod.etradeadmin.service;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.database.Cursor;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.navod.etradeadmin.CallBack.DeleteImageCallback;
import com.navod.etradeadmin.CallBack.ImageFetchCallback;
import com.navod.etradeadmin.CallBack.ImageListCallback;
import com.navod.etradeadmin.CallBack.ImageUploadCallback;
import com.navod.etradeadmin.CallBack.ImageUploadedCallback;
import com.navod.etradeadmin.CallBack.UpdateProductCallback;
import com.navod.etradeadmin.entity.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;


public class ImageUploadService {
    private static final String TAG = ImageUploadService.class.getName();
    FirebaseStorage storage;
    StorageReference storageRef;
    private Context context;
    public ImageUploadService(Context context){
        this.context = context;
    }
    public ImageUploadService(){
    }
    public ImageUploadService(Context context, String location) {
        this.context = context;
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = storage.getReference(location);
    }
    public void uploadProductImages(String productId, List<String> imagePaths, ImageFetchCallback callback) {
        List<String> downloadUrls = new ArrayList<>();
        int totalImageSize = imagePaths.size();
        for (String imagePath : imagePaths) {
            Log.i(TAG, "ImagePath: "+imagePath);
            String imageName = productId + "/" + UUID.randomUUID().toString();
            StorageReference imageRef = storageRef.child(imageName);

            try {
                ContentResolver contentResolver = context.getContentResolver();
                InputStream inputStream = contentResolver.openInputStream(Uri.parse(imagePath));

                if (inputStream != null) {
                    uploadImage(inputStream, imageRef, new ImageUploadCallback() {
                        @Override
                        public void onSuccess(String downloadUrl) {
                            if(downloadUrl != null){
                                downloadUrls.add(downloadUrl);
                                if(downloadUrls.size() == totalImageSize){
                                    Log.i(TAG, downloadUrls.toString());
                                    ProductService productService = new ProductService();
                                    productService.updateProductWithImageDownloadUrls(productId, downloadUrls, new UpdateProductCallback() {
                                        @Override
                                        public void onUpdateDone(boolean state) {
                                            if(state){
                                                callback.onImageFetched(true);
                                            }else{
                                                callback.onImageFetched(false);
                                            }
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            Log.e(TAG, error);
                        }
                    });
                } else {
                    Log.e(TAG, "Failed to open input stream for URI: " + imagePath);
                }
            } catch (FileNotFoundException e) {
                Log.e(TAG, "File not found", e);
            }
        }
    }

    private void uploadImage(InputStream inputStream, StorageReference reference, ImageUploadCallback callback) {
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

    public void deleteImages(StorageReference storageReference, DeleteImageCallback callback){
        storageReference.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference fileRef:listResult.getItems()){
                            fileRef.delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.i(TAG, "Image was deleted: "+fileRef);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e(TAG ,"Images cannot delete"+e.getMessage());
                                        }
                                    });
                        }
                        if(listResult.getItems().isEmpty()){
                            callback.onImageDeleted(true);
                        }else {
                            callback.onImageDeleted(false);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Images not found");
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
