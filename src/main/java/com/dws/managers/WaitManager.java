package com.dws.managers;

import static com.dws.managers.DriverManager.getDriver;
import static com.dws.managers.PropertiesManager.getThisProperties;
import static com.dws.utils.ProperitesConstant.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitManager {
    private static WebDriverWait wait;
    
    public static WebDriverWait getWaitManager() {
        if(wait == null) {
            long waitTime = Long.parseLong(getThisProperties()
                .getProperty(WAIT_TIMEOUTINSECONDS));
            long waitSleep = Long.parseLong(getThisProperties()
                    .getProperty(WAIT_SLEEPINMILLIS));
            wait = new WebDriverWait(getDriver(), waitTime, waitSleep);
        }
        return wait;
    }
}
