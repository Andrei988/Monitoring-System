package com.example.monitoringsystem.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.monitoringsystem.API.API;
import com.example.monitoringsystem.API.ApiConsumer;
import com.example.monitoringsystem.model.Report;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.String.valueOf;

public class ReportsRepository {

    private static final String TAG = "ReportsRepository";
    private static ReportsRepository instance;
    private MutableLiveData<List<Report>> reports;
    private MutableLiveData<Boolean> isLoading;

    public ReportsRepository(Application app) {
        this.reports = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>();
    }

    public static synchronized ReportsRepository getInstance(Application app) {
        if (instance == null) {
            instance = new ReportsRepository(app);
        }
        return instance;
    }

    public void updateReportsTodayDummyData(int amount) {
        isLoading.postValue(true);
        List<Report> dummyData = new ArrayList<>();

        int minute = 0;
        int hour = 12;
        int date = 25;
        int month = 5;
        int year = 2020;

        for (int i = 0; i <= amount; i++) {

            if (minute == 60) {
                minute = 0;
                hour++;
            } else if (hour == 24) {
                hour = 0;
                date++;
            } else if (date == 30) {
                date = 1;
                month++;
            } else if (month == 13) {
                month = 1;
                year++;
            }

            StringBuilder minute_string = new StringBuilder(valueOf(minute));
            StringBuilder hour_string = new StringBuilder(valueOf(hour));
            StringBuilder date_string = new StringBuilder(valueOf(date));
            StringBuilder month_string = new StringBuilder(valueOf(month));

            if (minute_string.length() == 1) {
                minute_string = new StringBuilder(0 + minute_string.toString());
            }

            if (hour_string.length() == 1) {
                hour_string = new StringBuilder(0 + hour_string.toString());
            }

            if (date_string.length() == 1) {
                date_string = new StringBuilder(0 + date_string.toString());
            }

            if (month_string.length() == 1) {
                month_string = new StringBuilder(0 + month_string.toString());
            }

            String timestamp_sb = minute_string.toString()
                    + hour_string.toString()
                    + date_string.toString()
                    + month_string.toString()
                    + year;

            dummyData.add(new Report(123, 69, 100, timestamp_sb));
            dummyData.add(new Report(123,24,20,timestamp_sb));


            minute = minute + 5;
        }

        reports.postValue(dummyData);
        isLoading.postValue(false);
    }


    public void updateReportsFromToDummyData(String from, String to) throws ParseException {
        isLoading.postValue(true);
        reports.postValue(DummyData.updateReportsFromToDummyData(from, to));
        isLoading.postValue(false);
    }

    public void updateReportsToday() {
        isLoading.setValue(true);
        Retrofit retrofit = ApiConsumer.getInstance().getRetrofitClient();
        API api = retrofit.create(API.class);
        final Call<List<Report>> call = api.getReports();

        call.enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(@NotNull Call<List<Report>> call, @NotNull Response<List<Report>> response) {
                reports.postValue(response.body());
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(@NotNull Call<List<Report>> call, @NotNull Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void updateReportsFromTo(String from, String to) {
        isLoading.setValue(true);
        Retrofit retrofit = ApiConsumer.getInstance().getRetrofitClient();
        API api = retrofit.create(API.class);
        final Call<List<Report>> call = api.getReports(from, to);

        call.enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(@NotNull Call<List<Report>> call, @NotNull Response<List<Report>> response) {
                reports.postValue(response.body());
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(@NotNull Call<List<Report>> call, @NotNull Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    public MutableLiveData<List<Report>> getReports()
    {
        return reports;
    }

    public LiveData<Boolean> isLoading()
    {
        return isLoading;
    }

}
