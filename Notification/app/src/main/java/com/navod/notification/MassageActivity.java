package com.navod.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MassageActivity extends AppCompatActivity {
    private static final String TAG = MassageActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage);

        String name = getIntent().getExtras().getString("name");
        Log.i(TAG, name);
    }
}