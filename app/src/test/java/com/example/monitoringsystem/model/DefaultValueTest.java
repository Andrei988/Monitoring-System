package com.example.monitoringsystem.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultValueTest {

    Parameter parameter = new Parameter( "a", "b", 123.456, "c", false);
    int value = 15;
    DefaultValue defaultValueTest = new DefaultValue(parameter, value);


    @Test
    public void setParameters() {
        Parameter parameterSet = new Parameter("", "", 0, "", true);
        defaultValueTest.setParameters(parameterSet);
        assertEquals(parameterSet, defaultValueTest.getParameters());
    }

    @Test
    public void setValue() {
        defaultValueTest.setValue(0);
        assertEquals(0, defaultValueTest.getValue());
    }

    @Test
    public void getParameters() {
        assertEquals(parameter, defaultValueTest.getParameters());
    }

    @Test
    public void getValue() {
        assertEquals(value, defaultValueTest.getValue());
    }
}