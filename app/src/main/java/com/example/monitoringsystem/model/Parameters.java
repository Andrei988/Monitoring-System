package com.example.monitoringsystem.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

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
public class Parameters {
    @SerializedName("sensorName")
    private String sensorName;
    @SerializedName("unitType")
    private String unitType;
    @SerializedName("value")
    private double value;
    @SerializedName("timestamp")
    private long timestamp;
}
