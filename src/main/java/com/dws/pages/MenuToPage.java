package com.dws.pages;

import com.dws.pages.base.PageBase;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuToPage extends PageBase {
    @FindBy(xpath = "//ul[@class='top-menu']//li")
    private List<WebElement> listTopMenu;
    
    @FindBy(xpath = "//ul[@class='top-menu']//a")
    private List<WebElement> listTopSubMenu;
    
    @FindBy(xpath = "//div[contains(@class,'block-category')]//a")
    private List<WebElement> listLeftMenuCategories;
    
    public MenuToPage(String description) {
        super(description);
    }

    public MenuToPage mouseMoveToTopMenu(String name) {
        mouseMoveToElementFromList(listTopMenu, name);
        return this;
    }
    
    public MenuToPage clickTopMenu(String name) {        
        clickToElementFromList(listTopMenu, name);
        return this;
    }
    
    public MenuToPage clickTopSubMenu(String name) {
        clickToElementFromList(listTopSubMenu, name);
        return this;
    }
    
    public MenuToPage clickLeftMenu(String name) {        
        clickToElementFromList(listLeftMenuCategories, name);
        return this;
    }
}