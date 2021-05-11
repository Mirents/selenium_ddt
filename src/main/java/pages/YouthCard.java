package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Класс страницы оформления молодежной карты
 * @author vadim
 */
public class YouthCard {
    WebDriver driver;
    WebDriverWait wait;
    
    @FindBy(xpath = "//h1[contains(@class, 'kitt-heading  page-teaser-"
            + "dict__header kitt-heading_size_l')]")
    WebElement labelPage;
    
    @FindBy(xpath = "//span[contains(@class, 'kitt-button__text')]")
    WebElement buttonIssueOnline;
    
    @FindBy(xpath = "//input[contains(@data-name, 'lastName')]")
    WebElement inputFieldLastName;
    
    @FindBy(xpath = "//input[contains(@data-name, 'firstName')]")
    WebElement inputFieldFirstName;
    
    @FindBy(xpath = "//input[contains(@data-name, 'middleName')]")
    WebElement inputFieldMiddleName;
    
    @FindBy(xpath = "//input[contains(@data-name, 'cardName')]")
    WebElement inputFieldCardName;

    @FindBy(xpath = "//input[contains(@data-name, 'birthDate')]")
    WebElement inputFieldBirthDate;
    
    @FindBy(xpath = "//input[contains(@data-name, 'email')]")
    WebElement inputFieldEmail;
    
    @FindBy(xpath = "//input[contains(@data-name, 'phone')]")
    WebElement inputFieldPhone;
    
    @FindBy(xpath = "//button[contains(@class, 'odcui-button odcui-button_color_black')]")
    WebElement buttonNext;
    
    @FindBy(xpath = "//input[contains(@data-name, 'series')]")
    WebElement inputSeries;
    
    @FindBy(xpath = "//input[contains(@data-name, 'number')]")
    WebElement inputNumber;
    
    @FindBy(xpath = "//input[contains(@data-name, 'issueDate')]")
    WebElement inputIssueDate;
    
    public YouthCard(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, 10, 1000);
    }

    public void checkLabelPage() {
        Assertions.assertEquals(labelPage.getText(), "Молодёжная карта");
    }

    public void clickButtonIssueOnline() {
        buttonIssueOnline.click();
    }

    /**
     * Заполнение полей формы для оформления молодежной карты с одновременной
     * проверкой правильности заполнения
     */
    public void fillInputs(String lastName, String firstName,
            String middleName, String cardName, String birthDate,
            String email, String phone) {
        // Ожидание перемотки страницы к элементам формы
        wait.until(ExpectedConditions.visibilityOf(inputFieldLastName));
        fillInput(inputFieldLastName, lastName);
        fillInput(inputFieldFirstName, firstName);
        fillInput(inputFieldMiddleName, middleName);
        // Поле ввода имени держателя карты требует обязательной очистки,
        // иначе, по крайней мере у меня сохранялось старое значение и 
        // повторно вводилось новое
        inputFieldCardName.clear();
        fillInput(inputFieldCardName, cardName);
        fillInput(inputFieldBirthDate, birthDate);
        fillInput(inputFieldEmail, email);
        
        // Ожидание конца ввода и валидации полей
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {}
        
        // Перемотка к полю ввода телефона и ввод с проверкой
        Actions actions = new Actions(driver);
        actions.click(inputFieldPhone);
        actions.perform();
        inputFieldPhone.sendKeys(phone);
        Assertions.assertEquals(getPhoneInMask(phone), inputFieldPhone.getAttribute("value"));
    }
    
    /**
     * Запуск заполнения полей дефолтными значениями
     */
    public void fillInputs() {
        fillInputs("Антонов", "Михаил", "Иванович", "MIHAIL ANTONOV",
                "22.02.2004", "antmih@mail.ru", "9681278345");
    }
    
    /**
     * Метод разбора номера с возвратом по маске телефона
     * Пример:
     * @param phone = "9681278345"
     * @return "+7 (968) 127-83-45"
     */
    private String getPhoneInMask(String phone) {
        String result = "+7 (" + phone.substring(0, 3) + ") " + phone.substring(3, 6) +
                   "-" + phone.substring(6, 8) + "-" + phone.substring(8, 10);
        return result;
    }
    
    private void fillInput(WebElement element, String value) {
        element.sendKeys(value);
        Assertions.assertEquals(value, element.getAttribute("value"));
    }
    
    /**
     * Нажатие на кнопку "Далее"
     */
    public void clickButtonNext() {
        Actions actions = new Actions(driver);
        try {
            actions.moveToElement(buttonNext);
            actions.perform();
        } catch (Exception ex) {}
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {}
        
        buttonNext.click();
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {}
    }
    
    /**
     * Проверка наличия ошибок в незаполенный полях
     */
    public void isErrorCheckInput() {        
        Assertions.assertEquals("Обязательное поле", inputSeries.findElement(By.xpath
                ("..//div[contains(@class, 'odcui-error__text')]")).getText());
        
        Assertions.assertEquals("Обязательное поле", inputNumber.findElement(By.xpath
                ("..//div[contains(@class, 'odcui-error__text')]")).getText());
        
        Assertions.assertEquals("Обязательное поле", inputIssueDate.findElement(By.xpath
                ("..//..//..//div[contains(@class, 'odcui-error__text')]")).getText());
    }
}
