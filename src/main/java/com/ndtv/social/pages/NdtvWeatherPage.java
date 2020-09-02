package com.ndtv.social.pages;

import com.ndtv.social.api.WeatherApiActions;
import com.ndtv.social.utilities.Temperature;
import com.ndtv.social.utilities.TemperatureCompare;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class NdtvWeatherPage extends BasePage {

    public enum WeatherDetail {
        CONDITION, WIND, HUMIDITY, CELCIUS, FARENHEIT
    }

    WeatherApiActions weatherApiActions;

    public NdtvWeatherPage(WebDriver driver, HashMap<String, String> environment, String city) {
        super(driver);
        weatherApiActions = new WeatherApiActions(environment, city);
    }

    @FindBy(how = How.ID, using = "searchBox")
    private WebElement tbx_search_city;

    @FindBy(how = How.XPATH, using = "//div[@class='leaflet-popup-content-wrapper']//span[@class='heading']")
    private List<WebElement> lbl_weather_detail;

    public WebElement getCity(String city) {
        return driver.findElement(By.xpath("//div[@title='" + city + "']"));
    }

    public WebElement getCityText(String city) {
        return getCity(city).findElement(By.className("cityText"));
    }

    public WebElement getTemperatureInCelcius(String city) {
        return getCity(city).findElement(By.className("tempRedText"));
    }

    public WebElement getTemperatureInFarenheit(String city) {
        return getCity(city).findElement(By.className("tempWhiteText"));
    }

    public void searchCity(String city) {
        waitForElementToBeClickable(tbx_search_city);
        tbx_search_city.sendKeys(city);
        WebElement cbx_city = driver.findElement(By.id(city));
        if (cbx_city.getAttribute("checked") == null)
            cbx_city.click();
    }

    public void selectCity(String city) {
        waitForElementToBeClickable(getCity(city));
        getCity(city).click();
    }

    public void validateCityOnMap(String city) {
        Assert.assertEquals(getCityText(city).getText(), city, "City is not displayed on Map");
        Assert.assertTrue(getTemperatureInCelcius(city).isDisplayed(), "Temperature is ℃ is not displayed ");
        Assert.assertTrue(getTemperatureInFarenheit(city).isDisplayed(), "Temperature is ℉ is not displayed ");
    }

    public int getWeatherPosition(WeatherDetail option) {
        int position = 0;
        switch (option.name()) {
            case "CONDITION":
                position = WeatherDetail.CONDITION.ordinal();
                break;
            case "WIND":
                position = WeatherDetail.WIND.ordinal();
                break;
            case "HUMIDITY":
                position = WeatherDetail.HUMIDITY.ordinal();
                break;
            case "CELCIUS":
                position = WeatherDetail.CELCIUS.ordinal();
                break;
            case "FARENHEIT":
                position = WeatherDetail.FARENHEIT.ordinal();
                break;
        }
        return position;
    }

    public void validateWeatherDetailsOnMap(String city) {
        lbl_weather_detail.stream().map(e -> e.isDisplayed());
    }

    public ArrayList<Temperature> weatherDetailsWeb(String city) {
        List<String> weatherDetail = lbl_weather_detail
                .stream()
                .map(e -> e.getText())
                .map(e -> e.split(": ")[1])
                .collect(Collectors.toList());

        ArrayList<Temperature> temp = new ArrayList<>();
        temp.add(new Temperature(WeatherDetail.CELCIUS.name(), Integer.parseInt(weatherDetail.get(getWeatherPosition(WeatherDetail.CELCIUS)))));
        temp.add(new Temperature(WeatherDetail.FARENHEIT.name(), Integer.parseInt(weatherDetail.get(getWeatherPosition(WeatherDetail.FARENHEIT)))));
        temp.add(new Temperature(WeatherDetail.HUMIDITY.name(), Integer.parseInt(weatherDetail.get(getWeatherPosition(WeatherDetail.HUMIDITY)).split("%")[0])));
        return temp;
    }

    public ArrayList<Temperature> weatherDetailsApi() {
        ArrayList<Temperature> temp = new ArrayList<>();
        temp.add(new Temperature(WeatherDetail.CELCIUS.name(), weatherApiActions.tempInCelcius));
        temp.add(new Temperature(WeatherDetail.FARENHEIT.name(), weatherApiActions.tempInFarenheit));
        temp.add(new Temperature(WeatherDetail.HUMIDITY.name(), weatherApiActions.humidity));
        return temp;
    }

    public ArrayList<Temperature> temperatureComparison(ArrayList<Temperature> weatherDetailsWeb, ArrayList<Temperature> weatherDetailsApi) {
        ArrayList<Temperature> temp = new ArrayList<>();
        temp.addAll(weatherDetailsWeb);
        temp.addAll(weatherDetailsApi);
        Collections.sort(temp, new TemperatureCompare());
        for (Temperature t : temp) {
            System.out.println(t.getUnit() + "::" + t.getTemp());
        }
        return temp;
    }

    public void weatherComparisonWithVariance(ArrayList<Temperature> weatherDetails, Object variance) {
        SoftAssert asserts = new SoftAssert();
        ArrayList<Double> temp = new ArrayList<>();
        int diff;
        for (Temperature t : weatherDetails) {
            temp.add(t.getTemp());
        }
        for (int i = 0; i < weatherDetails.size() - 1; i = i + 2) {
            diff = Math.abs((int) (temp.get(i) - temp.get(i + 1)));
            asserts.assertTrue(diff <= Integer.parseInt((String) variance), weatherDetails.get(i).getUnit() + " - is not within variance - " + variance);
        }
        asserts.assertAll();
    }

}