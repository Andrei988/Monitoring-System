package com.example.monitoringsystem.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class XAxisValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String stringValue = String.valueOf(value);

        int minutes = (int) value % 60;
        int hours = ((int) value - (int) value % 60) / 60;

        String formattedValue = "";

        if (minutes < 10 && hours < 10) {
            formattedValue = "0" + hours + ":0" + minutes;
        } else if (minutes > 10 && hours > 10) {
            formattedValue = +hours + ":" + minutes;
        } else if (minutes > 10 && hours < 10) {
            formattedValue = "0" + hours + ":" + minutes;
        } else if (minutes < 10 && hours > 10) {
            formattedValue = +hours + ":0" + minutes;
        }

        return formattedValue;
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
