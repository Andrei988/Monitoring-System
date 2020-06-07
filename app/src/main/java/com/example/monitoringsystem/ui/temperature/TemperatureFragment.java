package com.example.monitoringsystem.ui.temperature;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitoringsystem.Adapters.ParametersAdapter;
import com.example.monitoringsystem.R;
import com.example.monitoringsystem.model.Parameter;
import com.example.monitoringsystem.repository.Database.Preferences;
import com.example.monitoringsystem.utils.ValueFormatter;
import com.example.monitoringsystem.utils.XAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import lombok.SneakyThrows;

public class TemperatureFragment extends Fragment {

    private static final String TAG = "TemperatureFragment";
    private static final String SENSOR_NAME = "Temperature";
    private static final String MEASUREMENT_TYPE = "°C";

    private NotificationManagerCompat notificationManager;

    private TemperatureViewModel temperatureViewModel;
    private LineChart lineChartTemperature;
    private ProgressBar mProgressBarTemperature;
    private EditText time_fromTemperature;
    private EditText time_toTemperature;
    private RecyclerView recyclerView;
    private ParametersAdapter parametersAdapter;
    private Button updateButtonTemperature;
    private TextView max_valueTemperature;
    private TextView min_valueTemperature;
    private TextView avg_valueTemperature;
    private TextView currentValue;
    private Button generateReportButton;

