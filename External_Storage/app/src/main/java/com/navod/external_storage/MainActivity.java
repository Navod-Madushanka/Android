package com.navod.external_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = MainActivity.class.getName();
    private List<File> files = new ArrayList<>();
    private ListView listView;
    private FileAdapter fileAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        fileAdapter = new FileAdapter(getApplicationContext(), files);

        listView.setAdapter(fileAdapter);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                    if(!Environment.isExternalStorageManager()){
                        Snackbar.make(findViewById(R.id.container), "Permission Needed!", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Settings", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                        startActivity(intent);
                                    }
                                }).show();
                    }
                }else{
                    File directory = Environment.getExternalStorageDirectory();
                    loadFiles(directory);

                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = files.get(position);
                if(file.isDirectory()){
                    loadFiles(file);
                }else{
                    Toast.makeText(MainActivity.this, file.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                File file = files.get(position);
                new AlertDialog.Builder(MainActivity.this).setTitle("Delete File")
                        .setMessage("Do You Want To Delete This File?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteFiles(file);
                            }
                        }).setNegativeButton(android.R.string.no, null).show();
                return false;
            }
        });
    }

    void deleteFiles(File file){
        if(file.isDirectory()){
            File[] listFiles = file.listFiles();
            if(listFiles != null){
                for(File f : listFiles){
                    deleteFiles(f);
                }
            }
        }
        file.delete();
    }

    void loadFiles(File file){
        files.clear();
        files.addAll(Arrays.asList(file.listFiles()));
        fileAdapter.notifyDataSetChanged();
    }
    private void readExternalStorageDemo(){
        String state = Environment.getExternalStorageState();
        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.R){
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 0);
            if(!Environment.isExternalStorageManager()){
                Log.i(TAG, "Access Permission: "+ Environment.isExternalStorageManager());
                Snackbar.make(findViewById(R.id.container), "Permission Needed!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                startActivity(intent);
                            }
                        }).show();
            }
        }else{
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 0);
        }
    }
}