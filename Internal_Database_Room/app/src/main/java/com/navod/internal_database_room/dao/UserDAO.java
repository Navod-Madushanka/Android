package com.navod.internal_database_room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.navod.internal_database_room.entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    long insert(User user);
    @Insert
    long[] insertAll(User... users);
    @Update
    int update(User user);
    @Delete
    int delete(User user);
    @Query("SELECT * FROM User")
    List<User> getAll();
    @Query("SELECT * FROM User WHERE id=:id")
    User getById(int id);
    @Query("SELECT* FROM User WHERE email=:email")
    User findByEmail(String email);
}
