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

/**
 * Класс работы с драйвером Selenium.
 */
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
        LOGGER.info("Успешная инициализация веб-драйвера");
    }
    
    /**
     * Получение INSTANCE веб-драйвера.
     * @return WebDriver
     */
    public static EventFiringWebDriver getDriver() {
        if(eventDriver == null) {
            LOGGER.info("Создание экземпляра веб-драйвера");
            INSTANCE = new DriverManager();
        }
        
        return eventDriver;
    }

    /**
     * Завершение работы веб-драйвера.
     */
    public static void quitDriver() {
        if(eventDriver != null) {
            LOGGER.info("Завершение работы веб-драйвера и закрытие всех активных вкладок");
            eventDriver.quit();
            eventDriver = null;
            INSTANCE = null;
        }
    }
    
    /**
     * Инициализация веб-драйвера для различных ОС.
     */
    private void initDriver() {
        if (OS.isFamilyWindows()) {
            LOGGER.info("Инициализация веб-драйвера для ОС Windows");
            initDriverAnyOsFamily(PATH_DRIVER_CHROME_WINDOWS);
        } else if (OS.isFamilyUnix()) {
            LOGGER.info("Инициализация веб-драйвера для Unix");
            initDriverAnyOsFamily(PATH_DRIVER_CHROME_UNIX);
        }
    }
    
    private void initDriverAnyOsFamily(String chrome) {
        String param = getThisProperties().getProperty(chrome);
        LOGGER.info("Установка системной переменной путь к драйверу браузера chrome: ", param);
        System.setProperty("webdriver.chrome.driver", param);
        createDriver(getChromeOptions());
        setGeneralStartOptions();
    }
    
    private void createDriver(ChromeOptions chromeOptions) {
        try {
                LOGGER.info("Создание веб-драйвера браузера chrome");
                driver = new ChromeDriver(chromeOptions);
                eventDriver = new EventFiringWebDriver(driver);
                eventDriver.register(eventListener);
            } catch(IllegalStateException ex) {
                throwIllegalStateException(ex);
            }
    }
        
    private void throwIllegalStateException(IllegalStateException ex) {
        if(ex.getLocalizedMessage().contains("The driver executable does not exist")) {
            String message = "Для браузера не найден исполняемый файл вебдрайвера |"
                    + this.getClass().getName();
            LOGGER.info(message);
            Assertions.fail(message);
        } else if(ex.getLocalizedMessage().contains("The driver is not executable")) {
            String message = "Нет прав для запуска вебдрайвера или файл вебдрайвера поврежден |" +
                     this.getClass().getName();
            LOGGER.info(message);
            Assertions.fail(message);
        }
    }
    
    private ChromeOptions getChromeOptions() {
        ChromeOptions result = new ChromeOptions();
        LOGGER.info("Установка параметров браузера chrome");
        if(getThisProperties().getProperty(BROWSER_IS_HEADLESS).equals("yes")) {
            LOGGER.info("Включение параметра браузера: BROWSER_IS_HEADLESS");
            result.setHeadless(true);
        }
                
        return result;
    }
    
    private void setGeneralStartOptions() {
        if(getThisProperties().getProperty(BROWSER_DELETE_ALL_COOKIES_BEFORE_START_TESTS).equals("yes")) {
            LOGGER.info("Очистка куки перед стартом");
            getDriver().manage().deleteAllCookies();
        }
        if(getThisProperties().getProperty(BROWSER_MAXIMIZE_WINDOW).equals("yes")) {
            LOGGER.info("Разворачивание окна браузера на весь экран");
            getDriver().manage().window().maximize();
        }
    }
}
