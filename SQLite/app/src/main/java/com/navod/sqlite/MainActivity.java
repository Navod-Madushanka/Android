package com.navod.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callDbHelper();
    }

    private void callDbHelper(){
        DbHelper dbHelper = new DbHelper(getApplicationContext());
        User user = new User("Navod", "navod@gmail.com", "Kandy");
        dbHelper.insertUser(user);
    }
    private void openAndCloseSQLiteDemo(){
        SQLiteDatabase appDb = openOrCreateDatabase("App_db", MODE_PRIVATE, null);
        appDb.execSQL("CREATE TABLE IF NOT EXISTS User(name VARCHAR, email VARCHAR, address VARCHAR)");
//        appDb.close();
    }
}