package com.navod.luckynumberapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.stream.IntStream;

public class Activity2 extends AppCompatActivity {

    TextView luckyNumText;
    Button share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        init();

        luckyNumText.setText(""+generateRandomNum());

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareData(getUsername(), Integer.parseInt(luckyNumText.getText().toString()));
            }
        });
    }

    private void shareData(String username, int randomNum){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, username+" got lucky today!");
        intent.putExtra(Intent.EXTRA_TEXT, "His lucky number is: "+randomNum);
        startActivity(intent.createChooser(intent, "Choose Platform"));
    }

    private int generateRandomNum(){
        Random random = new Random();
        int upperLimit = 1000;
        return random.nextInt(upperLimit);
    }

    private String getUsername(){
        Intent intent = getIntent();
        return intent.getStringExtra("name");
    }

    private void init(){
        luckyNumText = findViewById(R.id.textView3);
        share = findViewById(R.id.button2);
    }
}