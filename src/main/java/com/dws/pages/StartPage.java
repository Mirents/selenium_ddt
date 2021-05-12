package com.dws.pages;

import static com.dws.managers.PageManager.getPageManager;
import com.dws.pages.base.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartPage extends PageBase {
    
    @FindBy(xpath = "//a[contains(text(),'Electronics')]")
    private WebElement buttonElectronics;
    
    @FindBy(xpath = "//a[contains(text(),'Computers')]")
    private WebElement buttonComputers;
    
    public StartPage(String description) {
        super(description);
    }
    
    public StartPage clickButtonElectronics() {
        buttonElectronics.click();
        return getPageManager().getStartPage();
    }
    
    public StartPage clickButtonComputers() {
        buttonComputers.click();
        return getPageManager().getStartPage();
    }
}
