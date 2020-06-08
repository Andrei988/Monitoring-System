package com.example.monitoringsystem.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ComputedValuesTest {

    private static final double DELTA = 1e-15;

        double co2_min = 1;
        double co2_max = 100;
        double co2_default = 50;
        double temp_min = 1;
        double temp_max = 100;
        double temp_default = 50;
        double hum_min = 1;
        double hum_max = 100;
        double hum_default = 50;
        ComputedValues computedValues = new ComputedValues(co2_min, co2_max, co2_default, temp_min, temp_max, temp_default, hum_min, hum_max, hum_default);

    @Test
    public void setCo2_min() {
        computedValues.setCo2_min(2);
        assertEquals(2, computedValues.getCo2_min(), DELTA);
    }

    @Test
    public void setCo2_max() {
        computedValues.setCo2_max(50.21);
        assertEquals(50.21, computedValues.getCo2_max(), DELTA);
    }

    @Test
    public void setCo2_default() {
        computedValues.setCo2_default(25.212);
        assertEquals(25.212, computedValues.getCo2_default(), DELTA);
    }

    @Test
    public void setHumidity_min() {
        computedValues.setHumidity_min(0);
        assertEquals(0, computedValues.getHumidity_min(), DELTA);
    }

    @Test
    public void setHumidity_max() {
        computedValues.setCo2_max(75.025);
        assertEquals(75.025, computedValues.getCo2_max(), DELTA);
    }

    @Test
    public void setHumidity_default() {
        computedValues.setHumidity_default(42.0);
        assertEquals(42.0, computedValues.getHumidity_default(), DELTA);
    }

    @Test
    public void setTemperature_min() {
        computedValues.setTemperature_min(0);
        assertEquals(0, computedValues.getTemperature_min(), DELTA);
    }

    @Test
    public void setTemperature_max() {
        computedValues.setTemperature_max(75.025);
        assertEquals(75.025, computedValues.getTemperature_max(), DELTA);
    }

    @Test
    public void setTemperature_default() {
        computedValues.setTemperature_default(42.0);
        assertEquals(42.0, computedValues.getTemperature_default(), DELTA);
    }

    @Test
    public void getCo2_min() {
        assertEquals(1, computedValues.getCo2_min(), DELTA);
    }

    @Test
    public void getCo2_max() {
        assertEquals(100, computedValues.getCo2_max(), DELTA);
    }

    @Test
    public void getCo2_default() {
        assertEquals(50, computedValues.getCo2_default(), DELTA);
    }

    @Test
    public void getHumidity_min() {
        assertEquals(1, computedValues.getHumidity_min(), DELTA);
    }

    @Test
    public void getHumidity_max() {
        assertEquals(100, computedValues.getHumidity_max(), DELTA);
    }

    @Test
    public void getHumidity_default() {
        assertEquals(50, computedValues.getHumidity_default(), DELTA);
    }

    @Test
    public void getTemperature_min() {
        assertEquals(1, computedValues.getTemperature_min(), DELTA);
    }

    @Test
    public void getTemperature_max() {
        assertEquals(100, computedValues.getTemperature_max(), DELTA);
    }

    @Test
    public void getTemperature_default() {
        assertEquals(50, computedValues.getTemperature_default(), DELTA);
    }
}