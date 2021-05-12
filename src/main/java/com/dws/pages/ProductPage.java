package com.dws.pages;

import static com.dws.managers.PageManager.getPageManager;
import com.dws.pages.base.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends PageBase {
    
    @FindBy(xpath = "//a[contains(text(),'Electronics')]")
    private WebElement buttonElectronics;
    
    @FindBy(xpath = "//a[contains(text(),'Computers')]")
    private WebElement buttonComputers;
    
    public ProductPage(String description) {
        super(description);
    }
    
    public ProductPage clickButtonElectronics() {
        buttonElectronics.click();
        return getPageManager().getProductPage();
    }
    
    public ProductPage clickButtonComputers() {
        buttonComputers.click();
        return getPageManager().getProductPage();
    }
}
