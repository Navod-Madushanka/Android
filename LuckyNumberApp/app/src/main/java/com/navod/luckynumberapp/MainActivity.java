package com.navod.luckynumberapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    
    EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                gotoSecondActivity(name);
            }
        });
    }
    
    private void gotoSecondActivity(String name){
        Intent intent = new Intent(MainActivity.this, Activity2.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
    
    private void init(){
        nameText = findViewById(R.id.editTextText);
    }
}