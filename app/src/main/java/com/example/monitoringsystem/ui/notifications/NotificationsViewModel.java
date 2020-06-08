package com.example.monitoringsystem.ui.notifications;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.monitoringsystem.repository.Database.Notification;
import com.example.monitoringsystem.repository.NotificationsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationsViewModel extends AndroidViewModel {

    private static final String TAG = "NotificationsViewModel";

    private NotificationsRepository notificationsRepository;

    public NotificationsViewModel(Application application) {
        super(application);
        notificationsRepository = NotificationsRepository.getInstance(application);
    }

    public LiveData<List<Notification>> getNotifications() throws ExecutionException, InterruptedException {
        return notificationsRepository.getNotifications();
    }

    public void insertNotification(Notification notification) throws ExecutionException, InterruptedException {
        notificationsRepository.insert(notification);
    }
    public void removeItem(int pos) throws ExecutionException, InterruptedException {
        notificationsRepository.removeNotification(pos);
    }


}