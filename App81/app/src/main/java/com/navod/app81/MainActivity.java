package com.navod.app81;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextWithAsyncTask2();
            }
        });
    }
    private void changeTextWithAsyncTask2(){
        AsyncTask downloadAsyncTask = new AsyncTask<Integer, String, Boolean>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                TextView textView = findViewById(R.id.textView);
                textView.setText("Waiting...");
            }
            @Override
            protected Boolean doInBackground(Integer... integers) {
                for (int i = 1; i <= integers[0]; i++) {
                    publishProgress("File "+i+" Start Downloading...");
                    for (int j = 0; j <= 100; j++) {
                        publishProgress("File "+i+" Downloaded "+j+"%");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                return true;
            }
            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                TextView textView = findViewById(R.id.textView);
                textView.setText(values[0]);
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                TextView textView = findViewById(R.id.textView);
                textView.setText(aBoolean.toString());
            }
        };
        Integer i[] = new Integer[1];
        i[0] = 3;
        downloadAsyncTask.execute(i);
    }
    private void changeTextWithAsyncTask1(){
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                for (int i = 0; i <= 10; i++) {
                    publishProgress(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                super.onProgressUpdate(values);
                TextView textView = findViewById(R.id.textView);
                textView.setText(String.valueOf(values[0]));
            }
        };
        asyncTask.execute();
    }
    private void changeTextWithActivityRunOnUIThread(){
        TextView textView = findViewById(R.id.textView);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (i = 0; i <= 10; i++) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(i));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
    private void changeTextWithUIThreadUsingPost(){
        TextView textView = findViewById(R.id.textView);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (i = 0; i <= 10; i++) {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(i));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
    private void changeTextWithUIThreadUsingPostDelayed(){
        TextView textView = findViewById(R.id.textView);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (i = 0; i <= 10; i++) {
                    textView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(i));
                        }
                    },5000);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
    private void changeTextWithNewThreadT(){
        TextView textView = findViewById(R.id.textView);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    textView.setText(String.valueOf(i));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
    private void changeTextWithMainThread(){
        TextView textView = findViewById(R.id.textView);
        for(int i=0; i<=10; i++){
            textView.setText(String.valueOf(i));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}