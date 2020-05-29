package com.example.monitoringsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitoringsystem.R;
import com.example.monitoringsystem.model.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ParametersAdapter extends RecyclerView.Adapter<ParametersAdapter.ViewHolder> {

    private static final String TAG = "ParametersAdapter";
    private List<Parameters> parameters;
    private Context context;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rw_parameters_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (parameters != null && position <= parameters.size()) {
            holder.name.setText(parameters.get(position).getSensorName());
            holder.timestamp.setText(parameters.get(position).getTimestamp());
            int value = (int) parameters.get(position).getValue();
            String unitType = parameters.get(position).getUnitType();
            holder.value.setText(value + unitType);
        }
    }

    @Override
    public int getItemCount() {
        if (parameters == null) {
            parameters = new ArrayList<>();
            return 0;
        }
        return parameters.size();
    }

    public interface OnItemClickListener {
        void onRemoveClickListener(int position) throws ExecutionException, InterruptedException;
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    ;

    public ParametersAdapter(List<Parameters> parameters, Context context) {
        this.parameters = new ArrayList<>();
        this.parameters = parameters;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView value;
        TextView timestamp;

        ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.nameRV);
            value = itemView.findViewById(R.id.valueRV);
            timestamp = itemView.findViewById(R.id.timeStampRV);

        }
    }

}
