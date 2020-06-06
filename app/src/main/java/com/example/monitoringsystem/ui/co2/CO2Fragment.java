package com.example.monitoringsystem.ui.co2;

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

import com.example.monitoringsystem.Adapters.NotificationsAdapter;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

import lombok.SneakyThrows;

public class CO2Fragment extends Fragment {

    private static final String TAG = "CO2Fragment";
    private static final String SENSOR_NAME = "CO2";
    private static final String MEASUREMENT_TYPE = "PPM";

    private NotificationManagerCompat notificationManager;

    private CO2ViewModel CO2ViewModel;
    private LineChart lineChartCO2;
    private ProgressBar mProgressBarCO2;
    private EditText time_fromCO2;
    private EditText time_toCO2;
    private RecyclerView recyclerView;
    private ParametersAdapter parametersAdapter;
    private Button updateButtonCO2;
    private TextView max_valueCO2;
    private TextView min_valueCO2;
    private TextView avg_valueCO2;
    private TextView currentValue;


    public static CO2Fragment newInstance() {
        return new CO2Fragment();
    }

    @SneakyThrows
    @SuppressLint("SetTextI18n")
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CO2ViewModel = new ViewModelProvider(this).get(CO2ViewModel.class);

        notificationManager = NotificationManagerCompat.from(requireContext());

