package com.navod.etrade.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.navod.etrade.R;
import com.navod.etrade.callback.ProductCallback;
import com.navod.etrade.entity.OrderItem;
import com.navod.etrade.entity.Product;
import com.navod.etrade.service.ProductService;
import com.navod.etrade.util.ImageSelector;

import java.util.List;

public class OrderItemRecyclerViewAdapter extends RecyclerView.Adapter<OrderItemRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<OrderItem> orderItems;
    public OrderItemRecyclerViewAdapter(Context context, List<OrderItem> orderItems){
        this.context = context;
        this.orderItems = orderItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.order_item_layout, parent, false);
        return new ViewHolder(inflate, context, orderItems);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);
        holder.bind(orderItem);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private List<OrderItem> orderItems;
        private ImageView productImage;
        private TextView model, quantity;
        private ProductService productService;
        private ImageSelector imageSelector;
        private static final String TAG = ViewHolder.class.getName();
        public ViewHolder(@NonNull View itemView, Context context, List<OrderItem> orderItems) {
            super(itemView);
            this.context = context;
            this.orderItems = orderItems;
            initViews(itemView);
        }
        private  void bind(OrderItem orderItem){
            if(orderItem != null){
                getProduct(orderItem.getProductId());
                quantity.setText(String.valueOf(orderItem.getQuantity()));
            }
        }
        private void getProduct(String productId){
            productService.getProductByDocumentId(productId, new ProductCallback() {
                @Override
                public void onGetProduct(Product product) {
                    model.setText(product.getModel());
                    imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(0)), productImage);
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e(TAG, errorMessage);
                }
            });
        }
        private void initViews(View itemView){
            productImage = itemView.findViewById(R.id.imageView20);
            model = itemView.findViewById(R.id.textView39);
            quantity = itemView.findViewById(R.id.quantityTextView);
            productService = new ProductService();
            imageSelector= new ImageSelector();
        }
    }
}
