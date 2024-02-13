package com.navod.internal_database_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.navod.internal_database_room.dao.UserDAO;
import com.navod.internal_database_room.entity.User;
import com.navod.internal_database_room.util.AppDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserById();
    }
    private void getUserById(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase appDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_db").build();
                UserDAO userDAO = appDb.userDAO();
                User user = userDAO.getById(1);

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        TextView textView = findViewById(R.id.textView);
//                        textView.setText(user.getEmail());
//                    }
//                });
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = findViewById(R.id.textView);
                        textView.setText(user.getEmail());
                    }
                }, 5000);
            }
        }).start();
    }
    private void getAllUsers(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase appDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_db").build();
                UserDAO userDAO = appDb.userDAO();
                List<User> all = userDAO.getAll();
                all.forEach(u->{
                    Log.i(TAG, u.toString());
                });
            }
        }).start();
    }
    private void saveUser(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase appDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_db").build();
                UserDAO userDAO = appDb.userDAO();

                User user = new User();
                user.setFirst_name("Navod");
                user.setLast_name("Madushanka");
                user.setEmail("navod@gmail.com");
                user.setAddress("Mahakanda, Kandy");
                user.setContact("0772967997");

                userDAO.insert(user);
            }
        }).start();
    }
}