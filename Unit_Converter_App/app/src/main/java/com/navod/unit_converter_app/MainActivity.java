package com.navod.unit_converter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText inputNum;
    Button convertBtn;
    TextView outputNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputNum.setText(String.valueOf(calculation()));
            }
        });
    }
    private double calculation(){
        String input = inputNum.getText().toString();
        return Double.parseDouble(input) * 2.20462;
    }
    private void initViews(){
        inputNum = findViewById(R.id.editTextNumber);
        convertBtn = findViewById(R.id.button);
        outputNum = findViewById(R.id.textView2);
    }
}