package com.example.monitoringsystem;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monitoringsystem.Adapters.ReportsAdapter;

import java.util.Calendar;

public class ReportsActivity extends AppCompatActivity {

    private RecyclerView rvReport;
    private Button reportBtn;

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
   }

}
