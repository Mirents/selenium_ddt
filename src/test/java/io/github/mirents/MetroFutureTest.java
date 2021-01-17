package io.github.mirents;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MetroFutureTest {
    private static WebDriver driver = null;
    private static ConfProperties config = null;
    public static SearchPage searchPage;
    
    @Before
    public void setup() {
        // Указание пути файла для конфигурационного класса
        config = new ConfProperties("src/test/resources/conf.properties");
        
        // Настройка мобильного устройства для открытия мобильной версии
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; "
                + "en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 "
                + "(KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");

        // Предварительные настройки
        ChromeOptions options = new ChromeOptions();
        if("on".equals(config.getProperty("disablenotifications")))
            options.addArguments("--disable-notifications");
        options.addArguments("--window-size=350,800");
        
        System.setProperty("webdriver.chrome.driver", config.getProperty("chromedriver"));
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        driver = new ChromeDriver(options);
        searchPage = new SearchPage(driver);
        
        // Развертывание окна на весь экран
        if("on".equals(config.getProperty("maximizewindow")))
            driver.manage().window().maximize();
        
        // Установка времени неявного ожидания элемента
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Переход на страницу входа на сайт
        driver.get(config.getProperty("searchpage"));
    }

/*Критерий приемки:
    переключение между табами Алфавит/Линия не сбрасывает выбор;
Test-cale:
    - открыть страницу поиска;
    - нажать кнопку поиска метро;
    - выбрать несколько случайных станций;
    - переключиться на вид по линиям;
    - переключится на вид по алфавиту;
    - переключиться на вид по линиям;
Ожидаемый результат:
    Выбранные станции не сброшены при переходе по поиску "По линиям" - 
    "По алфавиту" и обратно;*/
    @Test
    public void changeTabTest() {
        // Открытие меню поиска станций
        searchPage.clickBtnMetro();
        // Выбор станций метро случайным образом
        List<String> exceptedList = searchPage.getStationChoise(
                Integer.parseInt(config.getProperty("numstationtochoise")));
        // Получение списка выбранных станций с предыдущего шага
        List<String> actualList = searchPage.getSelectStation();
        // Проверка совпадения списков
        Assert.assertEquals("Проверка соответствия списков", exceptedList, actualList);
        
        // Нажатие кнопки выбора станицй "По линиям"
        searchPage.clickBtnLines();
        // Получение списка выбранных станций
        actualList = searchPage.getSelectStation();
        // Сравнение списка выбранных станций с результатом ранее
        Assert.assertEquals("Проверка после нажатия по линиям", exceptedList, actualList);
        
        // Нажатие кнопки выбора станицй "По алфавиту"
        searchPage.clickBtnStations();
        // Получение списка выбранных станций
        actualList = searchPage.getSelectStation();
        // Сравнение списка выбранных станций с результатом ранее
        Assert.assertEquals("Проверка после нажатия по алфавиту", exceptedList, actualList);
    }

/*Критерий приемки:
    при выборе станции снизу выезжает плавающая кнопка "Выбрать N станций";
Text-case:
    - открыть страницу поиска;
    - нажать кнопку поиска метро;
    - проверить отсутсвие кнопки выбора метро;
    - выбрать несколько случайных станций;
    - проверить видимость кнопки выбора метро;
    - проверить соответствие текста на кнопке 
    выбранному количеству станций;*/
    @Test
    public void visibleBtnChoiseTest() {
        // Открытие меню поиска станций
        searchPage.clickBtnMetro();
        
        Assert.assertEquals("Проверка оотсутствия кнопки", false, 
                searchPage.isVisibleBtnChoise());
        // Выбор станций метро случайным образом
        List<String> exceptedList = searchPage.getStationChoise(
                Integer.parseInt(config.getProperty("numstationtochoise")));
        Assert.assertEquals("Проверка наличия кнопки", true, 
                searchPage.isVisibleBtnChoise());
        
        // Проверка сообщения на кнопке
        Assert.assertEquals("Проверка правильности сообщения на кнопке", 
                ("Выбрать " + generateMessage(exceptedList.size())), 
                searchPage.getTextBtnChoise());
    }

/*Критерий приемки:
    кнопка “Сбросить” появляется только при выбранных станциях;
Text-case:
    - открыть страницу поиска;
    - проверить отсутствие кнопки сброса метро;
    - нажать кнопку поиска метро;
    - выбрать несколько случайных станций;
    - нажать кнопку выбора метро;
    - проверить наличие кнопки сброса метро;*/
    @Test
    public void visibleBtnClearTest() {
        // Проверка отсутсвия кнопки
        Assert.assertEquals("Проверка отсутствия кнопки", false, 
                searchPage.isVisibleBtnClear());
        // Открытие меню поиска станций
        searchPage.clickBtnMetro();
        // Выбор станций метро случайным образом
        searchPage.getStationChoise(
                Integer.parseInt(config.getProperty("numstationtochoise")));
        // Нажатие кнопки выбора метро
        searchPage.clickBtnChoise();
        // Проверка наличия кнопки
        Assert.assertEquals("Проверка наличия кнопки", true, 
                searchPage.isVisibleBtnClear());
    }
    
/*Критерий приемки:
    на экране “Уточнить”, примененный фильтр по метро отображаем 
    с формулировкой "Выбрано n станций".
Text-case:
    - открыть страницу поиска;
    - проверить текста по умолчанию на фильтре "Метро";
    - нажать кнопку поиска метро;
    - выбрать несколько случайных станций;
    - нажать кнопку выбора метро;
    - проверить корректность текста в фильтре станций;*/
    @Test
    public void filterTextСheckTest() {
        // Проверка текста по умолчанию
        Assert.assertEquals("Проверка текста по умолчанию на фильтре 'Метро'",
                "Метро", searchPage.getTextMetroBtn());
        
        // Открытие меню поиска станций
        searchPage.clickBtnMetro();
        // Выбор станций метро случайным образом
        List<String> exceptedList = searchPage.getStationChoise(
                Integer.parseInt(config.getProperty("numstationtochoise")));
        // Нажатие кнопки выбора метро
        searchPage.clickBtnChoise();
        
        // Проверка текста с выбранным фильтром
        Assert.assertEquals("Проверка текста с выбранным фильтром",
                ("Выбрано " + (generateMessage(exceptedList.size()))),
                searchPage.getTextMetroBtn());
    }
    
    private String generateMessage(int i) {
        String result = "";
        if(i == 1) {
            result += Integer.toString(i) + " станцию";
        } else if(i >= 2 && i <= 4) {
            result += Integer.toString(i) + " станции";
        } else {
            result += Integer.toString(i) + " станций";
        }
        return result;
    }
    
    @After
    public void tearDown() {
        driver.quit();
    }
}
