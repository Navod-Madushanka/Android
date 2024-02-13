package com.navod.internal_database_room.util;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.navod.internal_database_room.dao.UserDAO;
import com.navod.internal_database_room.entity.User;

@Database(entities = {User.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
}
