package com.ndtv.social.utilities;

public class WeatherDetail {

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    private String unit;

    public double getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    private double temp;

    public WeatherDetail(String unit, double temp) {
        this.unit = unit;
        this.temp = temp;
    }
}
