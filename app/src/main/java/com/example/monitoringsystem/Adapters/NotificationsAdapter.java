package com.example.monitoringsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitoringsystem.R;
import com.example.monitoringsystem.repository.Database.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private static final String TAG = "NotificationsAdapter";
    private List<Notification> notifications;
    private Context context;
    private NotificationsAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rw_notifications_item, parent, false);
        return new NotificationsAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (notifications != null && position <= notifications.size()) {
            holder.date.setText(notifications.get(position).getDate() + " ");
            holder.title.setText(notifications.get(position).getTitle() + " ");
            holder.minValue.setText((int) notifications.get(position).getMinValue() + " " + notifications.get(position).getUnitType());
            holder.maxValue.setText((int) notifications.get(position).getMaxValue() + " " + notifications.get(position).getUnitType());
            holder.sensorValue.setText((int) notifications.get(position).getValue() + " " + notifications.get(position).getUnitType());
            if (notifications.get(position).getTitle().equals("CO2")) {
                holder.image.setBackgroundResource(R.drawable.co2_icon);
            } else if (notifications.get(position).getTitle().equals("Temperature")) {
                holder.image.setBackgroundResource(R.drawable.temperature);
            } else if (notifications.get(position).getTitle().equals("Humidity")) {
                holder.image.setBackgroundResource(R.drawable.humidity_icon);
            }
        }
    }

    public void addParameter(Notification notification) {
        notifications.add(notification);
    }

    public List<Notification> getParametersRV() {
        return notifications;
    }

    @Override
    public int getItemCount() {
        if (notifications == null) {
            notifications = new ArrayList<>();
            return 0;
        }
        return notifications.size();
    }



    public interface OnItemClickListener {
        void onRemoveClickListener(int position) throws ExecutionException, InterruptedException;
    }

    public void setOnClickListener(NotificationsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public NotificationsAdapter(List<Notification> notifications, Context context) {
        this.context = context;
        this.notifications = new ArrayList<>();
        this.notifications = notifications;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private TextView sensorValue;
        private TextView minValue;
        private TextView maxValue;
        private ImageView image;
        private Button removeButton;

        ViewHolder(@NonNull View itemView, NotificationsAdapter.OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.notifications_title);
            date = itemView.findViewById(R.id.notification_date);
            sensorValue = itemView.findViewById(R.id.notification_sensor_value);
            minValue = itemView.findViewById(R.id.notification_min_value);
            maxValue = itemView.findViewById(R.id.notification_max_value);
            image = itemView.findViewById(R.id.notifications_img);
            removeButton = itemView.findViewById(R.id.notifications_button);

            removeButton.setOnClickListener(v -> {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        try {
                            listener.onRemoveClickListener(pos);
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        notifications.remove(pos);
                    }
                }
            });
        }
    }

}
