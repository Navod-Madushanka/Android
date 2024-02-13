package com.navod.app91;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }

}