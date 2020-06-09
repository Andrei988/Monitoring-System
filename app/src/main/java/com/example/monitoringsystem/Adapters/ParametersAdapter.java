package com.example.monitoringsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitoringsystem.R;
import com.example.monitoringsystem.model.Parameter;

import java.util.ArrayList;
import java.util.List;

public class ParametersAdapter extends RecyclerView.Adapter<ParametersAdapter.ViewHolder> {

    private static final String TAG = "ParametersAdapter";
    private List<Parameter> parameters;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rw_parameters_item, parent, false);
        return new ViewHolder(view);
    }

    public List<Parameter> getParametersRV() {
        return parameters;
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
            if (parameters.get(position).isNew())
                holder.card.setCardBackgroundColor(Color.LTGRAY);
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

    public ParametersAdapter(List<Parameter> parameters, Context context) {
        this.parameters = new ArrayList<>();
        this.parameters = parameters;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView value;
        private TextView timestamp;
        private CardView card;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameRV);
            value = itemView.findViewById(R.id.valueRV);
            timestamp = itemView.findViewById(R.id.timeStampRV);
            card = itemView.findViewById(R.id.card);
        }
    }

}
