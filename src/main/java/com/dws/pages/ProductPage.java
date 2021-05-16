package com.dws.pages;

import com.dws.helper.ProductHelper;
import com.dws.pages.base.PageBase;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static com.dws.helper.CartHelper.getCartHelper;
import static com.dws.managers.WaitManager.getWaitManager;

public class ProductPage extends PageBase {
    
    @FindBy(xpath = "//div[contains(@class, 'add-to')]//input[contains(@class, 'add-to-c')]")
    private WebElement buttonAddToCart;
    
    @FindBy(xpath = "//input[contains(@class, 'qty-input')]")
    private WebElement inputQuanity;
    
    @FindBy(xpath = "//div[@id='bar-notification']")
    private WebElement barNotification;
    
    @FindBy(xpath = "//div[@id='bar-notification']//p[@class=\"content\"]")
    private WebElement barNotificationMessage;
    
    @FindBy(xpath = "//h1")
    private WebElement labelProductName;
    
    @FindBy(xpath = "//span[@itemprop='price']")
    private WebElement labelProductPrice;
        
    public ProductPage() {
        String labelPrice = labelProductPrice.getText().replaceAll("[^\\d.]", "");
        float price = Float.parseFloat(labelPrice);
        String labelQuantity = inputQuanity.getAttribute("value").replaceAll("[^\\d.]", "");
        int quantity = Integer.parseInt(labelQuantity);
        getCartHelper().addProduct(new ProductHelper(labelProductName.getText(),
                price, quantity));
    }
    
    public ProductPage assertBarNotificationColor(String color) {
        boolean isContains = false;
        getWaitManager().until(ExpectedConditions.visibilityOf(barNotification));
        if(barNotification.getCssValue("background").contains(color))
            isContains = true;
        Assertions.assertTrue(isContains, "Checking the correctness of the color");
        return this;
    }
    
    public ProductPage assertBarNotificationText(String text) {
        getWaitManager().until(ExpectedConditions.textToBePresentInElement(barNotificationMessage, text));
        Assertions.assertEquals(barNotificationMessage.getText(), text,
                "Checking the message text");
        return this;
    }
    
    public ProductPage clickButtonAddToCart() {
        buttonAddToCart.click();
        return this;
    }
    
    public ProductPage inputQuanityEnterText(String text) {
        inputQuanity.sendKeys(text);
        return this;
    }
    
    public ProductPage inputQuanityEnterNumber(int number) {
        inputQuanity.sendKeys(Integer.toString(number));
        getCartHelper().getProductByName(labelProductName.getText()).setQuantity(number);
        return this;
    }
    
    public ProductPage inputQuanityClear() {
        inputQuanity.clear();
        return this;
    }
}
