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
public class Report {
    @SerializedName("avg_co2")
    private double avg_co2;
    @SerializedName("avg_humidity")
    private double avg_humidity;
    @SerializedName("avg_temperature")
    private double avg_temperature;
    @SerializedName("timestamp")
    private String timestamp;
}
