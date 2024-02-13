package com.navod.app121;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflateTextViewOption2();
            }
        });
    }
    private void inflateTextViewOption2(){
        LinearLayout linearLayoutMain = findViewById(R.id.linearLayoutMain);
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.layout1, linearLayoutMain);
    }
    private void inflateTextViewOption1(){
        LinearLayout linearLayoutMain = findViewById(R.id.linearLayoutMain);

        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.layout1, null);
        linearLayoutMain.addView(view1);
    }
}