package com.navod.app73;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webViewBtn();
    }

    private void webViewBtn(){
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webView = findViewById(R.id.webView);
                webView.loadUrl("https://www.google.com/");
            }
        });
    }
    private void switchOnAction(){
        Switch aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    Toast.makeText(MainActivity.this, "On", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Off", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}