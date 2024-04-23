package com.navod.counterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView counterText;
    Button btn;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterText.setText(String.valueOf(increaseCounter()));
            }
        });
    }
    private int increaseCounter(){
        return ++count;
    }
    private void initView(){
        counterText = findViewById(R.id.textView);
        btn = findViewById(R.id.button);
    }
}