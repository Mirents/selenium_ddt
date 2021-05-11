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
        LOGGER.debug("Загрузка из системных настроек ожидания implicityWait");
        int implicityWait = Integer.parseInt(getThisProperties().getProperty(DRIVER_IMPLICITY_WAIT));
        LOGGER.debug("Установка времени ожидания implicityWait на {} сек.", implicityWait);
        getDriver().manage().timeouts().implicitlyWait(implicityWait, TimeUnit.SECONDS);
        
        LOGGER.info("Загрузка из системных настроек ожидания pageLoadTimeout");
        int pageLoadTimeout = Integer.parseInt(getThisProperties().getProperty(DRIVER_PAGE_LOAD_TIMEOUT));
        LOGGER.debug("Установка времени ожидания pageLoadTimeout на {} сек.", pageLoadTimeout);
        getDriver().manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
    }

    public static void openBrowser() {
        try {
            String app_URL = getThisProperties().getProperty(APP_URL);
            LOGGER.debug("Открытие сайта: {}", app_URL);
            getDriver().get(app_URL);
        } catch(NullPointerException ex) {
            String message = "Вебдрайвер не инициализирован";
            LOGGER.error(message);
            Assertions.fail(message);
        } catch(WebDriverException ex) {
            if(ex.getLocalizedMessage().contains("ERR_INTERNET_DISCONNECTED")) {
                String message = "Отсутствует интернет-соединение |"
                        + initManager.getClass().getName();
                LOGGER.error(message);
                Assertions.fail(message);
            } else {
                String message = "Неизвестная ошибка при открытии сайта, связанная"
                        + "с вебдрайвером: " +
                        ex.getLocalizedMessage() + " |" +
                        initManager.getClass().getName();
                LOGGER.error(message);
                Assertions.fail(message);
            }
        } catch(Exception ex) {
            String message = "Неизвестная ошибка при открытии сайта: " +
                        ex.getLocalizedMessage() + " |" +
                        initManager.getClass().getName();
            LOGGER.error(message);
            Assertions.fail(message);
        }
    }
    
    
    public static void quitFramework() {
        LOGGER.debug("Завершение работы веб-драйвера");
        quitDriver();
        LOGGER.debug("Очистка хранилища классов веб-страниц");
        getPageManager().clearMapPage();
    }
}