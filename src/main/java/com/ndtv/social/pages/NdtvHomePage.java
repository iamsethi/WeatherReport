package com.ndtv.social.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.HashMap;

public class NdtvHomePage extends BasePage {

    public NdtvHomePage(WebDriver driver) {
        super(driver);
        navigateToHomePage();
    }

    @FindBy(how = How.ID, using = "h_sub_menu")
    private WebElement btn_nav_more;

    @FindBy(how = How.LINK_TEXT, using = "WEATHER")
    private WebElement btn_weather;

    public void openNavigationBar() {
        waitForElementToBeClickable(btn_nav_more);
        btn_nav_more.click();
    }

    public NdtvWeatherPage navigateToWeatherSection(HashMap<String, String> environment, String city) {
        openNavigationBar();
        waitForElementToBeClickable(btn_weather);
        btn_weather.click();
        return new NdtvWeatherPage(driver,environment,city);
    }


}