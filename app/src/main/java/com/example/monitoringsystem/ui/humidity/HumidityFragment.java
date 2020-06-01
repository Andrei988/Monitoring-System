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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitoringsystem.Adapters.ParametersAdapter;
import com.example.monitoringsystem.R;
import com.example.monitoringsystem.model.Parameters;
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

import lombok.SneakyThrows;

public class HumidityFragment extends Fragment {

    private static final String TAG = "HumidityFragment";

    private HumidityViewModel humidityViewModel;
    private LineChart lineChart;
    private ProgressBar mProgressBar;
    private EditText time_from;
    private EditText time_to;
    private RecyclerView recyclerView;
    private ParametersAdapter parametersAdapter;
    private Button updateButton;
    private TextView max_value;
    private TextView min_value;
    private TextView avg_value;


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


        humidityViewModel.getParametersToday().observe(this, parameters -> { // parameters from dummy data

            int hum_value_total = 0;
            int hum_value_max = 0;
            int hum_value_min = 100;

            for(Parameters parametersItem : parameters) { // computing min, max and avg
                hum_value_total+=parametersItem.getValue();
                if(hum_value_max < parametersItem.getValue()) {
                    hum_value_max = (int) parametersItem.getValue();
                } else if(hum_value_min > parametersItem.getValue()) {
                    hum_value_min = (int) parametersItem.getValue();
                }
            }

            int hum_value_average = hum_value_total/parameters.size();
            avg_value.setText(hum_value_average + "%");
            min_value.setText(hum_value_min + "%");
            max_value.setText(hum_value_max + "%");

            ArrayList<Entry> vals = new ArrayList<>(); // data entry for chart

            List<List<Parameters>> dataChunks = new ArrayList<>(); // list that holds parameters as parameter chunks
            Parameters parametersArray[] = parameters.toArray(new Parameters[0]);
            Parameters chunkArray[];
            int initialRatio = parameters.size() / 5;
            int ratio = parameters.size() / 5;

            for (int i = 0; i < parameters.size(); i += initialRatio) { // logic that gets parameters chunks and adding them to dataChunks list
                try {
                    chunkArray = Arrays.copyOfRange(parametersArray, i, ratio);
                    List<Parameters> chunkList = new ArrayList<Parameters>(Arrays.asList(chunkArray));
                    dataChunks.add(chunkList);
                    ratio += initialRatio;
                } catch (IndexOutOfBoundsException e) {
                    chunkArray = Arrays.copyOfRange(parametersArray, i, parameters.size() - 1);
                    List<Parameters> chunkList = new ArrayList<Parameters>(Arrays.asList(chunkArray));
                    dataChunks.add(chunkList);
                    ratio += initialRatio;
                }
            }
            for (List<Parameters> parametersChunk : dataChunks) {
                parametersChunk.removeAll(Collections.singletonList(null)); // removing null objects from a chunk
                Parameters lastParameter = parametersChunk.get(parametersChunk.size() - 1); // last parameter from a chunk

                String timestamp = lastParameter.getTimestamp();
                String hoursNow = timestamp.charAt(11) + "" + timestamp.charAt(12); // string hour from the timestamp
                String minutesNow = timestamp.charAt(14) + "" + timestamp.charAt(15); // string minutes from the timestamp

                int hrNow = Integer.parseInt(hoursNow);
                int minNow = Integer.parseInt(minutesNow);

                long totalMinutes = minNow + hrNow * 60;

                if (lastParameter.getSensorName().equals("Humidity")) { // humidity check
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
        mProgressBar = view.findViewById(R.id.progressBarHumidity);
        time_from = view.findViewById(R.id.dateAndTimeFrom);
        time_to = view.findViewById(R.id.dateAndTimeTo);
        updateButton = view.findViewById(R.id.updateButton);
        recyclerView = view.findViewById(R.id.rv);
        max_value = view.findViewById(R.id.MAX_value);
        min_value = view.findViewById(R.id.MIN_value);
        avg_value = view.findViewById(R.id.AVG_value);

        Calendar now = Calendar.getInstance(); // setting current hour as "time to" and "time from" is time_to - 2
        int now_hour = now.get(Calendar.HOUR_OF_DAY);
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

        time_from.setText(now_day + "-0" + now_month + "-" + now_year + " " + (now_hour - 2) + ":" + now_minute_string);
        time_to.setText(now_day + "-0" + now_month + "-" + now_year + " " + now_hour + ":" + now_minute_string);

        time_from.setOnClickListener(v -> showDateTimeDialogFrom(time_from));
        time_to.setOnClickListener(v -> showDateTimeDialogTo(time_to));

        updateButton.setOnClickListener(v -> {
            try {
                humidityViewModel.updateParametersFromTo(time_from.getText().toString(), time_to.getText().toString());
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
        humidityViewModel.updateParametersFromTo(time_from.getText().toString(), time_to.getText().toString());
    }

    private void initRecyclerView() {
        humidityViewModel.getParametersToday().observe(this.getViewLifecycleOwner(), parameters -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            parametersAdapter = new ParametersAdapter(humidityViewModel.getParametersToday().getValue(), getActivity());
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
        lineChart.invalidate();
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.getDescription().setEnabled(false); // remove description
        lineChart.setDrawBorders(false); // remove borders
        lineChart.getLegend().setEnabled(false);   // Hide legend
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // positioning x axis
        lineChart.getXAxis().setValueFormatter(new XAxisValueFormatter());
        lineChart.getXAxis().setLabelCount(5, true); // set x axis label count
        lineChart.getXAxis().setDrawGridLines(false); // remove background grid lines
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawLabels(true);
        lineChart.getAxisRight().setDrawLabels(true);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setAxisMinimum(0); // set bounds
        lineChart.getAxisRight().setAxisMaximum(100);
        lineChart.getAxisRight().setXOffset(15); // padding
        lineChart.getAxisLeft().setDrawLabels(true);
        lineChart.getAxisLeft().setDrawLabels(true);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisLeft().setAxisMinimum(0); // set bounds
        lineChart.getAxisLeft().setAxisMaximum(100);
        lineChart.getAxisLeft().setXOffset(15); // padding

    }


}
