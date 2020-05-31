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
public class Parameters {
    @SerializedName("sensorName")
    private String sensorName;
    @SerializedName("unitType")
    private String unitType;
    @SerializedName("value")
    private double value;
    @SerializedName("timestamp")
    private String timestamp; // TODO: tell database guys to send timestamp as STRING
}
