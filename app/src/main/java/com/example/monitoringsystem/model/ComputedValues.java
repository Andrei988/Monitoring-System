package com.example.monitoringsystem.model;

import com.google.gson.annotations.SerializedName;

import lombok.ToString;

@ToString
public class ComputedValues {

    @SerializedName("co2_min")
    private double co2_min;

    @SerializedName("co2_max")
    private double co2_max;

    @SerializedName("co2_default")
    private double co2_default;

    @SerializedName("humidity_min")
    private double humidity_min;

    @SerializedName("humidity_max")
    private double humidity_max;

    @SerializedName("humidity_default")
    private double humidity_default;

    @SerializedName("temperature_min")
    private double temperature_min;

    @SerializedName("temperature_max")
    private double temperature_max;

    @SerializedName("temperature_default")
    private double temperature_default;

    public ComputedValues(double co2_min, double co2_max, double co2_default, double humidity_min, double humidity_max, double humidity_default, double temperature_min, double temperature_max, double temperature_default) {
        this.co2_min = co2_min;
        this.co2_max = co2_max;
        this.co2_default = co2_default;
        this.humidity_min = humidity_min;
        this.humidity_max = humidity_max;
        this.humidity_default = humidity_default;
        this.temperature_min = temperature_min;
        this.temperature_max = temperature_max;
        this.temperature_default = temperature_default;
    }

    public double getCo2_min() {
        return co2_min;
    }

    public void setCo2_min(double co2_min) {
        this.co2_min = co2_min;
    }

    public double getCo2_max() {
        return co2_max;
    }

    public void setCo2_max(double co2_max) {
        this.co2_max = co2_max;
    }

    public double getCo2_default() {
        return co2_default;
    }

    public void setCo2_default(double co2_default) {
        this.co2_default = co2_default;
    }

    public double getHumidity_min() {
        return humidity_min;
    }

    public void setHumidity_min(double humidity_min) {
        this.humidity_min = humidity_min;
    }

    public double getHumidity_max() {
        return humidity_max;
    }

    public void setHumidity_max(double humidity_max) {
        this.humidity_max = humidity_max;
    }

    public double getHumidity_default() {
        return humidity_default;
    }

    public void setHumidity_default(double humidity_default) {
        this.humidity_default = humidity_default;
    }

    public double getTemperature_min() {
        return temperature_min;
    }

    public void setTemperature_min(double temperature_min) {
        this.temperature_min = temperature_min;
    }

    public double getTemperature_max() {
        return temperature_max;
    }

    public void setTemperature_max(double temperature_max) {
        this.temperature_max = temperature_max;
    }

    public double getTemperature_default() {
        return temperature_default;
    }

    public void setTemperature_default(double temperature_default) {
        this.temperature_default = temperature_default;
    }
}