        CO2ViewModel.getLastParameters().observe(this, parameters -> {
            for (Parameter parameter : parameters) {
                if (parameter.getSensorName().equals(SENSOR_NAME)) {
                    currentValue.setText((int) parameter.getValue() + MEASUREMENT_TYPE);

                    Preferences prefs = new Preferences();

                    try {
                        prefs = CO2ViewModel.getPreferences();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (prefs == null) {
                        prefs = new Preferences();
                        prefs.setMaxCo2(100);
                        prefs.setMinCo2(0);
                        prefs.setMaxHumidity(100);
                        prefs.setMinHumidity(0);
                        prefs.setMaxTemp(100);
                        prefs.setMinTemp(0);
                    }

                    if (parameter.getValue() > prefs.getMaxCo2()) {
                        Notification notification = new NotificationCompat.Builder(requireContext(), "1")
                                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                                .setContentTitle("CO2 Alert!!!")
                                .setContentText("MAX Value: " + prefs.getMaxCo2() + " Current value: " + (int) parameter.getValue() + MEASUREMENT_TYPE)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .build();

                        notificationManager.notify(0, notification);

                        try {
                            CO2ViewModel.insertNotification(new com.example.monitoringsystem.repository.Database.Notification(
                                    parameter.getTimestamp(),
                                    "CO2",
                                    parameter.getValue(),
                                    prefs.getMinCo2(),
                                    prefs.getMaxCo2(),
                                    MEASUREMENT_TYPE
                            ));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (parameter.getValue() < prefs.getMinCo2()) {
                        Notification notification = new NotificationCompat.Builder(requireContext(), "1")
                                .setSmallIcon(R.drawable.ic_warning_black_24dp)
                                .setContentTitle("CO2 Alert!!!")
                                .setContentText("MIN Value: " + prefs.getMinCo2() + " Current value: " + (int) parameter.getValue() + MEASUREMENT_TYPE)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .build();
                        notificationManager.notify(0, notification);

                        try {
                            CO2ViewModel.insertNotification(new com.example.monitoringsystem.repository.Database.Notification(
                                    parameter.getTimestamp(),
                                    "CO2",
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
        });

        CO2ViewModel.getParameters().observe(this, parameters -> { // parameters from dummy data

            List<Parameter> co2Parameters = new ArrayList<>();

            for (Parameter parametersItem : parameters) {
                if (parametersItem.getSensorName().equals(SENSOR_NAME)) {
                    co2Parameters.add(parametersItem);
                }
            }

            double co2_value_total = 0;
            int co2_value_max = 0;
            int co2_value_min = 100;

            for (Parameter parametersItem : co2Parameters) { // computing min, max and avg
                co2_value_total = parametersItem.getValue();
                if (co2_value_max < parametersItem.getValue()) {
                    co2_value_max = (int) parametersItem.getValue();
                } else if (co2_value_min > parametersItem.getValue()) {
                    co2_value_min = (int) parametersItem.getValue();
                }
            }

            double co2_value_average = co2_value_total / co2Parameters.size();
            avg_valueCO2.setText(co2_value_average + MEASUREMENT_TYPE);
            min_valueCO2.setText(co2_value_min + MEASUREMENT_TYPE);
            max_valueCO2.setText(co2_value_max + MEASUREMENT_TYPE);

            ArrayList<Entry> vals = new ArrayList<>(); // data entry for chart

            List<List<Parameter>> dataChunks = new ArrayList<>(); // list that holds parameters as parameter chunks
            Parameter parametersArray[] = co2Parameters.toArray(new Parameter[0]);
            Parameter chunkArray[];
            int initialRatio = co2Parameters.size() / 5;
            int ratio = co2Parameters.size() / 5;

            for (int i = 0; i < co2Parameters.size(); i += initialRatio) { // logic that gets parameters chunks and adding them to dataChunks list
                try {
                    chunkArray = Arrays.copyOfRange(parametersArray, i, ratio);
                    List<Parameter> chunkList = new ArrayList<Parameter>(Arrays.asList(chunkArray));
                    dataChunks.add(chunkList);
                    ratio += initialRatio;
                } catch (IndexOutOfBoundsException e) {
                    chunkArray = Arrays.copyOfRange(parametersArray, i, co2Parameters.size() - 1);
                    List<Parameter> chunkList = new ArrayList<Parameter>(Arrays.asList(chunkArray));
                    dataChunks.add(chunkList);
                    ratio += initialRatio;
                }
            }
            for (List<Parameter> parametersChunk : dataChunks) {
                parametersChunk.removeAll(Collections.singletonList(null)); // removing null objects from a chunk
                Parameter lastParameter = parametersChunk.get(parametersChunk.size() - 1); // last parameter from a chunk

                if (lastParameter.getSensorName().equals(SENSOR_NAME)) { // co2 check
                    String timestamp = lastParameter.getTimestamp();
                    String hoursNow = timestamp.charAt(11) + "" + timestamp.charAt(12); // string hour from the timestamp
                    String minutesNow = timestamp.charAt(14) + "" + timestamp.charAt(15); // string minutes from the timestamp

                    int hrNow = Integer.parseInt(hoursNow);
                    int minNow = Integer.parseInt(minutesNow);

                    long totalMinutes = minNow + hrNow * 60;
                    vals.add(new Entry(totalMinutes, Math.round((float) lastParameter.getValue())));
                }

            }

            LineDataSet dataSet = new LineDataSet(vals, "parameters");
            dataSet.setValueTextColor(getResources().getColor(R.color.Black));
            dataSet.setValueTextSize(12);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setDrawFilled(true);
            dataSet.setFillColor(getResources().getColor(R.color.Purple));
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);
            LineData data = new LineData(dataSets);
            data.setValueFormatter(new ValueFormatter());
            // line chart customization
            lineChartCO2.setData(data);
            configureLineChart();
        });

        CO2ViewModel.isLoading().observe(this, isLoading -> {
            int visibility = isLoading ? View.VISIBLE : View.INVISIBLE;
            if (mProgressBarCO2 != null) {
                mProgressBarCO2.setVisibility(visibility);
            }
        });
    }

    @SneakyThrows
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_co2, container, false);

        lineChartCO2 = view.findViewById(R.id.linechartCO2);
        mProgressBarCO2 = view.findViewById(R.id.progressBarCO2);
        time_fromCO2 = view.findViewById(R.id.dateAndTimeFromCO2);
        time_toCO2 = view.findViewById(R.id.dateAndTimeToCO2);
        updateButtonCO2 = view.findViewById(R.id.updateButtonCO2);
        recyclerView = view.findViewById(R.id.rvCO2);
        max_valueCO2 = view.findViewById(R.id.MAX_valueCO2);
        min_valueCO2 = view.findViewById(R.id.MIN_valueCO2);
        avg_valueCO2 = view.findViewById(R.id.AVG_valueCO2);
        currentValue = view.findViewById(R.id.co2CurrentValue);

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

        time_fromCO2.setText("0" + now_day + "-0" + now_month + "-" + now_year + " " + (now_hour - 2) + ":" + now_minute_string);
        time_toCO2.setText("0" + now_day + "-0" + now_month + "-" + now_year + " " + now_hour + ":" + now_minute_string);

        time_fromCO2.setOnClickListener(v -> showDateTimeDialogFrom(time_fromCO2));
        time_toCO2.setOnClickListener(v -> showDateTimeDialogTo(time_toCO2));

        updateButtonCO2.setOnClickListener(v -> {
            try {
                CO2ViewModel.updateParametersFromTo(time_fromCO2.getText().toString(), time_toCO2.getText().toString());
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
        CO2ViewModel.updateParametersFromTo(time_fromCO2.getText().toString(), time_toCO2.getText().toString());
    }

    private void initRecyclerView() {

        CO2ViewModel.getLastParameters().observe(this.getViewLifecycleOwner(), parameters -> {

            List<Parameter> params = new ArrayList<>();

            for (Parameter parameter : parameters) {
                if (parameter.getSensorName().equals(SENSOR_NAME)) {
                    parameter.setNew(true);
                    params.add(parameter);
                }
            }

            if (parametersAdapter != null) {
                List<Parameter> rwParams = parametersAdapter.getParametersRV();

                if (rwParams != null) {
                    for (Parameter parameter : rwParams) {
                        params.add(parameter);
                    }
                }
            }


            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            parametersAdapter = new ParametersAdapter(params, getActivity());
            recyclerView.setAdapter(parametersAdapter);

        });

        CO2ViewModel.getParameters().observe(this.getViewLifecycleOwner(), parameters -> {

            List<Parameter> CO2Parameters = new ArrayList<>();

            for (Parameter parameter : parameters) {
                if (parameter.getSensorName().equals("CO2")) {
                    CO2Parameters.add(parameter);
                }
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            parametersAdapter = new ParametersAdapter(CO2Parameters, getActivity());
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
        lineChartCO2.invalidate();
        lineChartCO2.setDragEnabled(true);
        lineChartCO2.setScaleEnabled(true);
        lineChartCO2.getDescription().setEnabled(false); // remove description
        lineChartCO2.setDrawBorders(false); // remove borders
        lineChartCO2.getLegend().setEnabled(false);   // Hide legend
        lineChartCO2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // positioning x axis
        lineChartCO2.getXAxis().setValueFormatter(new XAxisValueFormatter());
        lineChartCO2.getXAxis().setLabelCount(5, true); // set x axis label count
        lineChartCO2.getXAxis().setDrawGridLines(false); // remove background grid lines
        lineChartCO2.getAxisRight().setDrawGridLines(false);
        lineChartCO2.getAxisRight().setDrawLabels(true);
        lineChartCO2.getAxisRight().setDrawLabels(true);
        lineChartCO2.getAxisRight().setDrawGridLines(false);
        lineChartCO2.getAxisRight().setAxisMinimum(0); // set bounds
        lineChartCO2.getAxisRight().setAxisMaximum(100);
        lineChartCO2.getAxisRight().setXOffset(15); // padding
        lineChartCO2.getAxisLeft().setDrawLabels(true);
        lineChartCO2.getAxisLeft().setDrawLabels(true);
        lineChartCO2.getAxisLeft().setDrawGridLines(false);
        lineChartCO2.getAxisLeft().setAxisMinimum(0); // set bounds
        lineChartCO2.getAxisLeft().setAxisMaximum(100);
        lineChartCO2.getAxisLeft().setXOffset(15); // padding
    }

}
