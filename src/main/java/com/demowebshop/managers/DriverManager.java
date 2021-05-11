package com.demowebshop.managers;

import static com.demowebshop.managers.PropertiesManager.getThisProperties;
import static com.demowebshop.utils.ProperitesConstant.*;
import com.demowebshop.utils.WebDriverListener;
import org.apache.commons.exec.OS;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriver.class);
    
    private static WebDriver driver;
    private static EventFiringWebDriver eventDriver;
    private static DriverManager INSTANCE = null;
    private static WebDriverListener eventListener;
    
    private DriverManager() {
        if(getThisProperties().getProperty(HIGLILIGHTS_ELEMENTS_ENABLE).equals("yes")) {
            String color = getThisProperties().getProperty(HIGLILIGHTS_ELEMENTS_COLOR);
            int interval = Integer.parseInt(getThisProperties().getProperty(HIGLILIGHTS_ELEMENTS_INTERVAL));
            int count = Integer.parseInt(getThisProperties().getProperty(HIGLILIGHTS_ELEMENTS_COUNT));
            eventListener = new WebDriverListener(color, interval, count);
        } else {
            eventListener = new WebDriverListener("#000000", 0, 0);
        }

        initDriver();
        LOGGER.info("Successful initialization of the web driver");
    }
    
    public static EventFiringWebDriver getDriver() {
        if(eventDriver == null) {
            LOGGER.info("Instantiating the Web Driver");
            INSTANCE = new DriverManager();
        }
        
        return eventDriver;
    }

    public static void quitDriver() {
        if(eventDriver != null) {
            LOGGER.info("Shutting down the web driver");
            eventDriver.quit();
            eventDriver = null;
            INSTANCE = null;
        }
    }
    
    private void initDriver() {
        if (OS.isFamilyWindows()) {
            LOGGER.info("Initializing the Windows Web Driver");
            initDriverAnyOsFamily(PATH_DRIVER_CHROME_WINDOWS);
        } else if (OS.isFamilyUnix()) {
            LOGGER.info("Initializing the Unix Web Driver");
            initDriverAnyOsFamily(PATH_DRIVER_CHROME_UNIX);
        }
    }
    
    private void initDriverAnyOsFamily(String chrome) {
        String param = getThisProperties().getProperty(chrome);
        System.setProperty("webdriver.chrome.driver", param);
        createDriver(getChromeOptions());
        setGeneralStartOptions();
    }
    
    private void createDriver(ChromeOptions chromeOptions) {
        try {
            LOGGER.info("Creating a chrome browser web driver");
            driver = new ChromeDriver(chromeOptions);
            eventDriver = new EventFiringWebDriver(driver);
            eventDriver.register(eventListener);
        } catch(IllegalStateException ex) {
            throwIllegalStateException(ex);
        }
    }
        
    private void throwIllegalStateException(IllegalStateException ex) {
        if(ex.getLocalizedMessage().contains("The driver executable does not exist")) {
            String message = "Webdriver executable file not found for browser | "
                    + this.getClass().getName();
            LOGGER.info(message);
            Assertions.fail(message);
        } else if(ex.getLocalizedMessage().contains("The driver is not executable")) {
            String message = "There is no permission to run the web driver or the "
                    + "web driver file is corrupted | " +
                     this.getClass().getName();
            LOGGER.info(message);
            Assertions.fail(message);
        }
    }
    
    private ChromeOptions getChromeOptions() {
        ChromeOptions result = new ChromeOptions();
        LOGGER.info("Setting browser options");
        if(getThisProperties().getProperty(BROWSER_IS_HEADLESS).equals("yes")) {
            LOGGER.info("Enabling Browser Option: BROWSER_IS_HEADLESS");
            result.setHeadless(true);
        }
                
        return result;
    }
    
    private void setGeneralStartOptions() {
        if(getThisProperties().getProperty(BROWSER_DELETE_ALL_COOKIES_BEFORE_START_TESTS).equals("yes")) {
            LOGGER.info("Clearing cookies before starting");
            getDriver().manage().deleteAllCookies();
        }
        if(getThisProperties().getProperty(BROWSER_MAXIMIZE_WINDOW).equals("yes")) {
            LOGGER.info("Maximize the browser window to full screen");
            getDriver().manage().window().maximize();
        }
    }
}
