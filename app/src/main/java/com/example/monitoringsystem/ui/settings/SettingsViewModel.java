package com.example.monitoringsystem.ui.settings;

import android.app.Application;
import android.widget.EditText;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.monitoringsystem.repository.Database.Preferences;
import com.example.monitoringsystem.repository.ParametersRepository;

import org.jetbrains.annotations.NotNull;

public class SettingsViewModel extends AndroidViewModel {

    private static final String TAG = "SettingsViewModel";
    private ParametersRepository repository;

    public SettingsViewModel(@NotNull Application application) {
        super(application);
        repository=ParametersRepository.getInstance(application);
    }

    public void savePref(String username, int minCo2, int maxCo2, int minHumidity, int maxHumidity, int minTemp, int maxTemp) {
        Preferences pref=new Preferences(username,minCo2,maxCo2,minHumidity,maxHumidity,minTemp,maxTemp);
repository.insert(pref);
    }

}