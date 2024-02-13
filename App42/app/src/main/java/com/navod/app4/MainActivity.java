package com.navod.app4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m();
            }
        });

    }

    private void changeTextBtn(Button button){
        button.setText("hello");
    }

    private void changeText(){
        TextView textView = findViewById(R.id.textView);
        textView.setText("Hello");
    }

    public void m(){
        changeText();
    }
}