package com.ndtv.social;

import com.ndtv.social.factory.DriverFactory;
import com.ndtv.social.factory.DriverType;
import com.ndtv.social.utilities.YamlReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class BaseTest {

    private WebDriver driver;
    public static HashMap<String, Object> envdata = new HashMap<>();
    public static HashMap<String, String> environment = new HashMap<>();
    public static Properties config;

    @BeforeSuite
    public void beforeSuite() throws IOException {
        String browser = System.getProperty("browser","chrome");
        switch (browser.toLowerCase()) {
            case "firefox":
                driver = DriverFactory.getDriver(DriverType.FIREFOX);
                break;
            default:
                driver = DriverFactory.getDriver(DriverType.CHROME);
        }
        FileInputStream fis = new FileInputStream(new File("configuration.properties"));
        config = new Properties();
        config.load(fis);

        envdata = (HashMap<String, Object>) YamlReader.loadUserDataYaml(this.getClass().getClassLoader()
                .getResourceAsStream("TestData" + File.separator + "EnvironmentData.yml")).get("Environment");
        String envn = config.getProperty("environment");
        String value = envdata.get(envn).toString().substring(1, envdata.get(envn).toString().length() - 1);
        String[] keyValuePairs = value.split(",");

        for (String pair : keyValuePairs) {
            String[] entry = pair.split("=");
            environment.put(entry[0].trim(), entry[1].trim());
        }
    }

    @AfterSuite
    public void afterSuite() {
        if (null != driver) {
            driver.close();
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}