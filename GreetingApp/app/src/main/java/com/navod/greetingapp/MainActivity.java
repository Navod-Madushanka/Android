package com.navod.greetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Welcome "+getUserName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getUserName(){
        return editText.getText().toString();
    }

    private void initView(){
        editText = findViewById(R.id.editTextText);
        button = findViewById(R.id.button);
        title = findViewById(R.id.textView);
    }
}