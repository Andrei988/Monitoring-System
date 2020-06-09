package com.example.monitoringsystem.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.monitoringsystem.API.API;
import com.example.monitoringsystem.API.ApiConsumer;
import com.example.monitoringsystem.model.Parameter;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ParametersRepository {

    private static final String TAG = "ParametersRepository";
    private static ParametersRepository instance;

    private MutableLiveData<List<Parameter>> parameters;
    public MutableLiveData<List<Parameter>> lastParameters;
    private MutableLiveData<Boolean> isLoading;

    public ParametersRepository(Application app) {
        parameters = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        lastParameters = new MutableLiveData<>();
        runLiveData();
    }


    public static synchronized ParametersRepository getInstance(Application app) {
        if (instance == null) {
            instance = new ParametersRepository(app);
        }
        return instance;
    }

//    public void updateParametersFromToDummyData(String from, String to) throws ParseException {
//        isLoading.postValue(true);
//        parameters.postValue(DummyData.updateParametersFromToDummyData(from, to));
//        isLoading.postValue(false);
//    }

    public void updateParametersFromTo(String from, String to) {
        isLoading.setValue(true);
        Retrofit retrofit = ApiConsumer.getInstance().getRetrofitClient();
        API api = retrofit.create(API.class);
        final Call<List<Parameter>> call = api.getParameters(from, to);

        call.enqueue(new Callback<List<Parameter>>() {
            @Override
            public void onResponse(@NotNull Call<List<Parameter>> call, @NotNull Response<List<Parameter>> response) {
                parameters.postValue(response.body());
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(@NotNull Call<List<Parameter>> call, @NotNull Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void runLiveData() {

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(300000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Retrofit retrofit = ApiConsumer.getInstance().getRetrofitClient();
                API api = retrofit.create(API.class);
                final Call<List<Parameter>> call = api.getLastParameters();

                call.enqueue(new Callback<List<Parameter>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Parameter>> call, @NotNull Response<List<Parameter>> response) {
                        lastParameters.postValue(response.body());
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<Parameter>> call, @NotNull Throwable t) {
                        Log.e(TAG, Objects.requireNonNull(t.getMessage()));
                    }
                });
            }
        }).start();
    }

    public MutableLiveData<List<Parameter>> getParameters() {
        return parameters;
    }

    public LiveData<Boolean> isLoading()
    {
        return isLoading;
    }

    public LiveData<List<Parameter>> getLastParameters() {
        return lastParameters;
    }


}
