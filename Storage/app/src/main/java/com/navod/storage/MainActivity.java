package com.navod.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final static  String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        sharedPreferencesDemo();

        SharedPreferences data = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = data.edit();
        edit.putString("name", "App Data");
        edit.apply();
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity2();
            }
        });
    }
    private void gotoActivity2(){
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }
    private void sharedPreferencesDemo(){
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt("age", 23);
        edit.commit();
    }
}