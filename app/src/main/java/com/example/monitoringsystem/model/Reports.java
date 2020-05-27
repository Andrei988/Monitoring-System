package com.example.monitoringsystem.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

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
public class Reports {
    @SerializedName("period")
    private Date period;
    @SerializedName("parameters")
    private List<Parameters> parameters;
}
