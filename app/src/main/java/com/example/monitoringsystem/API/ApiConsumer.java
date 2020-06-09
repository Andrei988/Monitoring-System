package com.example.monitoringsystem.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiConsumer {

    private static final String TAG = "ApiConsumer";

    private static final String BASE_URL = "http://localhost:8080";
    private static ApiConsumer instance;
    private Retrofit retrofit;

    public static ApiConsumer getInstance() {
        if (instance == null) {
            instance = new ApiConsumer();
        }
        return instance;
    }

    private ApiConsumer() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getRetrofitClient() {
        return retrofit;
    }

}