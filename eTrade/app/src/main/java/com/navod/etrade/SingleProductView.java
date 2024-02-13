package com.navod.etrade;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.navod.etrade.callback.AddToCartCallback;
import com.navod.etrade.entity.CartItem;
import com.navod.etrade.entity.Product;
import com.navod.etrade.service.CartService;
import com.navod.etrade.util.ImageSelector;
import com.navod.etrade.util.SharedPreferencesManager;

import java.util.UUID;

public class SingleProductView extends AppCompatActivity {
    private Product product;
    private ImageView mainImage, image1, image2, image3;
    private TextView model, price, discount, realPrice, description;
    private Button buyNow, cart;
    private int quantity;
    private ImageSelector imageSelector;
    public static final String TAG = SingleProductView.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_view);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            product = (Product) intent.getSerializableExtra("product");
            Log.i(TAG, "Product: "+product.toString());
        }
        initViews();
        setData();
        changeImages();

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
    }
    private void addItemToCart(){
        CartItem cartItem = new CartItem();
        cartItem.setId(UUID.randomUUID().toString());
        cartItem.setProductId(product.getId());
        cartItem.setPrice(Double.parseDouble(String.valueOf(product.getPrice()*quantity)));
        cartItem.setQuantity(quantity);
        CartService cartService = new CartService();
        cartService.addCartListToCart(SharedPreferencesManager.getUserId(SingleProductView.this), cartItem, new AddToCartCallback() {
            @Override
            public void onSuccess(String successMassage) {
                Log.i(TAG, successMassage);
            }

            @Override
            public void onFailure(String failMassage) {
                Log.i(TAG, failMassage);
            }
        });
    }
    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SingleProductView.this);
        builder.setTitle("Enter Quantity");

        LayoutInflater layoutInflater = getLayoutInflater();
        View inflate = layoutInflater.inflate(R.layout.dialog_enter_quantity, null);
        builder.setView(inflate);

        EditText quantityEditText= inflate.findViewById(R.id.editTextQuantity);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(product.getQuantity() > Integer.parseInt(quantityEditText.getText().toString())){
                    quantity = Integer.parseInt(quantityEditText.getText().toString());
                    addItemToCart();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void changeImages(){
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(0)), mainImage);
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(1)), mainImage);
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(2)), mainImage);
            }
        });
    }
    private void setData(){
        imageSelector = new ImageSelector();
        imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(0)), mainImage);
        imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(0)), image1);
        imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(1)), image2);
        imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(2)), image3);

        model.setText(product.getModel());
        price.setText("Price Rs. "+String.valueOf(product.getPrice()));
        if(product.getDiscount() != 0){
            discount.setText("Discount: "+String.valueOf(product.getDiscount())+"%");
            double value = (product.getPrice()*product.getDiscount())/100.0;
            realPrice.setText("Rs: "+String.valueOf(value));
        }else{
            discount.setText("Not Available");
            realPrice.setText(String.valueOf(product.getPrice()));
        }
        description.setText(product.getDescription());
    }
    private void initViews(){
        mainImage = findViewById(R.id.imageView12);
        image1 = findViewById(R.id.imageView13);
        image2 = findViewById(R.id.imageView14);
        image3 = findViewById(R.id.imageView15);

        model = findViewById(R.id.textView10);
        price = findViewById(R.id.textView11);
        discount = findViewById(R.id.textView12);
        realPrice = findViewById(R.id.textView16);
        description = findViewById(R.id.textView17);

        buyNow = findViewById(R.id.button2);
        cart = findViewById(R.id.button);
    }
}