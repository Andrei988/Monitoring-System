package com.example.monitoringsystem.ui.co2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.monitoringsystem.model.Parameters;
import com.example.monitoringsystem.repository.ParametersRepository;

import java.text.ParseException;
import java.util.List;

public class CO2ViewModel extends ViewModel {

    private static final String TAG = "CO2ViewModel";

    private ParametersRepository repository;

    public CO2ViewModel() {
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
