package com.dws.managers;

import static com.dws.managers.DriverManager.getDriver;
import static com.dws.utils.ProperitesConstant.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitManager {
    private static WebDriverWait wait;
    private static WaitManager INSTANCE = null;
    PropertiesManager propertiesManager = PropertiesManager.getPropertiesManager();
    
    private WaitManager() {
        long sleepInMillis = Integer.parseInt(propertiesManager.getProperty(WAIT_SLEEPINMILLIS));
        if(sleepInMillis == 0) {
            sleepInMillis = 1000;
        }
        long timeoutInSeconds = Integer.parseInt(propertiesManager.getProperty(WAIT_TIMEOUTINSECONDS));
        if(timeoutInSeconds == 0) {
            timeoutInSeconds = 5;
        }

        wait = new WebDriverWait(getDriver(), timeoutInSeconds, sleepInMillis);
    }
    
    public static WebDriverWait getWaitManager() {
        if(wait == null) {
            INSTANCE = new WaitManager();
        }
        return wait;
    }
}
