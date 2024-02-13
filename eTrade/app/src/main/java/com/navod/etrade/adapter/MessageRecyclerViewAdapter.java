package com.navod.etrade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.navod.etrade.R;
import com.navod.etrade.entity.IndividualMessage;

import java.util.List;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder> {
    private List<IndividualMessage> messageList;
    private Context context;
    public MessageRecyclerViewAdapter(Context context, List<IndividualMessage> messageList){
        this.context = context;
        this.messageList = messageList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.message_layout, parent, false);
        return new ViewHolder(inflate, context, messageList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IndividualMessage individualMessage = messageList.get(position);
        holder.bind(individualMessage);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private List<IndividualMessage> messageList;
        private TextView titleTxt, messageTxt;
        public ViewHolder(@NonNull View itemView, Context context, List<IndividualMessage> messageList) {
            super(itemView);
            this.context = context;
            this.messageList = messageList;
            initViews(itemView);
        }
        public void bind(IndividualMessage message){
            titleTxt.setText(message.getTitle());
            messageTxt.setText(message.getContent());
        }
        public void initViews(View itemView){
            titleTxt = itemView.findViewById(R.id.textView43);
            messageTxt = itemView.findViewById(R.id.textView44);
        }
    }
}
