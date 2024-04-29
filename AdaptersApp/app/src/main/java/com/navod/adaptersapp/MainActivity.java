package com.navod.adaptersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        setAdapter();
        setCustomAdapter();
    }
    private void setCustomAdapter(){
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(MainActivity.this, getDataArray());
        listView.setAdapter(myCustomAdapter);
    }

    private void setAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, getDataArray());
        listView.setAdapter(adapter);
    }
    private String[] getDataArray(){
        return new String[]{"USA", "UK", "Canada", "Germany", "Italy", "Netherlands", "Japan"};
    }
    private void init(){
        listView = findViewById(R.id.listView);
    }
}