package com.navod.etradeadmin.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.navod.etradeadmin.R;
import com.navod.etradeadmin.entity.User;
import com.navod.etradeadmin.util.ImageSelector;

import java.util.List;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewAdapter.ViewHolder> {
    private List<User> userList;
    private Context context;
    public UserViewAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
    }
    @NonNull
    @Override
    public UserViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_layout, parent, false);
        return new ViewHolder(view, context, userList);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private List<User> userList;
        private Context context;
        private ImageView imageView;
        private TextView id, name, mobile;
        private ImageSelector imageSelector;

        public ViewHolder(@NonNull View itemView, Context context, List<User> userList) {
            super(itemView);
            this.context = context;
            this.userList = userList;
            imageSelector = new ImageSelector();
            imageView = itemView.findViewById(R.id.imageView15);
            id = itemView.findViewById(R.id.textView34);
            name = itemView.findViewById(R.id.textView35);
            mobile = itemView.findViewById(R.id.textView36);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void bind(User user){
            if(user.getProfilePictureUrl() != null){
                imageSelector.loadImageUriIntoImageView(Uri.parse(user.getProfilePictureUrl()), imageView);
            }
            id.setText(user.getUserId());
            name.setText(user.getUsername());
            if(user.getMobileNumber() != null){
                mobile.setText(user.getMobileNumber());
            }else{
                mobile.setText("Null");
            }
        }
    }
}
