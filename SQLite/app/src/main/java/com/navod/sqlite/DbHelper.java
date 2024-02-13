package com.navod.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "User";
    private static final String TABLE_USER_ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS User(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR, email VARCHAR, address VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Product(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long insertUser(User user){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_USER, user.getName());
        values.put("email", user.getName());
        values.put("address", user.getEmail());
        long id = database.insert(TABLE_USER, null, values);
        database.close();
        return id;
    }
}
