package com.example.monitoringsystem.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.monitoringsystem.repository.Database.AppDao;
import com.example.monitoringsystem.repository.Database.AppDatabase;
import com.example.monitoringsystem.repository.Database.Preferences;

import java.util.concurrent.ExecutionException;

public class SettingsRepository {
    private AppDao appDao;
    private static SettingsRepository instance;

    public SettingsRepository(Application app) {
        AppDatabase appDatabase=AppDatabase.getInstance(app);
        appDao=appDatabase.appDao();
    }

    public static synchronized SettingsRepository getInstance(Application app) {
        if (instance == null) {
            instance = new SettingsRepository(app);
        }
        return instance;
    }

    public void insert(Preferences preferences){
        new InsertAsyncTask(appDao).execute(preferences);
    }

    public Preferences getPreferences() throws ExecutionException, InterruptedException {
        return new GetPreferences(appDao).execute().get();
    }


    public static class GetPreferences extends AsyncTask<Void, Void, Preferences> {

        private AppDao dao;

        private GetPreferences(AppDao dao) {
            this.dao = dao;
        }

        @Override
        protected Preferences doInBackground(Void... voids) {
            return dao.getPreferences();
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Preferences,Void,Void> {

        private AppDao appDao;

        private InsertAsyncTask(AppDao appDao){
            this.appDao=appDao;
        }


        @Override
        protected Void doInBackground(Preferences... preferencess) {
            appDao.setPreference(preferencess[0]);

            return null;
        }
    }

}
