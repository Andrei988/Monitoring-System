package com.example.monitoringsystem.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.monitoringsystem.repository.Database.AppDao;
import com.example.monitoringsystem.repository.Database.AppDatabase;
import com.example.monitoringsystem.repository.Database.Notification;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationsRepository {

    private AppDao dao;
    private static NotificationsRepository instance;

    private MutableLiveData<List<Notification>> notifications;

    public NotificationsRepository(Application app) {
        AppDatabase appDatabase = AppDatabase.getInstance(app);
        dao = appDatabase.appDao();
        notifications = new MutableLiveData<>();
    }

    public static synchronized NotificationsRepository getInstance(Application app) {
        if (instance == null) {
            instance = new NotificationsRepository(app);
        }
        return instance;
    }

    public void insert(Notification notification) throws ExecutionException, InterruptedException {
        new NotificationsRepository.InsertAsyncTask(dao).execute(notification);

        List<Notification> notifications = getNotificationsFromDb();
        this.notifications.postValue(notifications);
    }

    public List<Notification> getNotificationsFromDb() throws ExecutionException, InterruptedException {
        return new GetNotifications(dao).execute().get();
    }

    public LiveData<List<Notification>> getNotifications() {
        return notifications;
    }

    public void removeNotification(int pos) throws ExecutionException, InterruptedException {
        List<Notification> list = getNotificationsFromDb();
        Notification temp = list.get(pos);
        new RemoveItemAsync(dao).execute(temp);
    }

    public static class RemoveItemAsync extends AsyncTask<Notification, Void, Void> {
        private AppDao dao;

        private RemoveItemAsync(AppDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Notification... notifications) {
            dao.removeNotification(notifications[0]);
            return null;
        }
    }


    public static class GetNotifications extends AsyncTask<Void, Void, List<Notification>> {

        private AppDao dao;

        private GetNotifications(AppDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<Notification> doInBackground(Void... voids) {
            return dao.getNotifications();
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Notification, Void, Void> {

        private AppDao dao;

        private InsertAsyncTask(AppDao appDao) {
            this.dao = appDao;
        }


        @Override
        protected Void doInBackground(Notification... notifications) {
            dao.insertNotification(notifications[0]);

            return null;
        }
    }

}
