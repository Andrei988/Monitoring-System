package com.example.monitoringsystem.model;

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
}
