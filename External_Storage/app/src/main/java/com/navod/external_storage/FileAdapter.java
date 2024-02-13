package com.navod.external_storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class FileAdapter extends BaseAdapter {
    private Context context;
    private List<File> files;
    private LayoutInflater inflater;
    public FileAdapter(Context context, List<File> files){
        this.context = context;
        this.files = files;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.file_item_layout, null);
        ImageView icon = convertView.findViewById(R.id.fileIcon);
        TextView fileText = convertView.findViewById(R.id.fileText);

        File file = files.get(position);
        icon.setImageResource(file.isDirectory() ? R.drawable.icon_folder_24 : R.drawable.icon_file_24);
        fileText.setText(file.getName());
        return convertView;
    }
}
