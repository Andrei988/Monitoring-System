package com.example.monitoringsystem.repository;

import com.example.monitoringsystem.model.Parameter;
import com.example.monitoringsystem.repository.Database.Report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimerTask;

public class DummyData {

    public static String generateTimeStamp(int day, int month, int year, int hour, int minutes) {
        return day+"-"+month+"-"+year+" "+hour+":"+minutes;
    }

    public static List<Parameter> updateParametersFromToDummyData(String from, String to) throws ParseException {

        List<Parameter> dummyData = new ArrayList<>();

        String mf = "" + from.charAt(14) + from.charAt(15);
        int minute_from = Integer.parseInt(mf);
        String hf = "" + from.charAt(11) + from.charAt(12);
        int hour_from = Integer.parseInt(hf);
        String df = "" + from.charAt(0) + from.charAt(1);
        int day_from = Integer.parseInt(df);
        String mof = "" + from.charAt(3) + from.charAt(4);
        int month_from = Integer.parseInt(mof);
        String yf = "2020";
        int year_from = Integer.parseInt(yf);

        String mt = "" + to.charAt(14) + to.charAt(15);
        int minute_to = Integer.parseInt(mt);
        String ht = "" + to.charAt(11) + to.charAt(12);
        int hour_to = Integer.parseInt(ht);
        String dt = "" + to.charAt(0) + to.charAt(1);
        int day_to = Integer.parseInt(dt);
        String mot = "" + to.charAt(3) + to.charAt(4);
        int month_to = Integer.parseInt(mot);
        String yt = "2020";
        int year_to = Integer.parseInt(yt);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        Date firstDate = sdf.parse(day_from + "/" + month_from + "/" + year_from + " " + hour_from + ":" + minute_from);
        Date secondDate = sdf.parse(day_to + "/" + month_to + "/" + year_to + " " + hour_to + ":" + minute_to);

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diffInMinutes =
                diffInMillies / 60000;

        int iterations = (int) (diffInMinutes / 5);

        for (int i = 0; i <= iterations; i++) {

            if (minute_from == 60) {
                mf = "00";
                minute_from = 0;
                hour_from++;
                if (hour_from < 10) {
                    hf = "0" + hour_from;
                } else {
                    hf = String.valueOf(hour_from);
                }
            } else if (hour_from == 24) {
                hour_from = 0;
                hf = "0" + hour_from;
                minute_from = 0;
                mf = "0" + minute_from;
                day_from++;
                if (day_from < 10) {
                    df = "0" + day_from;
                } else {
                    df = String.valueOf(day_from);
                }

            } else if (day_from == 30) {
                hour_from = 0;
                hf = "0" + hour_from;
                minute_from = 0;
                mf = "0" + minute_from;
                day_from = 1;
                df = "0" + day_from;
                month_from++;
                if (month_from < 10) {
                    mof = "0" + month_from;
                } else {
                    mof = String.valueOf(month_from);
                }
            } else if (month_from == 13) {
                hour_from = 0;
                hf = "0" + hour_from;
                minute_from = 0;
                mf = "0" + minute_from;
                day_from = 1;
                df = "0" + day_from;
                month_from = 1;
                mf = "0" + month_from;
                year_from++;
            }

            String timestamp_sb = "";

            if (minute_from < 10) {
                timestamp_sb = df + "-" + mof + "-" + yf + " " + hf + ":0" + minute_from;

            } else {
                timestamp_sb = df + "-" + mof + "-" + yf + " " + hf + ":" + minute_from;
            }

            Random random = new Random();
            dummyData.add(new Parameter("Humidity", "%", random.nextInt(100) , timestamp_sb));
            dummyData.add(new Parameter("CO2", "PPM", random.nextInt(100) , timestamp_sb));
            dummyData.add(new Parameter("Temperature", "C", random.nextInt(100) , timestamp_sb));

            minute_from += 5;
        }

        return dummyData;


    }

    public static void liveData(){
        Random random = new Random();

        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("Humidity", "%", random.nextInt(100), "06-05-2020 18:20"));
        parameters.add(new Parameter("Temperature", "C", random.nextInt(30), "06-05-2020 18:20"));
        parameters.add(new Parameter("CO2", "PPM", random.nextInt(50), "06-05-2020 18:20"));

