package com.example.monitoringsystem.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitoringsystem.Adapters.NotificationsAdapter;
import com.example.monitoringsystem.R;

import java.util.concurrent.ExecutionException;

import lombok.SneakyThrows;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private RecyclerView recyclerView;
    private NotificationsAdapter notificationsAdapter;

    @SneakyThrows
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        try {
            notificationsViewModel.getNotifications().observe(this, notifications -> {

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                notificationsAdapter = new NotificationsAdapter(notifications);
                recyclerView.setAdapter(notificationsAdapter);

                notificationsAdapter.setOnClickListener(position ->
                {
                    notificationsViewModel.removeItem(position);
                    Toast.makeText(getContext(), "Item removed", Toast.LENGTH_SHORT).show();
                    notificationsAdapter.notifyDataSetChanged();
                });

            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = view.findViewById(R.id.rv_notifications);

        return view;
    }

}
