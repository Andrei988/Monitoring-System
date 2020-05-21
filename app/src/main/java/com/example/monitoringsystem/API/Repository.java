package com.example.monitoringsystem.API;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.monitoringsystem.model.Parameters;

import java.util.List;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class Repository {
    private static Repository instance;
    private MutableLiveData<List<Parameters>> parameters;

    private Repository() {
        parameters = new MutableLiveData<>();
    }

    public static synchronized Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public MutableLiveData<List<Parameters>> getMeasurement() {
        return parameters;
    }

    public void getMeasurement(String id) {
        Api api = ServiceGenerator.getApi();
        Call<MeasurementsResponse> call = api.getMeasurement();
        call.enqueue(new Callback<MeasurementsResponse>() {
            @Override
            public void onResponse(Call<MeasurementsResponse> call, Response<MeasurementsResponse> responsse) {
                if (responsse.code() == 200) {
                    parameters.setValue(responsse.body().getMeasures());
                }
            }


            @Override
            public void onFailure(Call<MeasurementsResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong");
            }
        });
    }
}
