package com.navod.etradeadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.navod.etradeadmin.CallBack.GetProductCallback;
import com.navod.etradeadmin.entity.Product;
import com.navod.etradeadmin.service.ProductService;
import com.navod.etradeadmin.util.ImageSelector;

public class SingleProductViewActivity extends AppCompatActivity {
    private static final String TAG = SingleProductViewActivity.class.getName();
    private ImageView imageMain, image1, image2, image3;
    private TextView model, brand, description, price, qty, discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_view);

        initViews();

        Intent intent = getIntent();
        if (intent != null) {
            String productId = intent.getStringExtra("productId");

            ProductService productService = new ProductService();
            productService.getProductById(productId, new GetProductCallback() {
                @Override
                public void getProduct(Product product1, String documentId) {
                    displayProductDetails(product1);
                    navigateToUpdateProductActivity(product1);
                }
            });
        } else {
            navigateToHomeActivity();
        }
    }

    private void initViews() {
        imageMain = findViewById(R.id.imageView8);
        image1 = findViewById(R.id.imageView9);
        image2 = findViewById(R.id.imageView10);
        image3 = findViewById(R.id.imageView11);

        model = findViewById(R.id.textView13);
        brand = findViewById(R.id.textView15);
        description = findViewById(R.id.textView17);
        price = findViewById(R.id.textView21);
        discount = findViewById(R.id.textView19);
        qty = findViewById(R.id.textView23);
    }

    private void displayProductDetails(Product product) {
        ImageSelector imageSelector = new ImageSelector();
        imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(0)), imageMain);
        imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(0)), image1);
        imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(1)), image2);
        imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(2)), image3);

        model.setText(product.getModel());
        brand.setText(product.getBrand());
        description.setText(product.getDescription());
        price.setText(String.valueOf(product.getPrice()));
        discount.setText(String.valueOf(product.getDiscount()));
        qty.setText(String.valueOf(product.getQuantity()));
    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(SingleProductViewActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void navigateToUpdateProductActivity(Product product){
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SingleProductViewActivity.this, UpdateProductActivity.class);
                intent1.putExtra("product", product);
                startActivity(intent1);
            }
        });
    }

}