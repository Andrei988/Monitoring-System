package com.example.monitoringsystem.repository.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Preferences pref);

    @Update
    void update(Preferences pref);


    @Query("SELECT * FROM preferences_table WHERE username LIKE :name")
    Preferences findPrefByUsername(String name);

}
