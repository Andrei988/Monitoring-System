package com.example.monitoringsystem.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.monitoringsystem.repository.Database.AppDao;
import com.example.monitoringsystem.repository.Database.AppDatabase;
import com.example.monitoringsystem.repository.Database.Preferences;

public class SettingsRepository {
    private AppDao appDao;
    private static SettingsRepository instance;

    public SettingsRepository(Application app) {
        AppDatabase appDatabase=AppDatabase.getInstance(app);
        appDao=appDatabase.appDao();
    }

    public void insert(Preferences fav){
        new InsertAsyncTask(appDao).execute(fav);
    }


    public static synchronized SettingsRepository getInstance(Application app) {
        if (instance == null) {
            instance = new SettingsRepository(app);
        }
        return instance;
    }

    private static class InsertAsyncTask extends AsyncTask<Preferences,Void,Void> {

        private AppDao appDao;

        private InsertAsyncTask(AppDao appDao){
            this.appDao=appDao;
        }


        @Override
        protected Void doInBackground(Preferences... preferencess) {
            appDao.insert(preferencess[0]);

            return null;
        }
    }

}
