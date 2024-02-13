package com.navod.app5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        String name = savedInstanceState.getString("name");
        Intent i = getIntent();
        String name1 = i.getStringExtra("name");
        System.out.println(name1);

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void changeView(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}