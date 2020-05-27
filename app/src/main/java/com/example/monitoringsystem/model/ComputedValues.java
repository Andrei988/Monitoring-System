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
}
