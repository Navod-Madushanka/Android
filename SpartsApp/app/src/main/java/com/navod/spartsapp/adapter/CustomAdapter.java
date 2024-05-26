package com.navod.spartsapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.navod.spartsapp.R;
import com.navod.spartsapp.model.Sport;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.SportsViewHolder> {

    private List<Sport> sportList;

    public CustomAdapter(List<Sport> sportList) {
        this.sportList = sportList;
    }

    public List<Sport> getSportList() {
        return sportList;
    }

    public void setSportList(List<Sport> sportList) {
        this.sportList = sportList;
    }

    @NonNull
    @Override
    public SportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout, parent, false);
        return new SportsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsViewHolder holder, int position) {
        holder.bind(sportList.get(position));
    }

    @Override
    public int getItemCount() {
        return sportList.size();
    }

    public static class SportsViewHolder extends RecyclerView.ViewHolder {
        private TextView sportName;
        private ImageView sportImage;
        public SportsViewHolder(View view) {
            super(view);
            init(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "You clicked on " + sportName.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        public void bind(Sport sport) {
            sportName.setText(sport.getSportName());
            sportImage.setImageResource(sport.getSportImg());
        }

        private void init(View view){
            sportName = view.findViewById(R.id.textView);
            sportImage = view.findViewById(R.id.imageviewCard);
        }
    }
}
