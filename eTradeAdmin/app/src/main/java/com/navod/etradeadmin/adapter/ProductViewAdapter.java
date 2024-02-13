package com.navod.etradeadmin.adapter;

import static androidx.appcompat.resources.Compatibility.Api21Impl.inflate;

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

import com.navod.etradeadmin.R;
import com.navod.etradeadmin.SingleProductViewActivity;
import com.navod.etradeadmin.entity.Product;
import com.navod.etradeadmin.util.ImageSelector;

import java.util.List;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ViewHolder> {
    private List<Product> productList;

    private Context context;
    public ProductViewAdapter(List<Product> productList, Context context){
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_view_layout, parent, false);
        return new ViewHolder(view, context, productList);
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
        private List<Product> productList;
        private ImageView productImage;
        private TextView txtModel, txtPrice, txtDate;
        private Context context;
        public ViewHolder(@NonNull View itemView, Context context, List<Product> productList) {
            super(itemView);
            this.context = context;
            this.productList = productList;
            productImage = itemView.findViewById(R.id.imageView7);
            txtModel = itemView.findViewById(R.id.textView7);
            txtPrice = itemView.findViewById(R.id.textView11);
            txtDate = itemView.findViewById(R.id.textView12);

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
            Intent intent = new Intent(context, SingleProductViewActivity.class);
            intent.putExtra("productId", product.getId());
            context.startActivity(intent);
        }

        public void bind(Product product){
            ImageSelector imageSelector = new ImageSelector();
            imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(1)), productImage);
            txtModel.setText(product.getModel());
            txtPrice.setText(String.valueOf(product.getPrice()));
            txtDate.setText(product.getAddedAt().toString());
        }
    }

}
