package com.navod.volumeareaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyCustomAdaptor extends ArrayAdapter<Shape> {
    private ArrayList<Shape> shapesArrayList;
    Context context;

    public MyCustomAdaptor(ArrayList<Shape> shapesArrayList, Context context) {
        super(context, R.layout.grid_item_layout);
        this.shapesArrayList = shapesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Shape shape = getItem(position);
        MyViewHolder myViewHolder;
        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.grid_item_layout, parent, false);
            myViewHolder.shapeName = convertView.findViewById(R.id.textView);
            myViewHolder.shapeImg = convertView.findViewById(R.id.imageView);
            convertView.setTag(myViewHolder);

        }else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.shapeName.setText(shape.getShapeName());
        myViewHolder.shapeImg.setImageResource(shape.getShapeImg());

        return convertView;
    }

    private static class MyViewHolder {
        TextView shapeName;
        ImageView shapeImg;
    }
}
