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

    private ParametersRepository repository;

    public SettingsViewModel(@NotNull Application application) {
        super(application);
        repository=new ParametersRepository(application);

    }


    public void savePref(String username, EditText minCo2, EditText maxCo2, EditText minHumidity, EditText maxHumidity, EditText minTemp, EditText maxTemp) {
        Preferences pref=new Preferences(username,Integer.parseInt(minCo2.getText().toString()),Integer.parseInt(maxCo2.getText().toString()),
                Integer.parseInt(minHumidity.getText().toString()),Integer.parseInt(maxHumidity.getText().toString()),Integer.parseInt(minTemp.getText().toString()),
                Integer.parseInt(maxTemp.getText().toString()));
repository.insert(pref);
    }

}