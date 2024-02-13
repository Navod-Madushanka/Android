package com.navod.app51;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto52();
            }
        });
    }
    private  void goto52(){
        Intent intent = new Intent();
        intent.setClassName("com.navod.app52","com.navod.app52.MainActivity");
        startActivity(intent);
    }

    private void gotoHome(){
//        Intent intent = new Intent(MainActivity.this, Home.class);
        Intent intent = new Intent();
        intent.setClassName("com.navod.app51","com.navod.app51.Home");
        startActivity(intent);
    }
    private void sendSMS(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("smstp:0772967997"));
        startActivity(intent);
    }
    private void openCamera(){
//        Intent intent = new Intent("android.media.action.STILL_IMAGE_CAMERA");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }
}