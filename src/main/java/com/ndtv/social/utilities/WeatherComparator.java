package com.ndtv.social.utilities;

import java.util.Comparator;


public class WeatherComparator implements Comparator<WeatherDetail> {
    public int compare(WeatherDetail t1, WeatherDetail t2) {
        int unitComp = t1.getUnit().compareTo(t2.getUnit());
        return (int) ((unitComp == 0) ? t1.getTemp() - t2.getTemp() : unitComp);
    }
}