        ParametersRepository.lastParameters.postValue(parameters);
    }

    public static List<Report>  updateReportsFromToDummyData(String from, String to) throws ParseException {

        List<Report> dummyData = new ArrayList<>();

        String mf = "" + from.charAt(14) + from.charAt(15);
        int minute_from = Integer.parseInt(mf);
        String hf = "" + from.charAt(11) + from.charAt(12);
        int hour_from = Integer.parseInt(hf);
        String df = "" + from.charAt(0) + from.charAt(1);
        int day_from = Integer.parseInt(df);
        String mof = "" + from.charAt(3) + from.charAt(4);
        int month_from = Integer.parseInt(mof);
        String yf = "2020";
        int year_from = Integer.parseInt(yf);

        String mt = "" + to.charAt(14) + to.charAt(15);
        int minute_to = Integer.parseInt(mt);
        String ht = "" + to.charAt(11) + to.charAt(12);
        int hour_to = Integer.parseInt(ht);
        String dt = "" + to.charAt(0) + to.charAt(1);
        int day_to = Integer.parseInt(dt);
        String mot = "" + to.charAt(3) + to.charAt(4);
        int month_to = Integer.parseInt(mot);
        String yt = "2020";
        int year_to = Integer.parseInt(yt);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
        Date firstDate = sdf.parse(day_from + "/" + month_from + "/" + year_from + " " + hour_from + ":" + minute_from);
        Date secondDate = sdf.parse(day_to + "/" + month_to + "/" + year_to + " " + hour_to + ":" + minute_to);

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diffInMinutes =
                diffInMillies / 60000;

        int iterations = (int) (diffInMinutes / 5);

        for (int i = 0; i <= iterations; i++) {

            if (minute_from == 60) {
                mf = "00";
                minute_from = 0;
                hour_from++;
                if (hour_from < 10) {
                    hf = "0" + hour_from;
                } else {
                    hf = String.valueOf(hour_from);
                }
            } else if (hour_from == 24) {
                hour_from = 0;
                hf = "0" + hour_from;
                minute_from = 0;
                mf = "0" + minute_from;
                day_from++;
                if (day_from < 10) {
                    df = "0" + day_from;
                } else {
                    df = String.valueOf(day_from);
                }

            } else if (day_from == 30) {
                hour_from = 0;
                hf = "0" + hour_from;
                minute_from = 0;
                mf = "0" + minute_from;
                day_from = 1;
                df = "0" + day_from;
                month_from++;
                if (month_from < 10) {
                    mof = "0" + month_from;
                } else {
                    mof = String.valueOf(month_from);
                }
            } else if (month_from == 13) {
                hour_from = 0;
                hf = "0" + hour_from;
                minute_from = 0;
                mf = "0" + minute_from;
                day_from = 1;
                df = "0" + day_from;
                month_from = 1;
                mf = "0" + month_from;
                year_from++;
            }

            String timestamp_sb = "";

            if (minute_from < 10) {
                timestamp_sb = df + "-" + mof + "-" + yf + " " + hf + ":0" + minute_from;

            } else {
                timestamp_sb = df + "-" + mof + "-" + yf + " " + hf + ":" + minute_from;
            }

            Random random = new Random();
            dummyData.add(new Report(14, 40, random.nextInt(100), timestamp_sb));
            dummyData.add(new Report(13, 50, random.nextInt(100), timestamp_sb));
            dummyData.add(new Report(12, 60, random.nextInt(100), timestamp_sb));

            minute_from += 5;
        }
        return dummyData;
    }

    static class dummyLiveData extends TimerTask {
        public void run() {

            Random random = new Random();

            List<Parameter> parameters = new ArrayList<>();
            parameters.add(new Parameter("Humidity", "%", random.nextInt(100), "06-05-2020 18:20"));
            parameters.add(new Parameter("Temperature", "C", random.nextInt(30), "06-05-2020 18:20"));
            parameters.add(new Parameter("CO2", "ppm", random.nextInt(50), "06-05-2020 18:20"));

            for(Parameter parameter : parameters) {
                parameter.setNew(true);
            }

            ParametersRepository.lastParameters.postValue(parameters);

        }
    }
}
