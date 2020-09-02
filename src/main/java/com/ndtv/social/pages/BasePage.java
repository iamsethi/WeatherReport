package com.ndtv.social.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    private static final int TIMEOUT = 5;
    private static final int POLLING = 100;
    private static final String URL = "https://www.ndtv.com/";

    protected WebDriver driver;
    private WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        PageFactory.initElements(driver, this);
    }

    protected void waitForElementToAppear(WebElement locator) {
        wait.until(ExpectedConditions.visibilityOf(locator));
    }

    protected void waitForElementToDisappear(WebElement locator) {
        wait.until(ExpectedConditions.invisibilityOf(locator));
    }

    protected void waitForElementToBeClickable(WebElement locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void navigateToHomePage() {
        driver.manage().window().maximize();
        driver.get("https://www.ndtv.com/");
    }
}
