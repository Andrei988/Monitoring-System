package com.example.monitoringsystem.ui.reports;

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

import com.example.monitoringsystem.Adapters.ReportsAdapter;
import com.example.monitoringsystem.R;

import lombok.SneakyThrows;

public class ReportsFragment extends Fragment {

    private ReportsViewModel reportsViewModel;

    private RecyclerView recyclerView;
    private ReportsAdapter reportsAdapter;

    @SneakyThrows
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        reportsViewModel.getReports().observe(this, reports -> {

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            reportsAdapter = new ReportsAdapter(reports, getActivity());
            recyclerView.setAdapter(reportsAdapter);

            reportsAdapter.setOnClickListener(position -> {
                reportsViewModel.removeItem(position);
                Toast.makeText(getContext(), "Report removed", Toast.LENGTH_SHORT).show();
                reportsAdapter.notifyDataSetChanged();
            });
        });
    }

    @SneakyThrows
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        recyclerView = view.findViewById(R.id.rvReports);

        return view;
    }

}
