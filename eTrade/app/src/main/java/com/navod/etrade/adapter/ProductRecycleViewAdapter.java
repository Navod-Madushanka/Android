package com.navod.etrade.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.navod.etrade.R;
import com.navod.etrade.SingleProductView;
import com.navod.etrade.entity.Product;
import com.navod.etrade.util.ImageSelector;

import java.util.List;

public class ProductRecycleViewAdapter extends RecyclerView.Adapter<ProductRecycleViewAdapter.ViewHolder> {
    private List<Product> productList;
    private Context context;
    public ProductRecycleViewAdapter(Context context, List<Product> productList){
        this.context = context;
        this.productList = productList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.product_layout, parent, false);
        return new ViewHolder(inflate, context, productList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private List<Product> productList;
        private ImageView productImage;
        private TextView model, price, discount;
        private ImageSelector imageSelector;

        public ViewHolder(@NonNull View itemView, Context context, List<Product> productList) {
            super(itemView);
            this.context = context;
            this.productList = productList;
            imageSelector = new ImageSelector();
            initView(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Product product = productList.get(position);
                        openSingleProductView(product);
                    }
                }
            });
        }
        private void openSingleProductView(Product product){
            Intent intent = new Intent(context, SingleProductView.class);
            intent.putExtra("product", product);
            context.startActivity(intent);
        }
        private void initView(View item){
            productImage = item.findViewById(R.id.imageView11);
            model = item.findViewById(R.id.textView9);
            price = item.findViewById(R.id.textView13);
            discount = item.findViewById(R.id.textView14);
        }
        public void bind(Product product){
            imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(0)), productImage);
            model.setText(product.getModel());
            price.setText("Rs: "+String.valueOf(product.getPrice()));
            if(product.getDiscount() != 0){
                discount.setText(String.valueOf(product.getDiscount())+"%");
            }else{
                discount.setText("Not Available");
            }
        }
    }
}
