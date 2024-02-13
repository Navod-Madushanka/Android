package com.navod.app10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWithLayout();
            }
        });
    }
    private void addWithoutLayout(){
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        LinearLayout layoutX =(LinearLayout) layoutInflater.inflate(R.layout.text_view_layout, null);
        TextView textView = layoutX.findViewById(R.id.textView);
        layoutX.removeView(textView);

        LinearLayout layout = findViewById(R.id.layout1);
        layout.addView(textView);
    }
    private void addWithLayout(){
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        LinearLayout layoutX =(LinearLayout) layoutInflater.inflate(R.layout.text_view_layout, null);
        LinearLayout layout = findViewById(R.id.layout1);
        layout.addView(layoutX);
    }
}