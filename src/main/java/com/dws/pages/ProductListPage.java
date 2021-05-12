package com.dws.pages;

import static com.dws.managers.PageManager.getPageManager;
import com.dws.pages.base.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductListPage extends PageBase {
    
    @FindBy(xpath = "//a[contains(text(),'Electronics')]")
    private WebElement buttonElectronics;
    
    @FindBy(xpath = "//a[contains(text(),'Computers')]")
    private WebElement buttonComputers;
    
    public ProductListPage(String description) {
        super(description);
    }
    
    public ProductListPage clickButtonElectronics() {
        buttonElectronics.click();
        return getPageManager().getProductListPage();
    }
    
    public ProductListPage clickButtonComputers() {
        buttonComputers.click();
        return getPageManager().getProductListPage();
    }
}
