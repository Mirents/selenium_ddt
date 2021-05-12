package com.dws.pages;

import static com.dws.managers.PageManager.getPageManager;
import com.dws.pages.base.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends PageBase {
    
    @FindBy(xpath = "//a[contains(text(),'Electronics')]")
    private WebElement buttonElectronics;
    
    @FindBy(xpath = "//a[contains(text(),'Computers')]")
    private WebElement buttonComputers;
    
    public CartPage(String description) {
        super(description);
    }
    
    public CartPage clickButtonElectronics() {
        buttonElectronics.click();
        return getPageManager().getCartPage();
    }
    
    public CartPage clickButtonComputers() {
        buttonComputers.click();
        return getPageManager().getCartPage();
    }
}
