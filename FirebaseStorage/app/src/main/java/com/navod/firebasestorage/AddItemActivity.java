package com.navod.firebasestorage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.navod.firebasestorage.model.Item;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class AddItemActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private ImageButton imageButton;
    private FirebaseFirestore fireStore;
    private FirebaseStorage storage;
    private Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        fireStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

         imageButton = findViewById(R.id.imageButton);
         imageButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent();
                 intent.setType("image/*");
                 intent.setAction(Intent.ACTION_GET_CONTENT);
                 activityResultLauncher.launch(Intent.createChooser(intent, "Select Image"));
             }
         });
//         Add New Recode
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextName = findViewById(R.id.editTextName);
                EditText editTextDesc = findViewById(R.id.editTextDesc);
                EditText editTextPrice = findViewById(R.id.editTextPrice);

                String name = editTextName.getText().toString();
                String desc = editTextDesc.getText().toString();
                double price = Double.parseDouble(editTextPrice.getText().toString());

                String imageId = UUID.randomUUID().toString();

                Item item = new Item(name, desc, price, imageId);

                ProgressDialog dialog = new ProgressDialog(AddItemActivity.this);
                dialog.setMessage("Adding new Item");
                dialog.setCancelable(false);
                dialog.show();

                fireStore.collection("Items").add(item)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                if(imagePath!=null){
                                    dialog.setMessage("Uploading Image");

                                    StorageReference reference = storage.getReference("item-images")
                                            .child(imageId);
                                    reference.putFile(imagePath)
                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    dialog.dismiss();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialog.dismiss();
                                                    Toast.makeText(AddItemActivity.this,
                                                            e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                                    double progress = (100.0*snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                                    dialog.setMessage("Uploading: "+(int)progress+"%");
                                                }
                                            });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        imagePath = result.getData().getData();
                        Log.i(TAG, "Image Path:"+imagePath.getPath());

                        Picasso.get()
                                .load(imagePath)
                                .resize(200, 200)
                                .centerCrop()
                                .into(imageButton);
//                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
//                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), imagePath);
//                            Log.i(TAG, "source: "+ source);
//                            try{
//                                Bitmap bitmap = ImageDecoder.decodeBitmap(source);
//                                Log.i(TAG, "ImageBitMap: "+bitmap);
//                                imageButton.setImageBitmap(bitmap);
//                            }catch(Exception e){
//                                Log.e(TAG, e.getMessage());
//                            }
//                        }else{
//                            try {
//                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
//                                imageButton.setImageBitmap(bitmap);
//                            }catch (Exception e){
//                                Log.e(TAG, e.getMessage());
//                            }
//                        }

                    }
                }
            }
    );
}