package com.ndtv.social.api;

import com.ndtv.social.pages.NdtvWeatherPage;
import com.ndtv.social.pojo.Weathers;
import com.ndtv.social.utilities.RestUtilities;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

public class WeatherApiActions {

    private final String weatherEndPoint;
    public final double tempInCelcius;
    public final double tempInFarenheit;
    public final double humidity;
    private String city;
    private String basePath;
    private String baseURI;
    private String appKey;
    public RequestSpecification reqSpec;
    public ResponseSpecification resSpec;
    private static final Map<NdtvWeatherPage.WeatherDetail, String> unitMap = new HashMap<>();

    static {
        unitMap.put(NdtvWeatherPage.WeatherDetail.FARENHEIT, "imperial");
        unitMap.put(NdtvWeatherPage.WeatherDetail.CELCIUS, "metric");
    }

    public WeatherApiActions(HashMap<String, String> environment, String city) {
        this.baseURI = environment.get("baseURI");
        this.basePath = environment.get("basePath");
        this.weatherEndPoint = environment.get("weatherEndPoint");
        this.appKey = environment.get("appKey");
        this.city = city;
        resSpec = RestUtilities.getResponseSpecification();
        RestUtilities.setEndPoint(weatherEndPoint);
        this.tempInCelcius = getTemperature(NdtvWeatherPage.WeatherDetail.CELCIUS);
        this.tempInFarenheit = getTemperature(NdtvWeatherPage.WeatherDetail.FARENHEIT);
        this.humidity = getHumidity(NdtvWeatherPage.WeatherDetail.HUMIDITY);
    }

    private Weathers getWeather(NdtvWeatherPage.WeatherDetail unit) {
        reqSpec = RestUtilities.getRequestSpecification(baseURI, basePath, appKey, city);
        return RestUtilities
                .getResponse(RestUtilities.createQueryParam(reqSpec, "units", unitMap.get(unit)), "get")
                .as(Weathers.class);
    }

    private Double getTemperature(NdtvWeatherPage.WeatherDetail unit) {
        return Double.parseDouble(getWeather(unit).getMain().getTemp());
    }

    private Double getHumidity(NdtvWeatherPage.WeatherDetail unit) {
        return Double.parseDouble(getWeather(unit).getMain().getHumidity());
    }
}