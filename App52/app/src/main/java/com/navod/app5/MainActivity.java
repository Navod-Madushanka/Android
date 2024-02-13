package com.navod.app5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("name","Navod");
                startActivity(intent);
//                webSearch();
//                dial();
//                picK();
            }
        });
    }
    private void webSearch(){
        Intent intent = new Intent("android.intent.action.WEB_SEARCH");
        intent.putExtra("query", "Batman");
        startActivity(intent);
    }
    private void dial(){
        Intent intent = new Intent("android.intent.action.DIAL",Uri.parse("tel:0772967997"));
        startActivity(intent);
    }
    private void picK(){
        Intent intent = new Intent("android.intent.action.PICK");
        startActivity(intent);
    }
}