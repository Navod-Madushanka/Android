package com.navod.etradeadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.navod.etradeadmin.adapter.UpdateProductPageAdapter;
import com.navod.etradeadmin.entity.Product;
import com.navod.etradeadmin.listener.OnProductUpdateListener;
import com.navod.etradeadmin.service.ProductService;

public class UpdateProductActivity extends AppCompatActivity implements OnProductUpdateListener {
    public ViewPager viewPager;
    private static final String TAG = UpdateProductActivity.class.getName();
    public Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        requestPermissions(new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        }, 100);

        Intent intent = getIntent();
        if(intent.hasExtra("product")){
            product = (Product) intent.getSerializableExtra("product");
            Log.i(TAG, product.toString());
        }

        viewPager = findViewById(R.id.viewPager3);
        UpdateProductPageAdapter updateProductPageAdapter = new UpdateProductPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(updateProductPageAdapter);

    }

    @Override
    public void onProductUpdated(Product updatedProduct) {
        product = updatedProduct;
        Log.i(TAG, "Product: " + product.toString());
        Log.i(TAG, "Product updated: " + updatedProduct.toString());
    }

    @Override
    public void handleUpdatedProduct(Product product) {
        Log.i(TAG, "Updated product Object: "+product.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProductService productService = new ProductService(UpdateProductActivity.this);
                productService.updateProduct(product);
                Log.i(TAG, "Updating is complete");
            }
        }).start();
    }
}