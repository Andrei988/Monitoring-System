package com.example.monitoringsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitoringsystem.R;
import com.example.monitoringsystem.repository.Database.Report;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.VHolder> {

    private static final String TAG = "ReportsAdapter";
    private List<Report> reports;
    private Context context;
    private ReportsAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public ReportsAdapter.VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rw_reports_items, parent, false);
        return new ReportsAdapter.VHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        if (reports != null && position <= reports.size())
        {
            holder.co2Value.setText(reports.get(position).getCurrent_co2() + " ");
            holder.humValue.setText(reports.get(position).getCurrent_humidity() + " ");
            holder.tempValue.setText(reports.get(position).getCurrent_temperature() + " ");
            holder.timestamp.setText(reports.get(position).getTimestamp()+ " ");
        }
    }

    @Override
    public int getItemCount() {
        if (reports == null) {
            reports = new ArrayList<>();
            return 0;
        }
        return reports.size();
    }

    public interface OnItemClickListener {
        void onRemoveClickListener(int position) throws ExecutionException, InterruptedException;
    }


    public void setOnClickListener(ReportsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public ReportsAdapter(List<Report> reports, Context context) {
        this.context = context;
        this.reports = new ArrayList<>();
        this.reports = reports;
    }

    class VHolder extends RecyclerView.ViewHolder
    {
      private TextView co2Value, humValue, tempValue, timestamp;
      private Button deleteBtn;
        public VHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            co2Value = itemView.findViewById(R.id.reportCO2Value);
            timestamp = itemView.findViewById(R.id.reportDateTextValue);
            humValue = itemView.findViewById(R.id.reportHumidityValue);
            tempValue = itemView.findViewById(R.id.reportTempValue);
            deleteBtn = itemView.findViewById(R.id.buttonDeleteReport);

            deleteBtn.setOnClickListener(v ->
            {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        try
                        {
                            listener.onRemoveClickListener(pos);
                        }
                        catch (ExecutionException | InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        reports.remove(pos);
                    }
                }
            });
        }
    }
}