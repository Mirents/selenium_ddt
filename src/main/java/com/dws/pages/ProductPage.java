package com.dws.pages;

import com.dws.pages.base.PageBase;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends PageBase {
    
    @FindBy(xpath = "//div[contains(@class, 'add-to')]//input[contains(@class, 'add-to-c')]")
    private WebElement buttonAddToCart;
    
    @FindBy(xpath = "//input[@class='qty-input']")
    private WebElement inputQuanity;
    
    @FindBy(xpath = "//div[@id='bar-notification']")
    private WebElement barNotification;
    
    @FindBy(xpath = "//div[@id='bar-notification']//p[@class=\"content\"]")
    private WebElement barNotificationMessage;
    
    public ProductPage(String description) {
        super(description);
    }
    
    public ProductPage AssertBarNotificationColor(String color) {
        LOGGER.debug(">> AssertBarNotificationColor");
        boolean isContains = false;
        wait.until(ExpectedConditions.visibilityOf(barNotification));
        if(barNotification.getCssValue("background").contains(color))
            isContains = true;
        Assertions.assertTrue(isContains, "Checking the correctness of the color");
        LOGGER.debug("<< AssertBarNotificationColor");
        return this;
    }
    
    public ProductPage AssertBarNotificationText(String text) {
        LOGGER.debug(">> AssertBarNotificationText");
        wait.until(ExpectedConditions.textToBePresentInElement(barNotificationMessage, text));
        Assertions.assertEquals(barNotificationMessage.getText(), text,
                "Checking the message text");
        LOGGER.debug("<< AssertBarNotificationText");
        return this;
    }
    
    public ProductPage clickButtonAddToCart() {
        action.moveToElement(buttonAddToCart).click().perform();
        buttonAddToCart.click();
        return this;
    }
    
    public ProductPage inputQuanityEnterText(String text) {
        inputQuanity.sendKeys(text);
        return this;
    }
    
    public ProductPage inputQuanityClear() {
        inputQuanity.clear();
        return this;
    }
}
