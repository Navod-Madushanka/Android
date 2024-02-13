package com.navod.etradeadmin.service;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.navod.etradeadmin.CallBack.AddProductCallback;
import com.navod.etradeadmin.CallBack.DeleteImageCallback;
import com.navod.etradeadmin.CallBack.GetProductCallback;
import com.navod.etradeadmin.CallBack.ImageFetchCallback;
import com.navod.etradeadmin.CallBack.ImageListCallback;
import com.navod.etradeadmin.CallBack.ImageUploadedCallback;
import com.navod.etradeadmin.CallBack.ModelFetchCallback;
import com.navod.etradeadmin.CallBack.ProductCallback;
import com.navod.etradeadmin.CallBack.ProductFetchedCallback;
import com.navod.etradeadmin.CallBack.UpdateProductCallback;
import com.navod.etradeadmin.entity.Product;
import com.navod.etradeadmin.util.CurrentDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductService {
    private static final String TAG = ProductService.class.getName();
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private Context context;
    public ProductService(){
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }
    public ProductService(Context context){
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        this.context = context;
    }
    public void deleteProduct(String productId, UpdateProductCallback callback){
        db.collection("product").document(productId)
        .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ImageUploadService imageUploadService = new ImageUploadService(context);
                        StorageReference reference = storage.getReference("product/" + productId + "/");
                        imageUploadService.deleteImages(reference, new DeleteImageCallback() {
                            @Override
                            public void onImageDeleted(boolean success) {
                                if(success){
                                    Log.i(TAG, "in the success");
                                    callback.onUpdateDone(true);
                                }else{
                                    Log.i(TAG, "Not success");
                                    callback.onUpdateDone(false);
                                }
                            }
                        });

                        Log.i(TAG, "Product deletion successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error deleting product", e);
                    }
                });
    }

    public void updateImages(String productId, List<String> imagesDownloadUrl, ImageUploadedCallback callback){
        db.collection("product").document(productId)
                .update("imageUriList", imagesDownloadUrl)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            callback.onSuccess("Images were updated");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.OnFailure("Image Upload Failed. error: "+e.getMessage());
                    }
                });
    }
    public void updateProduct(Product updatedProduct){
        getProductById(updatedProduct.getId(), new GetProductCallback() {
            @Override
            public void getProduct(Product product, String documentId) {
                db.collection("product").document(documentId)
                        .update(
                            "description", updatedProduct.getDescription(),
                                "discount", updatedProduct.getDiscount(),
                                "price", updatedProduct.getPrice(),
                                "updatedAt",CurrentDate.getCurrentDate(),
                                "quantity", updatedProduct.getQuantity()
                        ).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                ImageUploadService imageUploadService = new ImageUploadService(context);
                                StorageReference reference = storage.getReference("product/" + documentId + "/");
                                imageUploadService.deleteImages(reference, new DeleteImageCallback() {
                                    @Override
                                    public void onImageDeleted(boolean success) {
                                        if(success){
                                            Log.i(TAG, "in the success");
                                            Log.i(TAG, "Document id: "+documentId);
                                            Log.i(TAG, "Images: "+product.getImages().toString());
                                            ImageUploadService uploadService = new ImageUploadService(context, "product");
                                            uploadService.uploadProductImages(documentId, product.getImages(), new ImageFetchCallback() {
                                                @Override
                                                public void onImageFetched(boolean success) {
                                                    if(success){
                                                        Log.i(TAG, "Images Uploaded successfully");
                                                    }else{
                                                        Log.e(TAG, "Image Upload failed");
                                                    }
                                                }
                                            });
                                        }else{
                                            Log.i(TAG, "Not success");
                                        }
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });
    }
    public void getProductByDocumentId(String documentId, ProductCallback callback) {
        db.collection("product").document(documentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
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

    public void getProductById(String id, GetProductCallback callback){
        db.collection("product")
                .whereEqualTo("id", id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            DocumentSnapshot snapshot = queryDocumentSnapshots.getDocuments().get(0);
                            String documentId = queryDocumentSnapshots.getDocuments().get(0).getId();
                            Product product = snapshot.toObject(Product.class);
                            callback.getProduct(product, documentId);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting data "+e.getMessage());
                    }
                });
    }


    public void getAllProductsByModel(String model, ProductFetchedCallback callback) {
        List<Product> productList = new ArrayList<>();
        db.collection("product")
                .orderBy("addedAt", Query.Direction.DESCENDING)
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

                                if (product.getModel().equalsIgnoreCase(model)) {
                                    productList.add(product);
                                }
                            }

                            callback.onProductsFetched(productList);
                        }
                    }
                });
    }

    public void getAllProductModels(ModelFetchCallback callback){
        List<String> models = new ArrayList<>();
        db.collection("product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot snapshot : task.getResult()){
                                String model = snapshot.getString("model");
                                if(model != null){
                                    models.add(model);
                                }
                            }
                            callback.onModelsFetched(models);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onModelsFetched(Collections.emptyList());
                        Log.e(TAG, "Error getting the product");
                    }
                });
    }

    public void getAllProducts(ProductFetchedCallback callback) {
        List<Product> productList = new ArrayList<>();
        db.collection("product")
                .orderBy("addedAt", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            final int[] productsProcessed = {0};
                            int totalProducts = task.getResult().size();

                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                String documentId = snapshot.getId();
                                Product product = snapshot.toObject(Product.class);
                                productList.add(product);

                                productsProcessed[0]++;
                                if (productsProcessed[0] == totalProducts) {
                                    callback.onProductsFetched(productList);
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
    public void addProduct(Product product, AddProductCallback callback) {
        Log.i(TAG, "in Add Product");
        if (validateProduct(product)) {
            Product newProduct = setNewProductData(product);
            db.collection("product").add(newProduct)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.i(TAG, "On Success");
                            String productId = documentReference.getId();
                            Log.i(TAG, productId);
                            List<String> images = newProduct.getImages();
                            if (images != null && !images.isEmpty()) {
                                ImageUploadService imageUploadService = new ImageUploadService(context, "product");
                                imageUploadService.uploadProductImages(productId, images, new ImageFetchCallback() {
                                    @Override
                                    public void onImageFetched(boolean success) {
                                        if (success) {
                                            Log.i(TAG, "Images Uploaded successfully");
                                            callback.onSuccess(productId);
                                        } else {
                                            Log.e(TAG, "Image Upload failed");
                                            callback.onFailure("Image Upload failed");
                                        }
                                    }
                                });
                            } else {
                                Log.i(TAG, "No images to upload.");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error adding product", e);
                            callback.onFailure("Error adding product "+e.getMessage());
                        }
                    });
        }
    }
    public void updateProductWithImageDownloadUrls(String productId, List<String> downloadUrlList, UpdateProductCallback callback) {
        Log.i(TAG, "Update Product With Image Download Urls method");

        db.collection("product").document(productId)
                .update("imageUriList", downloadUrlList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i(TAG, "Product document updated with new download URLs");
                        callback.onUpdateDone(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error updating product document", e);
                        callback.onUpdateDone(false);
                    }
                });
    }
    private boolean validateProduct(Product product){
        if(product.getBrand().isEmpty() || product.getPrice() == 0 || product.getQuantity() == 0){
            return false;
        } else if (product.getImages().isEmpty()) {
            return false;
        }
        return true;
    }
    private Product setNewProductData(Product product){
        product.setId(UUID.randomUUID().toString());
        product.setAddedAt(CurrentDate.getCurrentDate());
        product.setStatus(true);
        return product;
    }
}
