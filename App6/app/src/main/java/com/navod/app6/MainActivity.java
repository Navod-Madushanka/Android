package com.navod.app6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.editTextText);
                EditText password = findViewById(R.id.editTextNumberPassword);

                if(username.getText().toString().toLowerCase().equals("navod") && password.getText().toString().toLowerCase().equals("1234")){
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}