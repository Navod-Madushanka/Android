package com.navod.etradeadmin.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.squareup.picasso.Picasso;

public class ImageSelector {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri selectedImageUri;

    public ImageSelector(){

    }
    public ImageSelector(ActivityResultCaller activityResultCaller, ImageView imageView) {
        activityResultLauncher = activityResultCaller.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleImageSelectionResult(result, imageView)
        );
    }

    public void launchImageSelection() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent);
    }

    private void handleImageSelectionResult(ActivityResult result, ImageView imageView) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            selectedImageUri = result.getData().getData();
            if (selectedImageUri != null) {
                loadImageUriIntoImageView(selectedImageUri, imageView);
            }
        }
    }

    public void loadImageUriIntoImageView(Uri imageUri, ImageView imageView) {
        Picasso.get()
                .load(imageUri)
                .into(imageView);
    }

    public Uri getSelectedImageUri() {
        return selectedImageUri;
    }
}
