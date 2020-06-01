package com.example.monitoringsystem.ui.temperature;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.monitoringsystem.model.Parameters;
import com.example.monitoringsystem.repository.ParametersRepository;

import java.text.ParseException;
import java.util.List;

public class TemperatureViewModel extends ViewModel {

    private static final String TAG = "TemperatureViewModel";

    private ParametersRepository repository;

    public TemperatureViewModel() {
        repository = ParametersRepository.getInstance();
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
