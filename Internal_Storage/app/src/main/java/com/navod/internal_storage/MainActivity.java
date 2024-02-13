package com.navod.internal_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getExternalStorageDemo();
    }
    private void getExternalStorageDemo(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            String[] list = externalStorageDirectory.list();
            for(String l: list){
                Log.i(TAG, l);
            }
            Log.i(TAG, externalStorageDirectory.getName());
        }
    }

    private void getDirectoryDemo(){
        File filesDir = getFilesDir();
        Log.i(TAG, filesDir.getName());
    }
    private void readPropertyDemo(String fileName){
        Properties properties = new Properties();
        try {
            FileInputStream inputStream = openFileInput(fileName);
            properties.load(inputStream);
            Log.i(TAG, properties.getProperty("name"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void writeFileAsPropertyDemo(){
        Properties properties = new Properties();
        try {
            FileOutputStream outputStream = openFileOutput("app.txt", Context.MODE_PRIVATE);
            properties.put("name", "Internal_Storage_App");
            properties.put("version", "1.0");
            properties.store(outputStream, "This is comment");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void writeFileDemo(){
        try {
            FileOutputStream stream = openFileOutput("app.txt", Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write("Hello Java");
            writer.close();
            stream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void fileListDemo(){
        String[] strings = fileList();
        for(String s:strings){
            Log.i(TAG, s);
        }
    }
    private void readFile(){
        try {
            FileInputStream input = openFileInput("abc.txt");
            InputStreamReader reader = new InputStreamReader(input);

            char[] buffer = new char[1024];
            StringBuilder builder = new StringBuilder();
            int read;
            while ((read = reader.read(buffer))>0){
                String s = String.copyValueOf(buffer, 0, read);
                builder.append(s);
            }
            reader.close();
            Log.i(TAG, builder.toString());
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}