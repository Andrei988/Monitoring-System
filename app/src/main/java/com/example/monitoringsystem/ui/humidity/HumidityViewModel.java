package com.example.monitoringsystem.ui.humidity;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.monitoringsystem.model.Parameters;
import com.example.monitoringsystem.repository.ParametersRepository;

import java.text.ParseException;
import java.util.List;

public class HumidityViewModel extends AndroidViewModel {

    private static final String TAG = "HumidityViewModel";

    private ParametersRepository repository;

    public HumidityViewModel(Application application) {
        super(application);
        repository = ParametersRepository.getInstance(application);
    }

    public LiveData<List<Parameters>> getParametersToday() {
        return repository.getParameters();
    }

    public void updateParametersToday() {
        repository.updateParametersToday();
    }

    public LiveData<Boolean> isLoading() {
        return repository.isLoading();
    }

    public void updateParametersFromTo(String from, String to) throws ParseException {
        repository.updateParametersFromToDummyData(from, to);
    }
}
