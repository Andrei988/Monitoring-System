package com.example.monitoringsystem.model;

import com.google.gson.annotations.SerializedName;

import lombok.ToString;


@ToString
public class Parameter {

    @SerializedName("sensorName")
    private String sensorName;
    @SerializedName("unitType")
    private String unitType;
    @SerializedName("value")
    private double value;
    @SerializedName("timestamp")
    private String timestamp;

    private boolean isNew;

    public Parameter(String humidity, String s, int nextInt, String s1) {
        sensorName = humidity;
        unitType = s;
        value = nextInt;
        timestamp = s1;
        isNew = false;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
