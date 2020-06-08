package com.example.monitoringsystem.repository.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

@Entity(tableName = "notifications_table")
public class Notification {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String title;
    private double value;
    private double minValue;
    private double maxValue;
    private String unitType;

    public Notification(String date, String title, double value, double minValue, double maxValue, String unitType) {
        this.date = date;
        this.title = title;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unitType = unitType;
    }
}
