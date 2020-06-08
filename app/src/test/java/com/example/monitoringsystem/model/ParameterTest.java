package com.example.monitoringsystem.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParameterTest {

    private static final double DELTA = 1e-15;

    String sensorName = "SensorName";
    String unitType = "Unit";
    double value = 12.13;
    String timestamp = "Date";
    boolean isNew = false;
    Parameter testParameter = new Parameter(sensorName, unitType, value, timestamp, isNew);


    @Test
    public void setSensorName() {
        testParameter.setSensorName("Sensor");
        assertEquals("Sensor", testParameter.getSensorName());
    }

    @Test
    public void setUnitType() {
        testParameter.setUnitType("Uny");
        assertEquals("Uny", testParameter.getUnitType());
    }

    @Test
    public void setValue() {
        testParameter.setValue(15.420);
        assertEquals(15.420, testParameter.getValue(), DELTA);
    }

    @Test
    public void setTimestamp() {
        testParameter.setTimestamp("20.11.1999");
        assertEquals("20.11.1999", testParameter.getTimestamp());
    }

    @Test
    public void setNew() {
        testParameter.setNew(true);
        assertEquals(true, testParameter.isNew());
    }

    @Test
    public void getSensorName() {
        assertEquals("SensorName", testParameter.getSensorName());
    }

    @Test
    public void getUnitType() {
        assertEquals("Unit", testParameter.getUnitType());
    }

    @Test
    public void getValue() {
        assertEquals(12.13, testParameter.getValue(), DELTA);
    }

    @Test
    public void getTimestamp() {
        assertEquals("Date", testParameter.getTimestamp());
    }

    @Test
    public void isNew() {
        assertEquals(false, testParameter.isNew());
    }
}