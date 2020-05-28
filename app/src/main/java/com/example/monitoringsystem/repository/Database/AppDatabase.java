package com.example.monitoringsystem.repository.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

//@Database(entities = {ACU_Setup.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static AppDatabase instance;

    public abstract AppDao getDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "LocalDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
