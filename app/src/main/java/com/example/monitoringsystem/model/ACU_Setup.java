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
public class ACU_Setup {
    @SerializedName("name")
    private String name;
    @SerializedName("co2")
    private double co2;
    @SerializedName("humidity")
    private double humidity;
    @SerializedName("temperature")
    private double temperature;
}
