package com.example.monitoringsystem.ui.settings;

import android.app.Application;
import android.widget.EditText;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.monitoringsystem.repository.Database.Preferences;
import com.example.monitoringsystem.repository.ParametersRepository;
import com.example.monitoringsystem.repository.SettingsRepository;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public class SettingsViewModel extends AndroidViewModel {

    private static final String TAG = "SettingsViewModel";
    private SettingsRepository repository;

    public SettingsViewModel(@NotNull Application application) {
        super(application);
        repository = SettingsRepository.getInstance(application);
    }

    public Preferences getPreferences() throws ExecutionException, InterruptedException {
        return repository.getPreferences();
    }

    public void savePref(String username, int minCo2, int maxCo2, int minHumidity, int maxHumidity, int minTemp, int maxTemp) {
        Preferences pref = new Preferences(username, minCo2, maxCo2, minHumidity, maxHumidity, minTemp, maxTemp);
        repository.insert(pref);
    }

}