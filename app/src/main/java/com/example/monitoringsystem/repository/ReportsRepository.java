package com.example.monitoringsystem.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.monitoringsystem.API.API;
import com.example.monitoringsystem.repository.Database.AppDao;
import com.example.monitoringsystem.repository.Database.AppDatabase;
import com.example.monitoringsystem.repository.Database.Report;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReportsRepository {

    private static final String TAG = "ReportsRepository";
    private AppDao dao;
    private static ReportsRepository instance;
    private API api;

    private MutableLiveData<List<Report>> reports;

    public ReportsRepository(Application app) {
        AppDatabase appDatabase = AppDatabase.getInstance(app);
        dao = appDatabase.appDao();
        reports = new MutableLiveData<>();
    }

    public static synchronized ReportsRepository getInstance(Application app) {
        if (instance == null) {
            instance = new ReportsRepository(app);
        }
        return instance;
    }

    public void insert(Report report) throws ExecutionException, InterruptedException {
        new ReportsRepository.InsertAsyncTask(dao).execute(report);

        List<Report> reports = getReportFromDB();
        this.reports.postValue(reports);

    }

    public List<Report> getReportFromDB() throws ExecutionException, InterruptedException {
        return new GetReport(dao).execute().get();
    }

    public LiveData<List<Report>> getReports() {
        return reports;
    }


    public void removeReport(int pos) throws ExecutionException, InterruptedException {
        List<Report> list = getReportFromDB();
        Report temp = list.get(pos);
        new RemoveItemAsync(dao).execute(temp);
    }

    public static class RemoveItemAsync extends AsyncTask<Report, Void, Void> {
        private AppDao dao;

        private RemoveItemAsync(AppDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Report... reports) {
            dao.removeReport(reports[0]);
            return null;
        }
    }


    public static class GetReport extends AsyncTask<Void, Void, List<Report>> {

        private AppDao dao;

        private GetReport(AppDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<Report> doInBackground(Void... voids) {
            return dao.getReports();
        }
    }



    private static class InsertAsyncTask extends AsyncTask<Report, Void, Void> {

        private AppDao dao;

        private InsertAsyncTask(AppDao appDao) {
            this.dao = appDao;
        }


        @Override
        protected Void doInBackground(Report... reports) {
            dao.insertReport(reports[0]);

            return null;
        }
    }

}
