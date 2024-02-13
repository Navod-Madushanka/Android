package com.navod.etradeadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import com.navod.etradeadmin.CallBack.ModelFetchCallback;
import com.navod.etradeadmin.CallBack.ProductFetchedCallback;
import com.navod.etradeadmin.adapter.HomePageAdapter;
import com.navod.etradeadmin.adapter.ProductViewAdapter;
import com.navod.etradeadmin.entity.Product;
import com.navod.etradeadmin.service.ProductService;
import com.navod.etradeadmin.util.ReloadHome;
import com.navod.etradeadmin.util.SecureApp;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getName();
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!SecureApp.access){
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        requestPermissions(new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        }, 100);

        viewPager = findViewById(R.id.viewPager3);
        HomePageAdapter homePageAdapter = new HomePageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(homePageAdapter);

        findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        findViewById(R.id.imageView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });

        findViewById(R.id.imageView6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(4);
            }
        });

    }
}