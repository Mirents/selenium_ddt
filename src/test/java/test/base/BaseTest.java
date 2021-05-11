package test.base;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Базовый класс, отвечающий за первоначальную настройку тестов
 * @author vadim
 */
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    /**
     * Метод первоначальной настройки драйвера и параметров запуска
     */
    @BeforeEach
    public void beforeAll() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("disable-notifications");
        options.addArguments("disable-infobars");
        // Без этих опций вообще не начинало работу
        options.addArguments("disable-popup-blocking");
        options.addArguments("incognito");        
        
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        driver = new ChromeDriver(options);
        
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 25, 1000);

        String baseUrl = "https://www.sberbank.ru/ru/person";
        driver.get(baseUrl);
        
        new WebDriverWait(driver, 1).until( 
            webDriver -> ((JavascriptExecutor) webDriver)
        .executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Метод завершения работы
     */
    @AfterEach
    public void afterAll() {
        driver.quit();
    }    
}
