package com.navod.frenchteacherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button blackBtn, greenBtn, purpleBtn, redBtn, yellowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        blackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMedia(R.raw.black);
            }
        });
        greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMedia(R.raw.green);
            }
        });
        purpleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMedia(R.raw.purple);
            }
        });
        redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMedia(R.raw.red);
            }
        });
        yellowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMedia(R.raw.yellow);
            }
        });
    }
    
    private void playMedia(int mp3Id){
        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, mp3Id);
        mediaPlayer.start();
    }
    private void init(){
        blackBtn = findViewById(R.id.button);
        greenBtn = findViewById(R.id.button2);
        purpleBtn = findViewById(R.id.button3);
        redBtn = findViewById(R.id.button4);
        yellowBtn = findViewById(R.id.button5);
    }
}