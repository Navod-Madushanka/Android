package com.navod.etrade.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.navod.etrade.R;
import com.navod.etrade.SingleProductView;
import com.navod.etrade.callback.DeleteCartItemCallback;
import com.navod.etrade.callback.ProductCallback;
import com.navod.etrade.entity.CartItem;
import com.navod.etrade.entity.Product;
import com.navod.etrade.service.CartService;
import com.navod.etrade.service.ProductService;
import com.navod.etrade.util.ImageSelector;
import com.navod.etrade.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {
    private List<CartItem> cartItemList;
    private Context context;
    public CartRecyclerViewAdapter(Context context, List<CartItem> cartItems){
        this.context = context;
        this.cartItemList = cartItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(inflate, context, cartItemList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private List<CartItem> cartItemList;
        private ImageView productImage;
        private TextView model, price, quantity;
        private Button removeBtn;
        private Product product;
        private ProductService productService;
        private ImageSelector imageSelector;
        private CartItem currentCartItem;
        private static final String TAG = ViewHolder.class.getName();
        public ViewHolder(@NonNull View itemView, Context context, List<CartItem> cartItemList) {
            super(itemView);
            this.context = context;
            this.cartItemList = cartItemList;
            initViews(itemView);
            productService = new ProductService();
            imageSelector = new ImageSelector();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(product != null){
                        openSingleProductView(product);
                    }
                }
            });

            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CartService cartService = new CartService();
                    List<CartItem> deleteCartItem = new ArrayList<>();
                    deleteCartItem.add(currentCartItem);
                    cartService.DeleteCartItem(SharedPreferencesManager.getUserId(context), deleteCartItem, new DeleteCartItemCallback() {
                        @Override
                        public void onSuccess(String successMessage) {
                            Log.i(TAG, successMessage);
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Log.i(TAG, errorMessage);
                        }
                    });
                }
            });
        }
        private void openSingleProductView(Product product){
            Intent intent = new Intent(context, SingleProductView.class);
            intent.putExtra("product", product);
            context.startActivity(intent);
        }
        private void getProduct(String documentId){
            productService.getProductByDocumentId(documentId, new ProductCallback() {
                @Override
                public void onGetProduct(Product product1) {
                    product = product1;
                    updateUi();
                }

                @Override
                public void onError(String errorMessage) {
                    Log.i(TAG, errorMessage);
                }
            });
        }
        private void updateUi(){
            if(product != null){
                imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(0)), productImage);
                model.setText(product.getModel());
            }
        }
        public void bind(CartItem cartItem){
            if(cartItem != null){
                currentCartItem = cartItem;
                getProduct(cartItem.getProductId());
                price.setText(String.valueOf(cartItem.getPrice()));
                quantity.setText(String.valueOf(cartItem.getQuantity()));
            }
        }
        private void initViews(View itemView){
            productImage = itemView.findViewById(R.id.imageView17);
            model = itemView.findViewById(R.id.textView20);
            price = itemView.findViewById(R.id.textView23);
            quantity = itemView.findViewById(R.id.textView22);
            removeBtn = itemView.findViewById(R.id.button7);
        }
    }
}
