package com.example.monitoringsystem.repository.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.ToString;

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

    public Report() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCurrent_co2() {
        return current_co2;
    }

    public void setCurrent_co2(double current_co2) {
        this.current_co2 = current_co2;
    }

    public double getCurrent_humidity() {
        return current_humidity;
    }

    public void setCurrent_humidity(double current_humidity) {
        this.current_humidity = current_humidity;
    }

    public double getCurrent_temperature() {
        return current_temperature;
    }

    public void setCurrent_temperature(double current_temperature) {
        this.current_temperature = current_temperature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
