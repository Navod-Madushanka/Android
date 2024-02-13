package com.navod.storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        sharedPreferencesDemo();
//        getPreferencesDemo();
        defaultSharedPreferencesDemo();
    }
    private void defaultSharedPreferencesDemo(){
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
        SharedPreferences.Editor edit = defaultSharedPreferences.edit();
        edit.putString("name", "Android");
        edit.apply();
    }
    private void getPreferencesDemo(){
        SharedPreferences preferences = getSharedPreferences("data",Context.MODE_PRIVATE);
        String name = preferences.getString("name", "No Name Found");
        textViewText(name);
    }
    private void textViewText(String text){
        TextView textView = findViewById(R.id.textView);
        textView.setText(text);
    }
    private void sharedPreferencesDemo(){
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("name", "Android");
        edit.commit();
    }
}