package com.example.monitoringsystem.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.monitoringsystem.API.API;
import com.example.monitoringsystem.API.ApiConsumer;
import com.example.monitoringsystem.model.Parameters;
import com.example.monitoringsystem.repository.Database.AppDao;
import com.example.monitoringsystem.repository.Database.AppDatabase;
import com.example.monitoringsystem.repository.Database.Preferences;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.String.valueOf;

public class ParametersRepository {

    private static final String TAG = "ParametersRepository";
    private static ParametersRepository instance;

    private MutableLiveData<List<Parameters>> parameters;
    private MutableLiveData<Boolean> isLoading;

    public ParametersRepository(Application app) {
        parameters = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
    }


    public static synchronized ParametersRepository getInstance(Application app) {
        if (instance == null) {
            instance = new ParametersRepository(app);
        }
        return instance;
    }

    public void updateParametersTodayDummyData(int amount) {
        isLoading.postValue(true);
        List<Parameters> dummyData = new ArrayList<>();

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

            dummyData.add(new Parameters("humidity", "unitType", i, timestamp_sb));
            dummyData.add(new Parameters("CO2", "unitType", i, timestamp_sb));
            dummyData.add(new Parameters("temperature", "unitType", i, timestamp_sb));

            minute = minute + 5;
        }

        parameters.postValue(dummyData);
        isLoading.postValue(false);
    }


    public void updateParametersFromToDummyData(String from, String to) throws ParseException {


        isLoading.postValue(true);
        //List<Parameters> dummyData = new ArrayList<>();

        String mf = "" + from.charAt(14) + from.charAt(15);
        int minute_from = Integer.parseInt(mf);
        String hf = "" + from.charAt(11) + from.charAt(12);
        int hour_from = Integer.parseInt(hf);
        String df = "" + from.charAt(0) + from.charAt(1);
        int day_from = Integer.parseInt(df);
        String mof = "" + from.charAt(3) + from.charAt(4);
        int month_from = Integer.parseInt(mof);
        String yf = "2020";
        int year_from = Integer.parseInt(yf);

        String mt = "" + to.charAt(14) + to.charAt(15);
        int minute_to = Integer.parseInt(mt);
        String ht = "" + to.charAt(11) + to.charAt(12);
        int hour_to = Integer.parseInt(ht);
        String dt = "" + to.charAt(0) + to.charAt(1);
        int day_to = Integer.parseInt(dt);
        String mot = "" + to.charAt(3) + to.charAt(4);
        int month_to = Integer.parseInt(mot);
        String yt = "2020";
        int year_to = Integer.parseInt(yt);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        Date firstDate = sdf.parse(day_from + "/" + month_from + "/" + year_from + " " + hour_from + ":" + minute_from);
        Date secondDate = sdf.parse(day_to + "/" + month_to + "/" + year_to + " " + hour_to + ":" + minute_to);

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diffInMinutes =
                diffInMillies / 60000;

        int iterations = (int) (diffInMinutes / 5);

        for (int i = 0; i <= iterations; i++) {

            if (minute_from == 60) {
                mf = "00";
                minute_from = 0;
                hour_from++;
                if (hour_from < 10) {
                    hf = "0" + hour_from;
                } else {
                    hf = String.valueOf(hour_from);
                }
            } else if (hour_from == 24) {
                hour_from = 0;
                hf = "0" + hour_from;
                minute_from = 0;
                mf = "0" + minute_from;
                day_from++;
                if (day_from < 10) {
                    df = "0" + day_from;
                } else {
                    df = String.valueOf(day_from);
                }

            } else if (day_from == 30) {
                hour_from = 0;
                hf = "0" + hour_from;
                minute_from = 0;
                mf = "0" + minute_from;
                day_from = 1;
                df = "0" + day_from;
                month_from++;
                if (month_from < 10) {
                    mof = "0" + month_from;
                } else {
                    mof = String.valueOf(month_from);
                }
            } else if (month_from == 13) {
                hour_from = 0;
                hf = "0" + hour_from;
                minute_from = 0;
                mf = "0" + minute_from;
                day_from = 1;
                df = "0" + day_from;
                month_from = 1;
                mf = "0" + month_from;
                year_from++;
            }

            String timestamp_sb = "";

            if (minute_from < 10) {
                timestamp_sb = df + "-" + mof + "-" + yf + " " + hf + ":0" + minute_from;

            } else {
                timestamp_sb = df + "-" + mof + "-" + yf + " " + hf + ":" + minute_from;
            }

            Random random = new Random();
            //dummyData.add(new Parameters("Humidity", "%", random.nextInt(100) , timestamp_sb));
           // dummyData.add(new Parameters("CO2", "PPM", random.nextInt(100) , timestamp_sb));
            //dummyData.add(new Parameters("Temperature", "C", random.nextInt(100) , timestamp_sb));

            minute_from += 5;
        }
       // parameters.postValue(dummyData);
        isLoading.postValue(false);


    }

    public void updateParametersToday() {
        isLoading.setValue(true);
        Retrofit retrofit = ApiConsumer.getInstance().getRetrofitClient();
        API api = retrofit.create(API.class);
        final Call<List<Parameters>> call = api.getParameters();

        call.enqueue(new Callback<List<Parameters>>() {
            @Override
            public void onResponse(@NotNull Call<List<Parameters>> call, @NotNull Response<List<Parameters>> response) {
                parameters.postValue(response.body());
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(@NotNull Call<List<Parameters>> call, @NotNull Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    public void updateParametersFromTo(String from, String to) {
        isLoading.setValue(true);
        Retrofit retrofit = ApiConsumer.getInstance().getRetrofitClient();
        API api = retrofit.create(API.class);
        final Call<List<Parameters>> call = api.getParameters(from, to);

        call.enqueue(new Callback<List<Parameters>>() {
            @Override
            public void onResponse(@NotNull Call<List<Parameters>> call, @NotNull Response<List<Parameters>> response) {
                parameters.postValue(response.body());
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(@NotNull Call<List<Parameters>> call, @NotNull Throwable t) {
                Log.e(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }


    public MutableLiveData<List<Parameters>> getParameters() {
        return parameters;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }















}
