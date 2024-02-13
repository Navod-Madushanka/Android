package com.navod.broadcast_receiverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makingNewBroadcastReceiver();
    }
    private void makingNewBroadcastReceiver(){
        Button button = findViewById(R.id.b1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.javainstitute.CUSTOM_INTENT");
                sendBroadcast(intent);
            }
        });
        IntentFilter intentFilter = new IntentFilter("com.example.javainstitute.CUSTOM_INTENT");
        MyBroadcastReceiver mbr = new MyBroadcastReceiver();
        registerReceiver(mbr, intentFilter);
    }
    private void declaringIntentFilter(){
        IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_LOW");
        MyBroadcastReceiver mbr = new MyBroadcastReceiver();
        registerReceiver(mbr, intentFilter);
    }
}