package com.ndtv.social.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DriverFactory {

    private static final Map<DriverType, Supplier<WebDriver>> driverMap = new HashMap<>();
    private static String OS = System.getProperty("os.name").toLowerCase();

    //chrome driver supplier
    private static final Supplier<WebDriver> chromeDriverSupplier = () -> {
        if (isMac()) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver_mac");
        } else if (isUnix()) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        }
        return new ChromeDriver();
    };

    //firefox driver supplier
    private static final Supplier<WebDriver> firefoxDriverSupplier = () -> {
        if (isMac()) {
            System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver_mac");
        } else if (isUnix()) {
            System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver");
        } else {
            System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
        }
        return new FirefoxDriver();
    };

    static {
        driverMap.put(DriverType.CHROME, chromeDriverSupplier);
        driverMap.put(DriverType.FIREFOX, firefoxDriverSupplier);
    }

    public static final WebDriver getDriver(DriverType type) {
        return driverMap.get(type).get();
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

}
