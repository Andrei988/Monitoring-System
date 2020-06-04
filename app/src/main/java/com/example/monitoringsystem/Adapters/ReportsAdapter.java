package com.example.monitoringsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitoringsystem.R;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.VHolder> {

    double avg_CO2, avg_temperature, avg_humidity;
    String timestamp;
    Context context;

    public ReportsAdapter(Context ct, String tstamp, double avg_co2, double avg_hum, double avg_temp)
    {
        this.context = ct;
        this.timestamp = tstamp;
        this.avg_CO2 = avg_co2;
        this.avg_temperature = avg_temp;
        this.avg_humidity = avg_hum;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rw_reports_items, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        String tempString = String.valueOf(avg_temperature);
        String co2String = String.valueOf(avg_CO2);
        String humString = String.valueOf(avg_humidity);

        holder.textHum.setText("Humidity Value:");
        holder.textStamp.setText("Date:");
        holder.textCo2.setText("CO2 Value:");
        holder.textTemp.setText("Temperature Value:");
        holder.timestamp.setText(timestamp);
        holder.humValue.setText(humString);
        holder.co2Value.setText(co2String);
        holder.tempValue.setText(tempString);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class VHolder extends RecyclerView.ViewHolder
    {
      TextView co2Value, humValue, tempValue, timestamp, textStamp, textCo2, textTemp, textHum;
        public VHolder(@NonNull View itemView) {
            super(itemView);
            co2Value = itemView.findViewById(R.id.reportCO2Value);
            timestamp = itemView.findViewById(R.id.reportDateTextValue);
            humValue = itemView.findViewById(R.id.reportHumidityValue);
            tempValue = itemView.findViewById(R.id.reportTempValue);
            textStamp = itemView.findViewById(R.id.reportValueText);
            textCo2 = itemView.findViewById(R.id.co2ReportValueText);
            textTemp = itemView.findViewById(R.id.temperatureReportValueText);
            textHum = itemView.findViewById(R.id.humidityReportValueText);
        }
    }
}