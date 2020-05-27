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
public class DefaultValue {
    @SerializedName("parameters")
    private Parameters parameters;
    @SerializedName("value")
    private int value;
}
