package com.navod.broadcastreceiver2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn1);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.example.javainstitute.CUSTOM_INTENT");
                broadcastManager.sendBroadcast(intent);
            }
        });
    }
}