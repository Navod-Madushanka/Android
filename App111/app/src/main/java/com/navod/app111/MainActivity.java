package com.navod.app111;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterWithGenerics();
            }
        });
    }
    private void adapterWithGenerics(){
        ArrayList<User> userArrayList = User.getSampleUserList();
        RecyclerView.Adapter adapter = new RecyclerView.Adapter<VH>() {
            @NonNull
            @Override
            public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View userView = inflater.inflate(R.layout.user_view_layout, parent, false);
                return new VH(userView);
            }

            @Override
            public void onBindViewHolder(@NonNull VH holder, int position) {
                holder.getTextView1VH().setText(userArrayList.get(position).getFirstName()+" "+userArrayList.get(position).getLastName());
                holder.getTextView2VH().setText(userArrayList.get(position).getMobile());

                if(userArrayList.get(position).getCompany().equals("Apple")){
                    holder.getImageViewVH().setImageResource(R.drawable.apple);
                } else if (userArrayList.get(position).getCompany().equals("Amazon"))
                    holder.getImageViewVH().setImageResource(R.drawable.amazon);
                else if (userArrayList.get(position).getCompany().equals("Microsoft")) {
                    holder.getImageViewVH().setImageResource(R.drawable.microsoft);
                }else if (userArrayList.get(position).getCompany().equals("Google")) {
                    holder.getImageViewVH().setImageResource(R.drawable.google);
                }else if (userArrayList.get(position).getCompany().equals("Gothom")) {
                    holder.getImageViewVH().setImageResource(R.drawable.batman);
                }
            }

            @Override
            public int getItemCount() {
                return userArrayList.size();
            }
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }
    private void recyclerViewDemo(){
        ArrayList<User> userArrayList = User.getSampleUserList();
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View userView = inflater.inflate(R.layout.user_view_layout, parent, false);
                return new VH(userView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                VH vh = (VH) holder;
                vh.getTextView1VH().setText(userArrayList.get(position).getFirstName()+" "+userArrayList.get(position).getLastName());
                vh.getTextView2VH().setText(userArrayList.get(position).getMobile());
            }

            @Override
            public int getItemCount() {
                return userArrayList.size();
            }
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }

}
class VH extends RecyclerView.ViewHolder{
    private final ImageView imageViewVH;
    private final TextView textView1VH;
    private final TextView textView2VH;
    public VH(@NonNull View itemView) {
        super(itemView);
        imageViewVH = itemView.findViewById(R.id.imageView);
        textView1VH = itemView.findViewById(R.id.textView);
        textView2VH = itemView.findViewById(R.id.textView2);
    }

    public ImageView getImageViewVH() {
        return imageViewVH;
    }

    public TextView getTextView1VH() {
        return textView1VH;
    }

    public TextView getTextView2VH() {
        return textView2VH;
    }
}