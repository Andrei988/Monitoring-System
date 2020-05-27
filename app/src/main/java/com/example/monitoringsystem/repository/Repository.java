package com.example.monitoringsystem.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.monitoringsystem.API.API;
import com.example.monitoringsystem.API.ApiConsumer;
import com.example.monitoringsystem.model.Parameters;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Repository {
    private static Repository instance;
    private MutableLiveData<List<Parameters>> parameters;
    private static final String TAG = "Repository";

    private Repository() {
        parameters = new MutableLiveData<>();
    }

    public static synchronized Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }

        return instance;
    }

    public MutableLiveData<List<Parameters>> getData() {
        return parameters;
    }

    public void updateData() {
        Retrofit retrofit = ApiConsumer.getInstance().getRetrofitClient();
        API api = retrofit.create(API.class);
        final Call<List<Parameters>> call = api.getMeasurement();

        call.enqueue(new Callback<List<Parameters>>() {
            @Override
            public void onResponse(Call<List<Parameters>> call, Response<List<Parameters>> responsse) {
                System.out.println(responsse.body());
                Log.d(TAG, responsse.body().toString());
                parameters.postValue(responsse.body());
            }

            @Override
            public void onFailure(Call<List<Parameters>> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong");
                Log.e(TAG,  t.getMessage());
            }
        });
    }


}
