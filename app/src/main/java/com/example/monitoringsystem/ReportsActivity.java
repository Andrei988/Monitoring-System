package com.example.monitoringsystem;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitoringsystem.API.API;
import com.example.monitoringsystem.Adapters.ReportsAdapter;
import com.example.monitoringsystem.model.Report;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsActivity extends AppCompatActivity {

    private RecyclerView rvReport;
    private Button reportBtn;
    private API api;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        String timestamp = Calendar.getInstance().getTime().toString();;
        double co2_avg, hum_avg, temp_avg;

        co2_avg = 0;
        hum_avg = 0;
        temp_avg = 0;

        rvReport = findViewById(R.id.rvReports);
        reportBtn = findViewById(R.id.generateReport);

        ReportsAdapter reportsAdapter = new ReportsAdapter(this, timestamp, co2_avg, hum_avg, temp_avg);
        rvReport.setAdapter(reportsAdapter);
        rvReport.setLayoutManager(new LinearLayoutManager(this));

        api.createReport(co2_avg,hum_avg,temp_avg,timestamp);
    }

    private void createPost()
    {
        Report reportTest = new Report(12, 12, 12, "2020-06-05 10:31:11");
        Call<Report> call = api.createReport(reportTest);

        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response)
            {
                if(!response.isSuccessful())
                {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Report> call, Throwable t)
            {
                Context context = getApplicationContext();
                Toast.makeText(context, "Error Code: " + t.getMessage(),Toast.LENGTH_SHORT).show();;
            }
        });
    }

}