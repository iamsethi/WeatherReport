package com.ndtv.social;

import com.ndtv.social.pages.NdtvHomePage;
import com.ndtv.social.pages.NdtvWeatherPage;
import com.ndtv.social.utilities.WeatherDetail;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class NdtvWeatherPageTests extends BaseTest {

    NdtvWeatherPage ndtvWeatherPage;

    @Test(dataProvider = "CityProvider")
    public void validateCityOnMap(String city) {
        NdtvHomePage ndtvHomepage = new NdtvHomePage(getDriver());
        ndtvWeatherPage = ndtvHomepage.navigateToWeatherSection(environment, city);
        ndtvWeatherPage.searchCity(city);
        ndtvWeatherPage.validateCityOnMap(city);
    }

    @Test(dataProvider = "CityProvider")
    public void validateWeatherDetailOnMap(String city) {
        NdtvHomePage ndtvHomepage = new NdtvHomePage(getDriver());
        ndtvWeatherPage = ndtvHomepage.navigateToWeatherSection(environment, city);
        ndtvWeatherPage.searchCity(city);
        ndtvWeatherPage.selectCity(city);
        ndtvWeatherPage.validateWeatherDetailsOnMap(city);
    }

    @Test(dataProvider = "CityProvider")
    public void weatherPageTests(String city) {
        NdtvHomePage ndtvHomepage = new NdtvHomePage(getDriver());
        ndtvWeatherPage = ndtvHomepage.navigateToWeatherSection(environment, city);
        ndtvWeatherPage.searchCity(city);
        ndtvWeatherPage.selectCity(city);
        ArrayList<WeatherDetail> weatherDetailsWeb = ndtvWeatherPage.weatherDetailsWeb(city);
        ArrayList<WeatherDetail> weatherDetailsApi = ndtvWeatherPage.weatherDetailsApi();
        ArrayList<WeatherDetail> weatherDetails = ndtvWeatherPage.temperatureComparison(weatherDetailsWeb, weatherDetailsApi);
        ndtvWeatherPage.weatherComparisonWithVariance(weatherDetails, config.get("variance"));
    }

    @DataProvider(name = "CityProvider")
    public Object[][] getCityFromDataProvider() {
        return new Object[][]{{"Kanpur"}, {"Bengaluru"}};
    }
}