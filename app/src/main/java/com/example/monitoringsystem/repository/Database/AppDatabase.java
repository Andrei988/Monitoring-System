package com.example.monitoringsystem.repository.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import io.reactivex.annotations.NonNull;

@Database(entities = Preferences.class,version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static AppDatabase instance;

    public abstract AppDao appDao();



    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "LocalDB")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    //callback for populating
    private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private AppDao appDao;
        private PopulateDbAsyncTask(AppDatabase appDatabase){
            appDao=appDatabase.appDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            appDao.insert(new Preferences("TestLucian",32,123,123,213,213,231));
            return null;
        }
    }


}