    public static TemperatureFragment newInstance() {
        return new TemperatureFragment();
    }
    @SneakyThrows
    @SuppressLint("SetTextI18n")
    @SuppressWarnings("deprecation")

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);

        notificationManager = NotificationManagerCompat.from(requireContext());

        temperatureViewModel.getLastParameters().observe(this, parameters -> {
            for (Parameter parameter : parameters) {
                if (parameter.getSensorName().equals(SENSOR_NAME)) {
                    currentValue.setText((int)parameter.getValue() + MEASUREMENT_TYPE);

                    Preferences prefs = new Preferences();

                    try {
                        prefs = temperatureViewModel.getPreferences();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(prefs == null) {
                        prefs = new Preferences();
                        prefs.setMaxCo2(100);
                        prefs.setMinCo2(0);
                        prefs.setMaxHumidity(100);
                        prefs.setMinHumidity(0);
                        prefs.setMaxTemp(100);
                        prefs.setMinTemp(0);
                    }


                    if(parameter.getValue() > Objects.requireNonNull(prefs).getMaxTemp())
                    {
                        Notification notification = new NotificationCompat.Builder(requireContext(), "1")
                                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                                .setContentTitle("Temperature Alert!!!")
                                .setContentText("MAX Value: " + prefs.getMaxTemp() + " Current value: " + (int)parameter.getValue()+MEASUREMENT_TYPE)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .build();

                        notificationManager.notify(0, notification);
                        try {
                            temperatureViewModel.insertNotification(new com.example.monitoringsystem.repository.Database.Notification(
                                    parameter.getTimestamp(),
                                    "Temperature",
                                    parameter.getValue(),
                                    prefs.getMinCo2(),
                                    prefs.getMaxCo2(),
                                    MEASUREMENT_TYPE
                            ));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if(parameter.getValue() < prefs.getMinTemp())
                    {
                        Notification notification = new NotificationCompat.Builder(requireContext(), "1")
                                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                                .setContentTitle("Temperature Alert!!!")
                                .setContentText("MIN Value: " + prefs.getMinTemp() + " Current value: " + (int)parameter.getValue()+MEASUREMENT_TYPE)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .build();
                        notificationManager.notify(0, notification);
                        try {
                            temperatureViewModel.insertNotification(new com.example.monitoringsystem.repository.Database.Notification(
                                    parameter.getTimestamp(),
                                    "Temperature",
                                    (int) parameter.getValue(),
                                    prefs.getMinCo2(),
                                    prefs.getMaxCo2(),
                                    MEASUREMENT_TYPE
                            ));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
            }
            generateReportButton.setOnClickListener(v -> {
                double current_co2 = 0;
                double current_hum = 0;
                double current_temp = 0;
                Date currentTime = Calendar.getInstance().getTime();
                String timestamp = currentTime.toString();


                for (Parameter parametersItem : parameters)
                    switch (parametersItem.getSensorName())
                    {
                        case "CO2":
                            current_co2 = parametersItem.getValue();
                            break;
                        case "Humidity":
                            current_hum = parametersItem.getValue();
                            break;
                        case "Temperature":
                            current_temp = parametersItem.getValue();
                            break;
                    }
                try {
                    temperatureViewModel.insertReport(new com.example.monitoringsystem.repository.Database.Report(
                            current_co2,
                            current_hum,
                            current_temp,
                            timestamp
                    ));
                    Toast.makeText(getContext(), "Report Created", Toast.LENGTH_SHORT).show();
                }
                catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        temperatureViewModel.getParametersToday().observe(this, parameters -> { // parameters from dummy data

            List<Parameter> temperatureParameters = new ArrayList<>();

            for(Parameter parametersItem : parameters) {
                if(parametersItem.getSensorName().equals("Temperature")) {
                    temperatureParameters.add(parametersItem);
                }
            }

            int temp_value_total = 0;
            int temp_value_max = 0;
            int temp_value_min = 100;

            for(Parameter parametersItem : temperatureParameters) { // computing min, max and avg
                temp_value_total+=parametersItem.getValue();
                if(temp_value_max < parametersItem.getValue()) {
                    temp_value_max = (int) parametersItem.getValue();
                } else if(temp_value_min > parametersItem.getValue()) {
                    temp_value_min = (int) parametersItem.getValue();
                }
            }

            int temp_value_average = temp_value_total/temperatureParameters.size();
            avg_valueTemperature.setText(temp_value_average + "°C");
            min_valueTemperature.setText(temp_value_min + "°C");
            max_valueTemperature.setText(temp_value_max + "°C");

            ArrayList<Entry> vals = new ArrayList<>();

            List<List<Parameter>> dataChunks = new ArrayList<>();
            Parameter parametersArray[] = temperatureParameters.toArray(new Parameter[0]);
            Parameter chunkArray[];
            int initialRatio = temperatureParameters.size() / 5;
            int ratio = temperatureParameters.size() / 5;

            for (int i = 0; i < temperatureParameters.size(); i += initialRatio) {
                try {
                    chunkArray = Arrays.copyOfRange(parametersArray, i, ratio);
                    List<Parameter> chunkList = new ArrayList<Parameter>(Arrays.asList(chunkArray));
                    dataChunks.add(chunkList);
                    ratio += initialRatio;
                } catch (IndexOutOfBoundsException e) {
                    chunkArray = Arrays.copyOfRange(parametersArray, i, temperatureParameters.size() - 1);
                    List<Parameter> chunkList = new ArrayList<Parameter>(Arrays.asList(chunkArray));
                    dataChunks.add(chunkList);
                    ratio += initialRatio;
                }
            }
            for (List<Parameter> parametersChunk : dataChunks) {
                parametersChunk.removeAll(Collections.singletonList(null)); // removing null objects from a chunk
                Parameter lastParameter = parametersChunk.get(parametersChunk.size() - 1); // last parameter from a chunk

                String timestamp = lastParameter.getTimestamp();
                String hoursNow = timestamp.charAt(11) + "" + timestamp.charAt(12); // string hour from the timestamp
                String minutesNow = timestamp.charAt(14) + "" + timestamp.charAt(15); // string minutes from the timestamp

                int hrNow = Integer.parseInt(hoursNow);
                int minNow = Integer.parseInt(minutesNow);

                long totalMinutes = minNow + hrNow * 60;

                if (lastParameter.getSensorName().equals("Temperature")) { // humidity check
                    vals.add(new Entry(totalMinutes, Math.round((float) lastParameter.getValue())));
                }
            }

            LineDataSet dataSet = new LineDataSet(vals, "parameters");
            dataSet.setValueTextColor(getResources().getColor(R.color.Black));
            dataSet.setValueTextSize(12);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(getResources().getColor(R.color.Blue));
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);
            LineData data = new LineData(dataSets);
            data.setValueFormatter(new ValueFormatter());
            // line chart customization
            lineChartTemperature.setData(data);
            configureLineChart();
        });

        temperatureViewModel.isLoading().observe(this, isLoading -> {
            int visibility = isLoading ? View.VISIBLE : View.INVISIBLE;
            if (mProgressBarTemperature != null) {
                mProgressBarTemperature.setVisibility(visibility);
            }
        });
    }

    @SneakyThrows
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_temperature, container, false);

        lineChartTemperature = view.findViewById(R.id.linechartTemperature);
        mProgressBarTemperature = view.findViewById(R.id.progressBarTemperature);
        time_fromTemperature = view.findViewById(R.id.dateAndTimeFromTemperature);
        time_toTemperature = view.findViewById(R.id.dateAndTimeToTemperature);
        updateButtonTemperature = view.findViewById(R.id.updateButtonTemperature);
        recyclerView= view.findViewById(R.id.temperaturerv);
        max_valueTemperature = view.findViewById(R.id.MAX_valueTemperature);
        min_valueTemperature = view.findViewById(R.id.MIN_valueTemperature);
        avg_valueTemperature = view.findViewById(R.id.AVG_valueTemperature);
        currentValue = view.findViewById(R.id.temperatureCurrentValue);
        generateReportButton = view.findViewById(R.id.buttonGenerateRepTemp);

        Calendar now = Calendar.getInstance(); // setting current hour as "time to" and "time from" is time_to - 2
        //int now_hour = now.get(Calendar.HOUR_OF_DAY); //TODO: uncomment
        int now_hour = 12;
        int now_minute = now.get(Calendar.MINUTE);
        int now_day = now.get(Calendar.DAY_OF_MONTH);
        int now_month = now.get(Calendar.MONTH) + 1; // it gives month - 1 don't know why
        int now_year = now.get(Calendar.YEAR);

        if (now_minute % 5 < 3) { // rounding minutes so that ending is 0 or 5
            now_minute = now_minute - (now_minute % 5);
        } else {
            now_minute = now_minute - (now_minute % 5) + 5;
        }
        String now_minute_string = "";
        if (now_minute < 10) {
            now_minute_string += "0" + now_minute;
        } else {
            now_minute_string += now_minute;
        }

        time_fromTemperature.setText("0"+now_day + "-0" + now_month + "-" + now_year + " " + (now_hour - 2) + ":" + now_minute_string);
        time_toTemperature.setText("0"+now_day + "-0" + now_month + "-" + now_year + " " + now_hour + ":" + now_minute_string);

        time_fromTemperature.setOnClickListener(v -> showDateTimeDialogFrom(time_fromTemperature));
        time_toTemperature.setOnClickListener(v -> showDateTimeDialogTo(time_toTemperature));

        updateButtonTemperature.setOnClickListener(v -> {
            try {
                temperatureViewModel.updateParametersFromTo(time_fromTemperature.getText().toString(), time_toTemperature.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        initRecyclerView();
        return view;
    }

    @SneakyThrows
    @Override
    public void onResume() {
        super.onResume();
        temperatureViewModel.updateParametersFromTo(time_fromTemperature.getText().toString(), time_toTemperature.getText().toString());
    }

   private void initRecyclerView() {

       temperatureViewModel.getLastParameters().observe(this.getViewLifecycleOwner(), parameters -> {

           List<Parameter> params = new ArrayList<>();

           for (Parameter parameter : parameters) {
               if (parameter.getSensorName().equals(SENSOR_NAME)) {
                   parameter.setNew(true);
                   params.add(parameter);
               }
           }

           if(parametersAdapter!=null) {
               List<Parameter> rwParams = parametersAdapter.getParametersRV();

               if(rwParams != null) {
                   for (Parameter parameter : rwParams) {
                       params.add(parameter);
                   }
               }
           }


           recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
           parametersAdapter = new ParametersAdapter(params, getActivity());
           recyclerView.setAdapter(parametersAdapter);

       });

        temperatureViewModel.getParametersToday().observe(this.getViewLifecycleOwner(), parameters -> {

            List<Parameter> temperatureParameters = new ArrayList<>();

            for(Parameter parametersItem : parameters) {
                if(parametersItem.getSensorName().equals("Temperature")) {
                    temperatureParameters.add(parametersItem);
                }
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            parametersAdapter = new ParametersAdapter(temperatureParameters, getActivity());
            recyclerView.setAdapter(parametersAdapter);
        });
    }

    private void showDateTimeDialogFrom(EditText time_from) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                time_from.setText(simpleDateFormat.format(calendar.getTime()));
            };

            new TimePickerDialog(requireContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

        };
        new DatePickerDialog(requireContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showDateTimeDialogTo(EditText time_to) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                time_to.setText(simpleDateFormat.format(calendar.getTime()));
            };

            new TimePickerDialog(requireContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

        };
        new DatePickerDialog(requireContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @SuppressWarnings("deprecation")
    private void configureLineChart() {
        lineChartTemperature.invalidate();
        lineChartTemperature.setDragEnabled(true);
        lineChartTemperature.setScaleEnabled(true);
        lineChartTemperature.getDescription().setEnabled(false); // remove description
        lineChartTemperature.setDrawBorders(false); // remove borders
        lineChartTemperature.getLegend().setEnabled(false);   // Hide legend
        lineChartTemperature.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // positioning x axis
        lineChartTemperature.getXAxis().setValueFormatter(new XAxisValueFormatter());
        lineChartTemperature.getXAxis().setLabelCount(5, true); // set x axis label count
        lineChartTemperature.getXAxis().setDrawGridLines(false); // remove background grid lines
        lineChartTemperature.getAxisRight().setDrawGridLines(false);
        lineChartTemperature.getAxisRight().setDrawLabels(true);
        lineChartTemperature.getAxisRight().setDrawLabels(true);
        lineChartTemperature.getAxisRight().setDrawGridLines(false);
        lineChartTemperature.getAxisRight().setAxisMinimum(0); // set bounds
        lineChartTemperature.getAxisRight().setAxisMaximum(100);
        lineChartTemperature.getAxisRight().setXOffset(15); // padding
        lineChartTemperature.getAxisLeft().setDrawLabels(true);
        lineChartTemperature.getAxisLeft().setDrawLabels(true);
        lineChartTemperature.getAxisLeft().setDrawGridLines(false);
        lineChartTemperature.getAxisLeft().setAxisMinimum(0); // set bounds
        lineChartTemperature.getAxisLeft().setAxisMaximum(100);
        lineChartTemperature.getAxisLeft().setXOffset(15); // padding

    }


}