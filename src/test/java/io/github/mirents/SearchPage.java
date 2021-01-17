package io.github.mirents;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SearchPage {
    public WebDriver driver;
    
    public SearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    
    // Локатор кнопки перехода в меню выбора метро
    @FindBy(xpath = "//div[contains(@data-marker, 'metro-select/with')]")
    private WebElement btnMetro;
    
    // Нажатие на кнопку выбора метро
    public void clickBtnMetro() {
        btnMetro.click();
    }
    
    // Получение названия кнопки выбора метро
    public String getTextMetroBtn() {
        String data = btnMetro.getAttribute("data-marker");
        if(data.contains("withValue"))
            return btnMetro.findElement(By.xpath("span[contains(@data-marker, "
                    + "'metro-select/value')]")).getText();
        else
            return btnMetro.getText();
    }
    
    // Локатор диалога выбранных станций
    @FindBy(xpath = "//div[contains(@data-marker, 'metro-select-dialog/chips')]")
    private WebElement moreStationDialog;
    
    // Локатор списка станций по алфавиту
    @FindBy(xpath = "//div[contains(@data-marker, 'metro-select-dialog/stations')]")
    private WebElement stationsList;
    
    // Локатор выбора типа поиска станций "По алфавиту"
    @FindBy(xpath = "//button[contains(@value, 'stations')]")
    private WebElement btnStations;
    
    // Переключение поиска на вид "По алфавиту"
    public void clickBtnStations() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        btnStations.click();
        wait.until(ExpectedConditions.elementSelectionStateToBe(btnLines, false));
    }
    
    // Локатор выбора типа поиска станций "По линиям"
    @FindBy(xpath = "//button[contains(@value, 'lines')]")
    private WebElement btnLines;
    
    // Переключение поиска на вид "По линиям"
    public void clickBtnLines() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        btnLines.click();
        wait.until(ExpectedConditions.elementSelectionStateToBe(btnStations, false));
    }
    
    // Локатор кнопки выбора станций
    @FindBy(xpath = "//button[contains(@data-marker, 'metro-select-dialog/apply')]")
    private WebElement btnChoise;
    
    public void clickBtnChoise() {
        btnChoise.click();
    }
    
    // Получение текта с кнопки выбора станций
    public String getTextBtnChoise() {
        return btnChoise.getText();
    }
    
    // Проверка видимостри кнопки. Определяется наличием класса "_3gizF"
    public boolean isVisibleBtnChoise()
    {
        String result = btnChoise.getAttribute("class");
        if(result.contains("_3gizF"))
            return true;
        else
            return false;
    }
    
    @FindBy(xpath = "//div[contains(@data-marker, 'metro-select/clearIcon')]")
    private WebElement btnClear;
    
    public boolean isVisibleBtnClear() {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        try{
        // Ожидание появления выбранного элемента
            wait.until(ExpectedConditions.visibilityOf(btnClear));
            return true;
        } catch(NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
    
    // Метод выбора нескольки случайных станций из списка
    public List<String> getStationChoise(int numStationChoise) {
        // Получение списка станций
        List<WebElement> el = stationsList.findElements(By.xpath("label"));
        // Результирующий список
        List<String> result = new ArrayList<>();
        // Список сохранения выбранных станций, для исключения повторного выбора
        ArrayList<Integer> choiseNumStation = new ArrayList<>();
        
        for(int i=0; i<=numStationChoise-1; i++) {
            // Объект - явное ожидание события
            WebDriverWait wait = new WebDriverWait(driver, 10);
            
            // Генерация случайного числа от 0 до кол-во станций, не 
            // содержащегося в списке созданных чисел
            int numStationToAdd = getRandom(el.size(), choiseNumStation);
            // Добавление сгенерированного числа, для исключения его повторения
            choiseNumStation.add(numStationToAdd);
            // Выбор названия станции по сгенерированному числу
            String station = (el.get(numStationToAdd)).getText();
            // Добавление станции в результирующий список
            result.add(station);
            // Создание локатора сгенерированной станции
            String path = "//div[contains(@data-marker, "
                    + "'metro-select-dialog/stations')]//span[contains(text(), '"
                    + station + "')]";
            // Создание элемента для текущего локатора
            WebElement scrollAndSet = driver.findElement(By.xpath(path));
            // Создание действия
            Actions actions = new Actions(driver);
            // Перемотка страницы к указанному элементу и нажатие на него
            actions.moveToElement(scrollAndSet).click();
            // Применение вышеуказанных действий
            actions.perform();
            // Ожидание появления выбранного элемента
            wait.until(ExpectedConditions.
                visibilityOfElementLocated(By.xpath(path)));
        }
        
        // Создание действия
        Actions actions = new Actions(driver);
        // Перемотка страницы вверх
        actions.moveToElement(btnStations);
        // Применение действия
        actions.perform();
        // Возвращение списка с нажатыми станциями
        return result;
    }

    // Генерация случайного числа от 0 до max-1, не входящще в список listNum
    private int getRandom(int max, List<Integer> listNum) {
        int result;
        do {
            result = (int) (Math.random() * max);
        } while (listNum.indexOf(result) != -1);
        
        return result;
    }
    
    // Выбор всех элементов, присутвующих в диалоге выбора станций
    public List<String> getSelectStation() {
        // Результирующий список
        List<String> result = new ArrayList<>();
        
        // Кнопка развернуть показана в том случае, если выбрано несколько
        // станций и они не помещаются в меню. Если эта кнопка присутствует,
        // то она нажимается и разворачивается список с выбранными станциями.
        // Если этой кнопки нет или она уже развернута, переходим к следующему
        // действию
        try {
            // Объект - явное ожидание события
            WebDriverWait wait = new WebDriverWait(driver, 10);
            // Поиск элемента - кнопки, если ее нет, срабатывание исключения и
            // переход к следующему действию
            WebElement btn = moreStationDialog.findElement(By.xpath("a"));
            // Если кнопка не нажата нажать ее и подождать
            if(!btn.getText().contains("Скрыть выбранные")) {
                btn.click();
                wait.until(ExpectedConditions.visibilityOf(btn));
            }
        } catch(NoSuchElementException e) {
            System.out.println("Кнопки скрытых станций нет или она нажата");
        }

        // Поиск выбранных станций в диалоге выбора
        List<WebElement> el = moreStationDialog.findElements(By.xpath("div//div"));
        // Добавление в результирующий список названия выбранных станций
        for ( WebElement e : el ) {
          result.add(e.getText());
        }
        // Возвращение списка с выбранными станциями
        return result;
    }
}
