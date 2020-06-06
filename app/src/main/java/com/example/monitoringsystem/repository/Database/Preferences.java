package com.example.monitoringsystem.repository.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "preferences_table")
public class Preferences {
    @PrimaryKey
    @NotNull
    private String username;
    private int minCo2;
    private int maxCo2;
    private int minHumidity;
    private int maxHumidity;
    private int minTemp;
    private int maxTemp;

    public Preferences(String username, int minCo2, int maxCo2, int minHumidity, int maxHumidity, int minTemp, int maxTemp) {
        this.username = username;
        this.minCo2 = minCo2;
        this.maxCo2 = maxCo2;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public Preferences(){

    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMinCo2() {
        return minCo2;
    }

    public void setMinCo2(int minCo2) {
        this.minCo2 = minCo2;
    }

    public int getMaxCo2() {
        return maxCo2;
    }

    public void setMaxCo2(int maxCo2) {
        this.maxCo2 = maxCo2;
    }

    public int getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(int minHumidity) {
        this.minHumidity = minHumidity;
    }

    public int getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(int maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }
}
