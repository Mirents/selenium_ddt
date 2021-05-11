package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

public class DebetCard {
    WebDriver driver;
    
    @FindBy(xpath = "//h2[contains(@class, 'kitt-heading kitt-heading_size_m"
            + " product-catalog__header_product') and contains(text(), 'Молодёжная')]")
    WebElement labelCards;
    
    @FindBy(xpath = "//h1[contains(@class, 'kitt-heading kitt-heading_size_l product-catalog__header')]")
    WebElement labelPage;
    
    public DebetCard(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    
    public void checkLabelPage() {
        Assertions.assertEquals(labelPage.getText(), "Дебетовые карты");
    }
    
    public void checkAndClickCards() {
        String buttonGetCardsXPath = "//h2[contains(@class, 'kitt-heading "
                + "kitt-heading_size_m product-catalog__header_product') "
                + "and contains(text(), 'Молодёжная')]//..//..//span[contains"
                + "(@class, 'kitt-button__text') and contains(text(), 'Заказать онлайн')]";
        
        Assertions.assertEquals(labelCards.getText(), "Молодёжная карта");
        WebElement buttonGetCards = driver.findElement(By.xpath(buttonGetCardsXPath));
        buttonGetCards.click();
    }
}
