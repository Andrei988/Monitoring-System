package com.example.monitoringsystem.ui.reports;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.monitoringsystem.repository.Database.Report;
import com.example.monitoringsystem.repository.ReportsRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReportsViewModel extends AndroidViewModel {

    private static final String TAG = "ReportsViewModel";

    private ReportsRepository reportsRepository;

    public ReportsViewModel(@NonNull Application application) {
        super(application);
        this.reportsRepository = ReportsRepository.getInstance(application);
    }

    public LiveData<List<Report>> getNotifications() throws ExecutionException, InterruptedException {
        return reportsRepository.getReports();
    }

    public void insertReport(Report report) throws ExecutionException, InterruptedException {
        reportsRepository.insert(report);
    }

    public void removeItem(int pos) throws ExecutionException, InterruptedException {
        reportsRepository.removeReport(pos);
    }
}
