package com.navod.etradeadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.os.Bundle;

import com.navod.etradeadmin.adapter.PageFormAdapter;
import com.navod.etradeadmin.entity.Product;

public class ProductFormActivity extends AppCompatActivity {

    public ViewPager viewPager;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        }, 100);

        product = new Product();

        viewPager = findViewById(R.id.viewPager3);
        PageFormAdapter adapter = new PageFormAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}