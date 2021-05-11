package com.demowebshop.pages;

import com.demowebshop.base.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartPage extends PageBase {
    
    @FindBy(xpath = "//ul[contains(@class, 'top-menu')]")
    WebElement top_menu;
    
    public StartPage(String description) {
        super(description);
    }
    
    public void getText() {
        LOGGER.debug(top_menu.getText());
    }
}
