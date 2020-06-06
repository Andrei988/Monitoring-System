package com.example.monitoringsystem.repository.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity(tableName = "report_table")
public class Report {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double current_co2;
    private double current_humidity;
    private double current_temperature;
    private String timestamp;

    public Report (double co2_curr, double hum_curr, double temp_curr, String tstamp)
    {
        this.current_co2 = co2_curr;
        this.current_humidity = hum_curr;
        this.current_temperature= temp_curr;
        this.timestamp = tstamp;
    }
}
