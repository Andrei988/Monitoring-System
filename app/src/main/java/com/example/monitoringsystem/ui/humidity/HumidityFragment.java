package com.example.monitoringsystem.ui.humidity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.monitoringsystem.R;
import com.example.monitoringsystem.model.Parameters;
import com.example.monitoringsystem.utils.ValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import lombok.SneakyThrows;

public class HumidityFragment extends Fragment {

    private static final String TAG = "HumidityFragment";

    private HumidityViewModel humidityViewModel;
    private LineChart lineChart;
    private ProgressBar mProgressBar;
    private EditText time_from;
    private EditText time_to;
    private Button updateButton;


    public static HumidityFragment newInstance() {
        return new HumidityFragment();
    }

    @SneakyThrows
    @SuppressLint("SetTextI18n")
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        //humidityViewModel.updateParametersToday(); // TODO: this method is for api - method below is for testing (dummy data)
        humidityViewModel.updateParametersTodayDummyData(10); // generating values from 00:00 till 12:00





        humidityViewModel.getParametersToday().observe(this, parameters -> {

            ArrayList<Entry> vals = new ArrayList<>();

            if (parameters.size() > 60)
            { // show hourly

            }
            else if (parameters.size() < 60)
            { // show every 5 min for
                for (Parameters parametersItem : parameters) { // insert data from parameter list to Data Entry list
                    String timestamp = parametersItem.getTimestamp();
                    String hr_now_string = timestamp.charAt(0) + "" + timestamp.charAt(0); // string hour from the timestamp
                    int hr = Integer.parseInt(hr_now_string); // converting to integer
                    if (parametersItem.getSensorName().equals("humidity")) {
                        vals.add(new Entry(hr, Math.round((float) parametersItem.getValue())));
                    }
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
            lineChart.setData(data);

            configureLineChart();

        });

        humidityViewModel.isLoading().observe(this, isLoading -> {
            int visibility = isLoading ? View.VISIBLE : View.INVISIBLE;
            if (mProgressBar != null) {
                mProgressBar.setVisibility(visibility);
            }
        });
    }

    @SneakyThrows
    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_humidity, container, false);
        lineChart = view.findViewById(R.id.linechart);
        mProgressBar = view.findViewById(R.id.progressBar);
        time_from = view.findViewById(R.id.dateAndTimeFrom);
        time_to = view.findViewById(R.id.dateAndTimeTo);
        updateButton = view.findViewById(R.id.updateButton);

        Calendar now = Calendar.getInstance();
        int now_hour = now.get(Calendar.HOUR_OF_DAY);
        int now_minute = now.get(Calendar.MINUTE);
        int now_day = now.get(Calendar.DAY_OF_MONTH);
        int now_month = now.get(Calendar.MONTH) + 1; // it gives month - 1 dont know why
        int now_year = now.get(Calendar.YEAR);

        if(now_minute % 5 < 3) { // rounding minutes
            now_minute = now_minute - (now_minute % 5);
        } else {
            now_minute = now_minute - (now_minute % 5) + 5;
        }

        time_from.setText(now_day + "-0" + now_month + "-" + now_year + " " + (now_hour - 2) + ":" + now_minute);
        time_to.setText(now_day + "-0" + now_month + "-" + now_year + " " + now_hour + ":" + now_minute);
        humidityViewModel.updateParametersFromTo(time_from.getText().toString(), time_to.getText().toString());

        time_from.setOnClickListener(v -> showDateTimeDialogFrom(time_from));
        time_to.setOnClickListener(v -> showDateTimeDialogTo(time_to));
        updateButton.setOnClickListener(v -> {
            try {
                humidityViewModel.updateParametersFromTo(time_from.toString(), time_to.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        return view;
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

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy HH:mm");

                time_from.setText(simpleDateFormat.format(calendar.getTime()));
            };

            new TimePickerDialog(requireContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();

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

                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

                time_to.setText(simpleDateFormat.format(calendar.getTime()));
            };

            new TimePickerDialog(requireContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();

        };
        new DatePickerDialog(requireContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @SuppressWarnings("deprecation")
    private void configureLineChart() {
        lineChart.invalidate();
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.getAxisRight().setDrawLabels(false); // remove y axis labels
        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getDescription().setEnabled(false); // remove description
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // positioning x axis
        lineChart.getXAxis().setTextColor(getResources().getColor(R.color.White)); // text color
        lineChart.getXAxis().setTextSize(12); // text size
        lineChart.setDrawBorders(false); // remove borders
        lineChart.getXAxis().setDrawGridLines(false); // remove background grid lines
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getXAxis().setLabelCount(6, true); // set x axis label count
        lineChart.getAxisLeft().setAxisMinimum(-2); // set bounds
        lineChart.getAxisLeft().setAxisMaximum(25);
        lineChart.getLegend().setEnabled(false);   // Hide legend
    }


}
