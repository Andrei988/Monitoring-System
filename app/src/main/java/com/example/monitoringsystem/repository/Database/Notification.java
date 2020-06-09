package com.example.monitoringsystem.repository.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.ToString;

@ToString

@Entity(tableName = "notifications_table")
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String title;
    private double value;
    private double minValue;
    private double maxValue;
    private String unitType;

    public Notification(String date, String title, double value, double minValue, double maxValue, String unitType) {
        this.date = date;
        this.title = title;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unitType = unitType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
}
