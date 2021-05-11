package com.demowebshop.managers;

import static com.demowebshop.managers.DriverManager.getDriver;
import static com.demowebshop.managers.DriverManager.quitDriver;
import static com.demowebshop.managers.PageManager.getPageManager;
import static com.demowebshop.managers.PropertiesManager.getThisProperties;
import static com.demowebshop.utils.ProperitesConstant.*;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitManager.class);
    private static final InitManager initManager = new InitManager();

    public static void initFramework() {
        LOGGER.debug("Loading from system settings wait implicityWait");
        int implicityWait = Integer.parseInt(getThisProperties().getProperty(DRIVER_IMPLICITY_WAIT));
        LOGGER.debug("Setting implicityWait timeout to {} sec.", implicityWait);
        getDriver().manage().timeouts().implicitlyWait(implicityWait, TimeUnit.SECONDS);
        
        LOGGER.info("Loading from system settings wait pageLoadTimeout");
        int pageLoadTimeout = Integer.parseInt(getThisProperties().getProperty(DRIVER_PAGE_LOAD_TIMEOUT));
        LOGGER.debug("Setting the pageLoadTimeout timeout to {} sec.", pageLoadTimeout);
        getDriver().manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
    }

    public static void openBrowser() {
        try {
            String app_URL = getThisProperties().getProperty(APP_URL);
            LOGGER.debug("Site opening: {}", app_URL);
            getDriver().get(app_URL);
        } catch(NullPointerException ex) {
            String message = "Webdriver not initialized";
            LOGGER.error(message);
            Assertions.fail(message);
        } catch(WebDriverException ex) {
            if(ex.getLocalizedMessage().contains("ERR_INTERNET_DISCONNECTED")) {
                String message = "No internet connection | "
                        + initManager.getClass().getName();
                LOGGER.error(message);
                Assertions.fail(message);
            } else {
                String message = "Unknown error when opening the site related "
                        + "to the web driver: " +
                        ex.getLocalizedMessage() + " | " +
                        initManager.getClass().getName();
                LOGGER.error(message);
                Assertions.fail(message);
            }
        } catch(Exception ex) {
            String message = "Unknown error when opening the site: " +
                        ex.getLocalizedMessage() + " |" +
                        initManager.getClass().getName();
            LOGGER.error(message);
            Assertions.fail(message);
        }
    }
    
    public static void quitFramework() {
        LOGGER.debug("Shutting down the web driver");
        quitDriver();
        LOGGER.debug("Clearing the class store of web pages");
        getPageManager().clearMapPage();
    }
}