package com.example.monitoringsystem.ui.co2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.monitoringsystem.repository.Database.Notification;
import com.example.monitoringsystem.model.Parameter;
import com.example.monitoringsystem.repository.Database.Preferences;
import com.example.monitoringsystem.repository.Database.Report;
import com.example.monitoringsystem.repository.NotificationsRepository;
import com.example.monitoringsystem.repository.ParametersRepository;
import com.example.monitoringsystem.repository.ReportsRepository;
import com.example.monitoringsystem.repository.SettingsRepository;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CO2ViewModel extends AndroidViewModel {

    private static final String TAG = "CO2ViewModel";

    private ParametersRepository repository;
    private SettingsRepository sRepository;
    private NotificationsRepository notificationsRepository;
    private ReportsRepository reportsRepository;

    public CO2ViewModel(Application application) {
        super(application);
        repository = ParametersRepository.getInstance(application);
        sRepository = SettingsRepository.getInstance(application);
        notificationsRepository = NotificationsRepository.getInstance(application);
        reportsRepository = ReportsRepository.getInstance(application);
    }

    public LiveData<List<Parameter>> getParameters() {
        return repository.getParameters();
    }

    public LiveData<Boolean> isLoading() {
        return repository.isLoading();
    }

    public void updateParametersFromTo(String from, String to) throws ParseException
    {
        repository.updateParametersFromTo(from, to);
    }

    public void insertNotification(Notification notification) throws ExecutionException, InterruptedException
    {
        notificationsRepository.insert(notification);
    }

    public LiveData<List<Parameter>> getLastParameters() {
        return repository.getLastParameters();
    }

    public Preferences getPreferences() throws ExecutionException, InterruptedException
    {
        return sRepository.getPreferences();
    }

    public void insertReport(Report report) throws ExecutionException, InterruptedException
    {
        reportsRepository.insert(report);
    }

}
